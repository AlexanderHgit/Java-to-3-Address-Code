package intercode.ast;
import intercode.visitor.*;

import intercode.lexer.*;
import intercode.parser.*;
public class TrueNode extends ExprNode {

    public TrueNode(){
        
    }


    
    public void accept(ASTVisitor v){
        v.visit(this);
    }
}
