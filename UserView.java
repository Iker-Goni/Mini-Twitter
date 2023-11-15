import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;

public class UserView extends JFrame implements Observer{
    // current user 
    private User currentUser;
    // list of all users
    private List<User> users;
    // current following model used for list
    private DefaultListModel<String> currentFollowingModel;
    // news feed model used for list
    private DefaultListModel<String> newsFeedModel;
    // list for newsfeed
    private JList<String> newsFeed;
    
    // userview constructor
    public UserView(User user, List<User> users){
        this.currentUser = user;
        this.users = users;
        this.currentFollowingModel = new DefaultListModel<>();
        this.newsFeedModel = new DefaultListModel<>();
        user.addObserver(this);
        initialize();
    }

    // update currentfollowing model
    private void updateCurrentFollowingModel(){
        currentFollowingModel.clear();

        if(currentUser.getFollowing().size() != 0){
            for(User u : currentUser.getFollowing()){
                currentFollowingModel.addElement(u.getID());
            }
        }
    }

    // update news feed model
    private void updateNewsFeedModel(){
        newsFeedModel.clear();

        List<String> newsFeedData = currentUser.getNewsFeed();

        if(newsFeedData.size() > 0){
            for(String tweet : newsFeedData){
                newsFeedModel.addElement(tweet);
            }
            newsFeed.setModel(newsFeedModel);
        }
    }

    // initialize the userview ui
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
        currentFollowing.setVisibleRowCount(10); 
        currentFollowing.setFixedCellWidth(150); 
        updateCurrentFollowingModel();

        // leftPane for followers
        JScrollPane leftPanel = new JScrollPane(currentFollowing);
        JLabel leftPanelLabel = new JLabel("Following");
        leftPanel.setColumnHeaderView(leftPanelLabel);

        // newsFeed JList
        newsFeed = new JList<>(newsFeedModel);
        newsFeed.setVisibleRowCount(10);
        newsFeed.setFixedCellWidth(150);
        updateNewsFeedModel();

        // rightpane for newsfeed
        JScrollPane rightPanel = new JScrollPane(newsFeed);
        JLabel rightPanelLabel = new JLabel("News Feed");
        rightPanel.setColumnHeaderView(rightPanelLabel);

        // add components to mainpanel gridbag
        centerPanel.add(userID, gbc);
        gbc.gridx = 1;
        centerPanel.add(followUser, gbc);
        gbc.gridy = 1;
        gbc.gridx = 0;
        centerPanel.add(tweetMessage, gbc);
        gbc.gridx = 1;
        centerPanel.add(postTweet, gbc);

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
                tweetMessage.setText("");
                AdminControlPanel tempPanel = AdminControlPanel.getInstance();
            }
        };

        postTweet.addActionListener(postTweetAction);

        // add centerPanel to mainPanel
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // add left and right panel to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // add mainPanel to frame
        frame.add(mainPanel);

        // make window visible to user
        frame.setTitle(currentUser.getID() + "'s Profile");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    // update the news feed
    @Override
    public void update(String tweet){
        updateNewsFeedModel();
    }
}
