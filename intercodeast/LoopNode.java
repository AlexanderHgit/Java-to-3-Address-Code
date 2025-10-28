package intercode.intercodeast ;

import intercode.visitor.* ;
import intercode.ast.*;
public class LoopNode extends StatementNode{
    public int loopNumber;
    StatementNode stmt;
    public LoopNode(){

    }
    public LoopNode(int n){
        this.loopNumber=n;
     
    }


    public void accept(ASTVisitor v){
        v.visit(this);
    }
}
