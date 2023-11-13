public interface Component {
    String getID();

    void removeUser(Component user);

    void addUser(Component user);
}
