import java.util.ArrayList;
import java.util.List;

public class UserGroup implements Component{
    private String groupID;
    private List<Component> components;

    public UserGroup(String groupID){
        this.groupID = groupID;
        this.components = new ArrayList<>();
    }

    public List<Component> getComponents(){
        return this.components;
    }

    public void acceptVisitor(Visitor visitor){
        visitor.visit(this);
    }

    @Override
    public String getID(){
        return this.groupID;
    }

    @Override
    public void addUser(Component component){
        components.add(component);
    }

    @Override 
    public void removeUser(Component component){
        components.remove(component);
    }

    @Override
    public String toString(){
        return getID();
    }
}
