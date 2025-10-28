package intercode.ast;
import intercode.lexer.*;
import intercode.visitor.*;;
public class IdentifierNode extends ExprNode {
    public String id=null;
    public Word w;
    public TypeNode typeNode;
    public IdentifierNode(){

    }
    public IdentifierNode(Word w){
        this.id=w.lexeme;
        this.w=w;
    }
    public IdentifierNode(Word w,TypeNode typeNode){
        this.id=w.lexeme;
        this.w=w;
        this.typeNode=typeNode;
    }
        public IdentifierNode(Word w,Type type){
        this.id=w.lexeme;
        this.w=w;
        
        this.type=type;
    }
    public void accept(ASTVisitor v){
        v.visit(this);
    }
    public void printNode(){
        System.out.println("IdentifierNode: "+id);
    }
}

