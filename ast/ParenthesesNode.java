package intercode.ast;
import intercode.visitor.*;

import intercode.lexer.*;

public class ParenthesesNode extends ExprNode {
    public ExprNode expr;
    public ParenthesesNode(){

    }


    
    public void accept(ASTVisitor v){
        v.visit(this);
    }
}
