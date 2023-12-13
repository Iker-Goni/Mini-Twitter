import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;


public class AdminControlPanel extends JFrame{
    // total messages
    private int totalMessages;
    // total positive messages
    private int totalPositiveMessages;
    // total users
    private int totalUsers;
    // total groups
    private int totalGroups;
    // list of users
    private List<User> users;
    // list of groups
    private List<UserGroup> groups;
    // set of userids
    private HashSet<String> userIDs;
    // set of groupids
    private HashSet<String> groupIDs;
    // selected node in the tree view
    private DefaultMutableTreeNode selectedNode;
    // tree view of hiearchy
    private JTree treeView;
    private DefaultTreeModel treeModel;
    // root node
    private DefaultMutableTreeNode rootNode;
    // singleton instance of admincontrolpanel
    private static AdminControlPanel instance;

    // boolean that represents whether ids are valid or not
    private boolean validIDs;

    // last updated user 
    private User latestUpdatedUser;

    // private constructor for AdminControlPanel
    private AdminControlPanel() {
        this.totalMessages = 0;
        this.users = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.userIDs = new HashSet<>();
        this.groupIDs = new HashSet<>();
        this.validIDs = true;
        this.latestUpdatedUser = null;
        UserGroup root = new UserGroup("Root");
        rootNode = new DefaultMutableTreeNode(root);
        treeModel = new DefaultTreeModel(rootNode);
        treeView = new JTree(treeModel);
        treeView.setCellRenderer(new CustomTreeCellRenderer());
        selectedNode = rootNode;
    }

    // Singleton design pattern
    public static AdminControlPanel getInstance(){
        if(instance == null){
            instance = new AdminControlPanel();
        }
        return instance;
    }

    // get the data for showing statistics
    private void getStatistics(){
        TwitterStatisticsVisitor twitterStatistics = new TwitterStatisticsVisitor();

        for(User user : users){
            twitterStatistics.visit(user);
        }

        for(UserGroup userGroup : groups){
            twitterStatistics.visit(userGroup);
        }
        totalUsers = twitterStatistics.getTotalUsers();
        totalGroups = twitterStatistics.getTotalGroups();
        totalMessages = twitterStatistics.getTotalMessages();
        totalPositiveMessages = twitterStatistics.getTotalPositiveMessages();
    }

    public void setLastestUpdatedUser(User user){
        this.latestUpdatedUser = user;
    }

    // add a childnode to the tree, and update the tree
    private void addChildNode(DefaultMutableTreeNode parentNode, Component component) {
        Object parentObject = rootNode.getUserObject();
        if (!(parentNode == rootNode)) {
            parentObject = parentNode.getUserObject();
        }
    
        // if the parent (selected node) is a userGroup
        if (parentObject instanceof UserGroup) {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(component);
            treeModel.insertNodeInto(childNode, parentNode, parentNode.getChildCount());
            treeModel.nodeStructureChanged(parentNode);
        } else {
            JOptionPane.showMessageDialog(null, "Error: Can only add users and groups to groups.");
        }
    }
    
