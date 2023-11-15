public interface Component {
    // get the id of the component
    String getID();

    // remove from component
    void removeUser(Component user);

    // add to component
    void addUser(Component user);

    // print the component
    String toString();
}
