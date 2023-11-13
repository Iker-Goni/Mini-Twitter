import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.List;

public class UserView extends JFrame{
    public UserView(){
        initialize();
    }

    public void initialize(){
        JFrame frame = new JFrame();

        JPanel mainPanel = new JPanel(new GridBagLayout());

        // GridBag constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // userID text area
        JTextArea userID = new JTextArea();

        // follow user button
        JButton followUser = new JButton("Follow User");

        // tweetMessage text area
        JTextArea tweetMessage = new JTextArea();

        // postTweet button
        JButton postTweet = new JButton();

        // currentFollowing JList
        JList currentFollowing = new JList();

        // newsFeed JList
        JList newsFeed = new JList();
    }
}
