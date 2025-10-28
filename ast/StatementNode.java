package intercode.ast ;

import intercode.visitor.* ;

public class StatementNode extends Node{
 
    public StatementNode(){

    }



    public void accept(ASTVisitor v){
        v.visit(this);
    }
}