    // set up components
    public void initialize() {
        JFrame frame = new JFrame();

        // default tree node is rootNode
        selectedNode = rootNode;

        // main panel
        JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        // rightPanel
        JPanel rightPanel = new JPanel(new GridBagLayout());

        // JScrollPane
        JScrollPane scrollPane = new JScrollPane(treeView);

        // add right pane and scroll pane to mainpanel
        mainPanel.setLeftComponent(scrollPane);
        mainPanel.setRightComponent(rightPanel);
        mainPanel.setDividerLocation(280);

        // GridBag constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 5);

        // add user button
        JButton addUser = new JButton("Add User");

        // add group button
        JButton addGroup = new JButton("Add Group");

        // oper user view button
        JButton openUserView = new JButton("Open User View");

        // show user total button
        JButton userTotal = new JButton("Show User Total");

        // show group total button
        JButton groupTotal = new JButton("Show Group Total");

        // show messages total button
        JButton messagesTotal = new JButton("Show Messages Total");

        // show positive percentage button
        JButton positivePercentage = new JButton("Show Positive Percentage");

        // user id text area
        JTextArea userID = new JTextArea();

        // group id text area
        JTextArea groupID = new JTextArea();

        // validate ids button
        JButton validateIDs = new JButton("Validate IDs");

        // last updated user button
        JButton lastUpdatedUser = new JButton("Last Updated User");

        // add components to rightPanel
        rightPanel.add(userID, gbc);
        gbc.gridx = 1;
        rightPanel.add(addUser, gbc);
        gbc.gridy = 1;
        gbc.gridx = 0;
        rightPanel.add(groupID, gbc);
        gbc.gridx = 1;
        rightPanel.add(addGroup, gbc);
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        rightPanel.add(openUserView, gbc);
        gbc.gridwidth = 1;
        gbc.gridy = 3;
        rightPanel.add(userTotal, gbc);
        gbc.gridx = 1;
        rightPanel.add(groupTotal, gbc);
        gbc.gridy = 4;
        gbc.gridx = 0;
        rightPanel.add(messagesTotal, gbc);
        gbc.gridx = 1;
        rightPanel.add(positivePercentage, gbc);
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        rightPanel.add(validateIDs, gbc);
        gbc.gridy = 6;
        rightPanel.add(lastUpdatedUser, gbc);



        // addUser action
        ActionListener addedUser = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(userID.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Error: User ID cannot be empty.");
                }
                else if(selectedNode.getUserObject() instanceof User){
                    JOptionPane.showMessageDialog(null,"Error: Can only add users and groups to groups.");
                }
                else if(userIDs == null || !userIDs.contains(userID.getText())){
                    String[] splitID = userID.getText().split(" ");
                    if(groupIDs.contains(userID.getText()) || splitID.length > 1){
                        validIDs = false;
                    }

                    userIDs.add(userID.getText());
                    User user = new User(userID.getText());
                    userID.setText("");
                    users.add(user);
                    addChildNode(selectedNode, user);
                    selectedNode = rootNode;
                }
                else{
                    JOptionPane.showMessageDialog(null, "Error: User ID is already in use, try another.");
                }
            }
        };

        addUser.addActionListener(addedUser);

        // addGroup action
        ActionListener addedGroup = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(groupID.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Error: Group ID cannot be empty.");
                }
                else if(selectedNode.getUserObject() instanceof User){
                    JOptionPane.showMessageDialog(null,"Error: Can only add users and groups to groups.");
                }
                else if(groupIDs == null || !groupIDs.contains(groupID.getText())){
                    String[] splitID = groupID.getText().split(" ");
                    if(userIDs.contains(groupID.getText()) || splitID.length > 1){
                        validIDs = false;
                    }
                    groupIDs.add(groupID.getText());
                    UserGroup group = new UserGroup(groupID.getText());
                    groupID.setText("");
                    groups.add(group);
                    addChildNode(selectedNode, group);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Error: Group ID is already in use, try another.");
                }
            }
        };

        addGroup.addActionListener(addedGroup);

        // showUserTotal action
        ActionListener showUserTotal = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                getStatistics();
                JOptionPane.showMessageDialog(null, "User Total: " + totalUsers);
            }
        };

        userTotal.addActionListener(showUserTotal);

        // showGroupTotal action
        ActionListener showGroupTotal = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                getStatistics();
                JOptionPane.showMessageDialog(null, "Group Total: " + totalGroups);
            }
        };

        groupTotal.addActionListener(showGroupTotal);

        // openUserView action
        ActionListener openUserViewAction = new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e){
                Object component = selectedNode.getUserObject();
                if(component instanceof User){
                    User user = (User) component;
                    UserView userView = new UserView(user, users);
                }
            }
        };

        openUserView.addActionListener(openUserViewAction);

        // showMessagesTotal action
        ActionListener showMessagesTotal = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                getStatistics();
                JOptionPane.showMessageDialog(null, "Total messages posted by users: " + totalMessages);
            }
        };

        messagesTotal.addActionListener(showMessagesTotal);

        // showPositiveMessages action
        ActionListener showPositiveMessagesTotal = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                getStatistics();
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                double result = Double.parseDouble(decimalFormat.format((double) totalPositiveMessages / (double) totalMessages));
                int finalPositivePercentage = (int) (result * 100);
                JOptionPane.showMessageDialog(null, "Percent of messages that are positive: " + finalPositivePercentage + "%");
            }
        };

        positivePercentage.addActionListener(showPositiveMessagesTotal);

        // validate ids action
        ActionListener showIDValidation = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e ){
                if(validIDs){
                    JOptionPane.showMessageDialog(null, "All IDs are valid.");
                }
                else{
                    JOptionPane.showMessageDialog(null, "All IDs are not valid.");
                }
            }
        };

        validateIDs.addActionListener(showIDValidation);

        // last updated user action
        ActionListener showLastUpdatedUser = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e ){
                JOptionPane.showMessageDialog(null, "Latest Updated User: " + latestUpdatedUser);
            }
        };

        lastUpdatedUser.addActionListener(showLastUpdatedUser);

        // tree selection listener
        treeView.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e){
                selectedNode = (DefaultMutableTreeNode) treeView.getLastSelectedPathComponent();
            }
        });

        // add mainPanel to frame
        frame.add(mainPanel);

        // make window visible to user
        frame.setTitle("Admin Control Panel");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
