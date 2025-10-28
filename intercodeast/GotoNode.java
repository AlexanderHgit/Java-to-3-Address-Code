package intercode.intercodeast ;

import intercode.visitor.* ;
import intercode.ast.*;
public class GotoNode extends StatementNode{
    public int loop;
    public GotoNode(){

    }

    public GotoNode(int l){
        this.loop=l;

    }


    public void accept(ASTVisitor v){
        v.visit(this);
    }
}
