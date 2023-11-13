import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.List;


public class AdminControlPanel extends JFrame{
    private List<User> users;
    private List<UserGroup> groups;
    private DefaultMutableTreeNode selectedNode;
    private JTree treeView;
    private DefaultTreeModel treeModel;
    // Constructor for AdminControlPanel
    public AdminControlPanel() {
        this.users = new ArrayList<>();
        this.groups = new ArrayList<>();
        initialize(); // Call the initialization method in the constructor
    }

    private void addChildNode(DefaultMutableTreeNode parentNode, Component component){
        Object parentObject = parentNode.getUserObject();

        // if the parent (selected node) is a userGroup
        if(parentObject instanceof UserGroup){
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(component);

            treeModel.insertNodeInto(childNode, parentNode, parentNode.getChildCount());

            treeModel.nodeStructureChanged(parentNode);
        }

        // if the parent (selected node) is a user
        else{
            JOptionPane.showMessageDialog(null, "Error: Can only add users and groups to groups.");
        }
    }

    // set up components
    public void initialize() {
        JFrame frame = new JFrame();

        // create root group
        UserGroup root = new UserGroup("Root");
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(root);
        treeModel = new DefaultTreeModel(rootNode);
        treeView = new JTree(treeModel);
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

        // addUser action
        ActionListener addedUser = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                User user = new User(userID.getText());
                userID.setText("");
                users.add(user);
                addChildNode(selectedNode, user);
            }
        };

        addUser.addActionListener(addedUser);

        // addGroup action
        ActionListener addedGroup = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                UserGroup group = new UserGroup(groupID.getText());
                groupID.setText("");
                groups.add(group);
                addChildNode(selectedNode, group);
            }
        };

        addGroup.addActionListener(addedGroup);

        // showUserTotal action
        ActionListener showUserTotal = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(null, "User Total: " + users.size());
            }
        };

        userTotal.addActionListener(showUserTotal);

        // showGroupTotal action
        ActionListener showGroupTotal = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(null, "Group Total: " + groups.size());
            }
        };

        groupTotal.addActionListener(showGroupTotal);

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
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
