package intercode.ast ;
import intercode.lexer.*;
import intercode.visitor.* ;
public class TypeNode extends Node{
    public Type basic;
    public ArrayTypeNode array=null;
    public TypeNode(){
  
    }
    public TypeNode(Type basic){
        this.basic=basic;
    }
    public TypeNode(Type basic,ArrayTypeNode array){
        this.basic=basic;
        this.array=array;
    


    }

    public void accept(ASTVisitor v){
         
         v.visit(this);
    }
}