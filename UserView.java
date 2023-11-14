import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;

public class UserView extends JFrame{
    private User currentUser;
    private List<User> users;
    private DefaultListModel<String> currentFollowingModel;
    public UserView(User user, List<User> users){
        this.currentUser = user;
        this.users = users;
        this.currentFollowingModel = new DefaultListModel<>();
        initialize();
    }

    private void updateCurrentFollowingModel(){
        currentFollowingModel.clear();

        for(User u : currentUser.getFollowing()){
            currentFollowingModel.addElement(u.getID());
        }
    }

    public void initialize(){
        JFrame frame = new JFrame();

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
        JList<String> currentFollowing = new JList<>(currentFollowingModel);

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
                for(User u : users){
                    if(u.getID().equals(userID.getText())){
                        currentUser.follow(u);
                        userID.setText("");
                        updateCurrentFollowingModel();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "User Not Found");
            }
        };

        followUser.addActionListener(followUserAction);

        // postMessage action
        ActionListener postTweetAction = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                currentUser.postMessage(tweetMessage.getText());
            }
        };

        postTweet.addActionListener(postTweetAction);

        // add centerPanel to mainPanel
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // add mainPanel to frame
        frame.add(mainPanel);

        // make window visible to user
        frame.setTitle(currentUser.getID() + "'s Profile");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
