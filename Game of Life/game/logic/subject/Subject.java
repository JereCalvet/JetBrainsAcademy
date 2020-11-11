package life.game.logic.subject;

import life.game.logic.observer.Observer;

public interface Subject {
    void registerObserver(Observer obs);
    void removeObserver(Observer obs);
    void notifyObservers();
}
