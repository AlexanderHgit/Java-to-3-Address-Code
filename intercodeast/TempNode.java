package intercode.intercodeast ;

import intercode.visitor.* ;
import intercode.ast.*;
public class TempNode extends IdentifierNode {

    public int  num  ;

    public TempNode () {
        
    }
    public TempNode(int num){
        this.num=num;
    }

    public void accept(ASTVisitor v) {

        v.visit(this);
    }
}
