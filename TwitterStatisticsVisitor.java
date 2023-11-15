import java.util.Arrays;
import java.util.HashSet;

public class TwitterStatisticsVisitor implements Visitor{
    // total users
    private int totalUsers;

    // total groups
    private int totalGroups;

    // total messages
    private int totalMessages;

    // total positive messages
    private int positiveMessages;
    
    // set of some positive words
    private HashSet<String> positiveWords = new HashSet<String>(Arrays.asList("happy", "positive", "awesome", "great", "nice"));

    // reset the statistics
    public TwitterStatisticsVisitor(){
        reset();
    }

    // reset the data
    public void reset(){
        totalUsers = 0;
        totalGroups = 0;
        totalMessages = 0;
        positiveMessages = 0;
    }

    // visit a user to get data
    @Override
    public void visit(User user){
        totalUsers++;
        totalMessages += user.getPostedMessages().size();
        for(String message : user.getPostedMessages()){
            String[] splitMessage = message.split(" ");

            for(String word : splitMessage){
                if(positiveWords.contains(word.toLowerCase())){
                    positiveMessages++;
                }
            }
        }
    }

    // visit a group to get data
    @Override 
    public void visit(UserGroup userGroup){
        totalGroups++;

    }

    // get the total users
    public int getTotalUsers(){
        return totalUsers;
    }

    // get the total groups
    public int getTotalGroups(){
        return totalGroups;
    }

    // get the total messages
    public int getTotalMessages(){
        return totalMessages;
    }

    // get the total positive messages
    public int getTotalPositiveMessages(){
        return positiveMessages;
    }
}
