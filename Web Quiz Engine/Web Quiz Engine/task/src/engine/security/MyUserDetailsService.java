package engine.security;

import engine.models.userdomain.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
class MyUserDetailService implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    MyUserDetailService(@Qualifier("JpaUserImpl") UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDao.selectByEmail(email)
                      .map(MyUserDetails::new)
                      .orElseThrow(() ->
                              new UsernameNotFoundException(String.format("User %s not found", email)));
    }
}
