import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

// mainly used to show either file or folder icon for nodes in tree view
public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public java.awt.Component getTreeCellRendererComponent(
            JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        // Check the type of the node (User or Group)
        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object userObject = node.getUserObject();

            // Set the icon based on the type
            if (userObject instanceof User) {
                setIcon(UIManager.getIcon("FileView.fileIcon"));  // Use the file icon
            } else if (userObject instanceof UserGroup) {
                setIcon(UIManager.getIcon("FileView.directoryIcon"));  // Use the directory icon
            }
        }

        return this;
    }
}
