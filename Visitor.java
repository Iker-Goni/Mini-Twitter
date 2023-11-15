public interface Visitor {
    // visit user
    void visit(User user); 
    
    // visit user group
    void visit(UserGroup userGroup);
}
