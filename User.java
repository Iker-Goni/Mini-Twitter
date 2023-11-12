import java.util.ArrayList;
import java.util.List;

public class User {
    private String userID;
    private List<User> followers;
    private List<User> following;
    private List<String> newsFeed;

    public User(String userID){
        this.userID = userID;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.newsFeed = new ArrayList<>();
    }
}
