import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class TwitterStatisticsVisitor implements Visitor{
    private int totalUsers;
    private int totalGroups;
    private int totalMessages;
    private int positiveMessages;
    private HashSet<String> positiveWords = new HashSet<String>(Arrays.asList("happy", "positive", "awesome", "great", "nice"));

    public TwitterStatisticsVisitor(){
        reset();
    }

    private void reset(){
        totalUsers = 0;
        totalGroups = 0;
        totalMessages = 0;
        positiveMessages = 0;
    }

    @Override
    public void visit(User user){
        totalUsers++;
        totalMessages = user.getPostedMessages().size();
        for(String message : user.getPostedMessages()){
            String[] splitMessage = message.split(" ");

            for(String word : splitMessage){
                if(positiveWords.contains(word.toLowerCase())){
                    positiveMessages++;
                }
            }
        }
    }

    @Override 
    public void visit(UserGroup userGroup){
        totalGroups++;

    }

    public int getTotalUsers(){
        return totalUsers;
    }

    public int getTotalGroups(){
        return totalGroups;
    }

    public int getTotalMessages(){
        return totalMessages;
    }

    public int getTotalPositiveMessages(){
        return positiveMessages;
    }
}
