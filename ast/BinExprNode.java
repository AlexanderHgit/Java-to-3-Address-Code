package intercode.ast ;

import intercode.lexer.*;
import intercode.visitor.ASTVisitor;



public class BinExprNode extends ExprNode {
    public ExprNode left;
    public Node right;
    public Token op;

    public BinExprNode(){

    }

    public BinExprNode(Token op,ExprNode left, Node right){

        this.left=left;
        this.right=right;
        this.op=op;

    }        
    public void accept(ASTVisitor v){
        v.visit(this);
    }
}
