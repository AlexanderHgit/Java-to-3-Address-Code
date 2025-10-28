package intercode.ast ;

import intercode.lexer.*;
import intercode.visitor.ASTVisitor;

public class ExprNode extends Node {

    public Type type=null;
    public ExprNode(){

    }

    public void accept(ASTVisitor v){
        v.visit(this);
    }
}


