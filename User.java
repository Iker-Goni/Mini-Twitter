import java.util.ArrayList;
import java.util.List;

public class User implements Component, Observer, Subject{
    private String userID;
    private List<Observer> followers;
    private List<User> following;
    private List<String> postedMessages;
    private List<String> newsFeed;
    private UserGroup parentGroup;

    public User(String userID){
        this.userID = userID;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.postedMessages = new ArrayList<>();
        this.newsFeed = new ArrayList<>();
    }

    public UserGroup getParent(){
        return this.parentGroup;
    }

    public void setParent(UserGroup group){
        this.parentGroup = group;
    }

    public void follow(User user){
        following.add(user);
        user.addObserver(this);
    }

    public void postMessage(String message){
        postedMessages.add(message);
        newsFeed.add(message);
        notifyObservers(message);
    }

    public List<User> getFollowing(){
        return this.following;
    }

    public List<String> getPostedMessages(){
        return this.postedMessages;
    }

    public List<String> getNewsFeed(){
        return this.newsFeed;
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

    @Override
    public String toString(){
        return getID();
    }

    @Override 
    public void addObserver(Observer observer){
        if(observer instanceof User){
            this.followers.add((User) observer);
        }
        else{
            this.followers.add((UserView) observer);
        }
    }

    @Override 
    public void notifyObservers(String tweet){
        for(Observer observer : followers){
            observer.update(tweet);
        }
    }

    @Override
    public void update(String tweet){
        this.newsFeed.add(tweet);
    }
}
