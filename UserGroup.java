import java.util.ArrayList;
import java.util.List;

public class UserGroup implements Component{
    private String groupID;
    private List<Component> components;
    private long creationTime;

    // usergroup constructor
    public UserGroup(String groupID){
        this.creationTime = System.currentTimeMillis();
        this.groupID = groupID;
        this.components = new ArrayList<>();
        this.creationTime = System.currentTimeMillis() - this.creationTime;
    }

    // get creation time
    public long getCreationTime(){
        return this.creationTime;
    }

    // get the components
    public List<Component> getComponents(){
        return this.components;
    }

    // accept a visitor for data
    public void acceptVisitor(Visitor visitor){
        visitor.visit(this);
    }

    // get group id
    @Override
    public String getID(){
        return this.groupID;
    }

    // add a user 
    @Override
    public void addUser(Component component){
        components.add(component);
    }


    // remove a user
    @Override 
    public void removeUser(Component component){
        components.remove(component);
    }

    // print user group
    @Override
    public String toString(){
        return getID();
    }
}
