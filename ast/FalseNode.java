package intercode.ast;
import intercode.visitor.*;

import intercode.lexer.*;

public class FalseNode extends ExprNode {

    public FalseNode(){

    }


    
    public void accept(ASTVisitor v){
        v.visit(this);
    }
}
