import java.util.ArrayList;
import java.util.List;

public class User implements Component, Observer, Subject{
    private String userID;
    private List<Observer> followers;
    private List<User> following;
    private List<String> postedMessages;
    private List<String> newsFeed;
    private UserGroup parentGroup;
    private long creationTime;
    private long lastUpdateTime;

    // user constructor
    public User(String userID){
        long start  = System.currentTimeMillis();
        this.userID = userID;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.postedMessages = new ArrayList<>();
        this.newsFeed = new ArrayList<>();
        this.creationTime = System.currentTimeMillis() - start;
    }

    // get creation time
    public long getCreationTime(){
        return this.creationTime;
    }

    // get last update time
    public long getLastUpdateTime(){
        return this.lastUpdateTime;
    }

    // get parent (group) of user
    public UserGroup getParent(){
        return this.parentGroup;
    }

    // set the group of the user
    public void setParent(UserGroup group){
        this.parentGroup = group;
    }

    // follow another user
    public void follow(User user){
        following.add(user);
        user.addObserver(this);
    }

    // post a message on the feed
    public void postMessage(String message){
        postedMessages.add(message);
        update(message);
        notifyObservers(message);
    }

    // get the followins of this user
    public List<User> getFollowing(){
        return this.following;
    }

    // get the posted messages of the user
    public List<String> getPostedMessages(){
        return this.postedMessages;
    }

    // get the news feed of the user
    public List<String> getNewsFeed(){
        return this.newsFeed;
    }

    // accept a visitor for data
    public void acceptVisitor(Visitor visitor){
        visitor.visit(this);
    }

    // get id of user
    @Override
    public String getID(){
        return this.userID;
    }

    // add user
    @Override
    public void addUser(Component user) throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Cannot add a user to a user");
    }

    // remove user
    @Override 
    public void removeUser(Component user) throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Cannot remove a user from a user");
    }

    // print user
    @Override
    public String toString(){
        return getID();
    }

    // add observer 
    @Override 
    public void addObserver(Observer observer){
        if(observer instanceof User){
            this.followers.add((User) observer);
        }
        else{
            this.followers.add((UserView) observer);
        }
    }

    // notify observers of post
    @Override 
    public void notifyObservers(String tweet){
        for(Observer observer : followers){
            observer.update(tweet);
        }
    }

    // update observers
    @Override
    public void update(String tweet){
        this.newsFeed.add(tweet);
        this.lastUpdateTime = System.currentTimeMillis();
    }
}
