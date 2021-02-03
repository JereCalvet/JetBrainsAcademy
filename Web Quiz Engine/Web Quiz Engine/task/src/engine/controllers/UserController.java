package engine.controllers;

import engine.models.userdomain.User;
import engine.models.userdomain.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/register")
@Validated
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserDao userDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodQuizArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return errors;
    }

    @PostMapping(consumes = "application/json")
    public User registerUser(@Valid @RequestBody User user) {

        if (isMailAlreadyInUse(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mail already used.");
        } else {
            encodeUserPassword(user);
            userDao.save(user);
        }

       return user;
    }

    private boolean isMailAlreadyInUse(User user) {
        Optional<User> userByMail = userDao.selectByEmail(user.getEmail());
        return userByMail.isPresent();
    }

    private void encodeUserPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
