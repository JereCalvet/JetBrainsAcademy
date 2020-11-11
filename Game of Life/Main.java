package life;

import life.game.gui.ViewGameOfLife;
import life.game.logic.observer.Controller;
import life.game.logic.subject.ModelGameLogic;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int size = scan.nextInt();

        ModelGameLogic model = new ModelGameLogic(size);
        ViewGameOfLife view = new ViewGameOfLife();
        Controller controller = new Controller(model, view);
    }
}
