import java.util.ArrayList;
import java.util.List;

public class UserGroup {
    private String groupID;
    private List<User> users;

    public UserGroup(String groupID){
        this.groupID = groupID;
        this.users = new ArrayList<>();
    }
}
