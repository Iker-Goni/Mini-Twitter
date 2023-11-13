import java.util.ArrayList;
import java.util.List;

public class User implements Component{
    private String userID;
    private List<User> followers;
    private List<User> following;
    private List<String> postedMessages;

    public User(String userID){
        this.userID = userID;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.postedMessages = new ArrayList<>();
    }

    public void follow(User user){
        following.add(user);
    }

    public void postMessage(String message){
        postedMessages.add(message);
    }

    public List<User> getFollowing(){
        return this.following;
    }

    public List<String> getPostedMessages(){
        return this.postedMessages;
    }

    @Override
    public String getID(){
        return this.userID;
    }

    @Override
    public void addUser(Component user) throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Cannot add a user to a user");
    }

    @Override 
    public void removeUser(Component user) throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Cannot remove a user from a user");
    }
}
