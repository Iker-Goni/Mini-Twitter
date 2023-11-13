import javax.swing.tree.DefaultMutableTreeNode;

public class CustomTreeNode extends DefaultMutableTreeNode{
    public CustomTreeNode(Component component){
        super(component);
    }

    @Override
    public String toString(){
        return getUserObject().toString();
    }
}
