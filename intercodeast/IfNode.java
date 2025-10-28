package intercode.intercodeast ;

import intercode.visitor.* ;
import intercode.ast.*;
import intercode.intercodeast.*;
public class IfNode extends StatementNode{
 
public ParenthesesNode cond;
public GotoNode goton;
    public IfNode(){

    }
    public IfNode(ParenthesesNode cond,GotoNode goton){
        this.cond=cond;
        this.goton=goton;
    }


    public void accept(ASTVisitor v){
        v.visit(this);
    }
}
