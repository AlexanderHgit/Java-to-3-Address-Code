package intercode.ast ;

import intercode.visitor.* ;

public class AssignmentNode extends StatementNode {

    public ExprNode  left  ;
    public Node right ;

    public AssignmentNode () {
        
    }

    public AssignmentNode (IdentifierNode id,NumNode num) {
        this.left=id;
        this.right=num;
    }
        public AssignmentNode (ExprNode  left,Node right ) {
        this.left=left;
        this.right=right;
    }
    public void accept(ASTVisitor v) {

        v.visit(this);
    }
}
