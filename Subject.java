public interface Subject {
    // add observer
    void addObserver(Observer observer);

    // notify observers of change
    void notifyObservers(String tweet);
}
