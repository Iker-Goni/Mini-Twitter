import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.List;

public class UserView extends JFrame{
    private User currentUser;
    private UserGroup group;
    public UserView(User user){
        this.currentUser = user;
        initialize();
    }

    public void initialize(){
        JFrame frame = new JFrame();
        group = currentUser.getParent();

        // main panel for gridbagpanel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // gridbagpanel for components
        JPanel centerPanel = new JPanel(new GridBagLayout());

        // GridBag constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // userID text area
        JTextArea userID = new JTextArea();
        userID.setColumns(10);

        // follow user button
        JButton followUser = new JButton("Follow User");

        // tweetMessage text area
        JTextArea tweetMessage = new JTextArea();
        tweetMessage.setColumns(10);

        // postTweet button
        JButton postTweet = new JButton("Post Tweet");

        // currentFollowing JList
        JList currentFollowing = new JList();

        // newsFeed JList
        JList newsFeed = new JList();

        // add components to mainpanel gridbag
        centerPanel.add(userID, gbc);
        gbc.gridx = 1;
        centerPanel.add(followUser, gbc);
        gbc.gridy = 1;
        gbc.gridx = 0;
        centerPanel.add(currentFollowing, gbc);
        gbc.gridy = 2;
        centerPanel.add(tweetMessage, gbc);
        gbc.gridx = 1;
        centerPanel.add(postTweet, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        centerPanel.add(newsFeed);

        // followUser action
        ActionListener followUserAction = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                User user = new User(userID.getText());
                userID.setText("");
                currentUser.follow(user);
            }
        };

        followUser.addActionListener(followUserAction);

        // add centerPanel to mainPanel
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // add mainPanel to frame
        frame.add(mainPanel);

        // make window visible to user
        frame.setTitle(currentUser.getID() + "'s Profile");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
