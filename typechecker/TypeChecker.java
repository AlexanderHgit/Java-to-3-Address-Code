package intercode.typechecker;
import intercode.lexer.*;
import intercode.parser.*;
import intercode.ast.*;
import intercode.visitor.*;
public class TypeChecker extends ASTVisitor{
    public Parser parser = null;
    public CompilationUnit cu= null;
    int level=0;
     public Env top=null;
     public boolean canBreak=false;
    public TypeChecker(Parser parser){
        this.parser=parser;
        cu=parser.cu;
        visit(cu);

    }
    public TypeChecker(){
        visit(this.parser.cu);
    }
    void error(String s){
        println("Error "+s);
        exit(1);
    }
    void exit(int n){
        System.exit(n);
    }
    void print(String s){
        System.out.print(s);
    }
    void println(String s){
        System.out.println(s);
    }
    void printSpace(String s){
        System.out.print(" ");
    }

public void visit(CompilationUnit n){
    println("***************************");
    println("*   TypeChecker starts    *");
    println("***************************");
    println("");
    println("CompilationUnit");

    n.block.accept(this);

}
public void visit(BlockStatementNode n){
    //probably just make this class have its own environment
    n.sTable=top;
    top=new Env(top);
    println("BlockStatementNode");
        for(DeclarationNode decl :n.decls)
        decl.accept(this);
        for(StatementNode stmt:n.stmts)
        stmt.accept(this);
 
    top=n.sTable;
  
}
/* public void visit(Declarations n){
    if(n.decls!=null){
        n.decl.accept(this);
        n.decls.accept(this);

    }


} */
public void visit(DeclarationNode n){

    System.out.println("DeclarationNode");

    n.type.accept(this);

    n.id.accept(this);
 
    top.put(n.id.w,n.id);
    IdentifierNode table_id=(IdentifierNode)top.get(((IdentifierNode)n.id).w);
    println("In TypeChecker ID: "+table_id.id+", Declaration type: "+table_id.type);
}
public void visit(TypeNode n){

    System.out.println("TypeNode: "+n.basic);

    if(n.array != null){
       
        n.array.accept(this);
     

    }
}
public void visit(ArrayTypeNode n){

    System.out.println("ArrayTypeNode: "+n.size);

    if(n.type != null){
   
        n.type.accept(this);
 


    }


}
public void visit(Statements n){
    if(n.stmts != null){
        n.stmt.accept(this);
        n.stmts.accept(this);
    }
    
}

public void visit (ParenthesesNode n) { 

System.out.println("ParenthesesNode");
n.expr.accept(this);

}

public void visit (IfStatementNode n) {
   
    System.out.println("IfStatementNode");
   
    n.cond.accept(this);
 
    n.stmt.accept(this);
   
    if (n.else_stmt != null) {
        
        println("Else Clause");
        
        n.else_stmt.accept(this);
       
    }

}
public void visit (WhileStatementNode n) {
    
    System.out.println("WhileStatementNode");

    n.cond.accept(this);
    if(n.stmt instanceof BlockStatementNode){
        canBreak=true;
        n.stmt.accept(this);
        canBreak=false;
    }else{
        n.stmt.accept(this);
    }

 
}
public void visit (DoWhileStatementNode n) {

    System.out.println("DoWhileStatementNode");
    if(n.stmt instanceof BlockStatementNode){
        canBreak=true;
        n.stmt.accept(this);
        canBreak=false;
    }else{
        n.stmt.accept(this);
    }
    n.stmt.accept(this);
      System.out.println(canBreak);


    n.cond.accept(this);

}
public void visit(ArrayAccessNode n){

    println("ArrayAccessNode");

    n.id.accept(this);
   

    n.index.accept(this);

}
public void visit(ArrayDimsNode n){
  
    println("ArrayDimsNode");

    
    n.size.accept(this);
 

    if(n.dim != null){
   
        n.dim.accept(this);

    }
}
public void visit(BreakStatementNode n){
    println("BreakStatementNode");
    if(!canBreak){
        error("BreakStatementNode: Break is not encased in a while or dowhile loop");
    }
}
public void visit(TrueNode n){
   
    println("TrueNode");
}
public void visit(FalseNode n){
   
    println("FalseNode");
}
public void visit(AssignmentNode n){
  
    println("AssignmentNode");
      IdentifierNode rightId=null;
      IdentifierNode leftId=null;
      IdentifierNode table_idl=null;
      Type leftType=null;
    n.left.accept(this);
    if(n.left instanceof IdentifierNode){
    leftId=(IdentifierNode)n.left;
    System.out.println(leftId.w);
    top.printStuff();
    Word waw=new Word("i",Tag.BASIC);

    table_idl=(IdentifierNode)top.get(leftId.w);
    
System.out.println(table_idl);
    }else if(n.left instanceof ArrayAccessNode){
            leftId=((ArrayAccessNode)n.left).id;
            table_idl=(IdentifierNode)top.get(leftId.w);
            
            leftType=table_idl.type;
        }
  
    if(table_idl==null){
        error("AssignmentNode: identifier "+leftId.id+" is not declared");
    }
    leftType = table_idl.type;
    println("In TypeChecker, AssignmentNode's left type: "+leftType);
    


    Type rightType =null;

        if(n.right instanceof IdentifierNode){
            
        ((IdentifierNode)n.right).accept(this);
        IdentifierNode table_idr=(IdentifierNode)top.get(((IdentifierNode)n.right).w);
        rightId=table_idr;
        rightType=rightId.type;
       }
    else if (n.right instanceof NumNode){
      

        rightType=Type.Int;
        ((NumNode)n.right).accept(this);
        }else if(n.right instanceof RealNode){ 
            rightType=Type.Float;
        ((RealNode)n.right).accept(this);

        }else if(n.right instanceof ArrayAccessNode){
            ((ArrayAccessNode)n.right).accept(this);
            rightId=((ArrayAccessNode)n.right).id;
            IdentifierNode table_idr=(IdentifierNode)top.get(rightId.w);
            
            rightType=table_idr.type;
        }
            else{
            ((BinExprNode)n.right).accept(this);
            
            rightType=((BinExprNode)n.right).type;
        }
    
  if(leftType==Type.Int)
  println("*********** leftType is Type.Int");
  if(leftType!= rightType){
    if(leftType==null){
        error("AssignmentNode: left identifier |"+leftId.id+"| not declared ");
    }
    if(rightType==null){
        error("AssignmentNode: right identifier |"+rightId.id+"| not declared ");
    }
    if(leftType==Type.Int){
    error("AssignmentNode: variable "+leftId.id+" is incompatible with type "+rightType);
    }
  }

}
public void visit(BinExprNode n){

    System.out.println("BinExprNode: "+n.op);
  Type leftType=null;
  IdentifierNode leftId=null;
        if(n.left instanceof IdentifierNode){
    
        ((IdentifierNode)n.left).accept(this);
        leftId=(IdentifierNode)top.get(((IdentifierNode)n.left).w);
        leftType=leftId.type;
       }
    else if (n.left instanceof NumNode){
        leftType=Type.Int;
        ((NumNode)n.left).accept(this);
        }else if(n.left instanceof RealNode){ 
        leftType=Type.Float;
        ((RealNode)n.left).accept(this);

        }
            else if(n.left instanceof ArrayAccessNode){
            ((ArrayAccessNode)n.left).accept(this);
        }else if(n.left instanceof ParenthesesNode){
            ((ParenthesesNode)n.left).accept(this);}
            else{
            ((BinExprNode)n.left).accept(this);
            leftType=((BinExprNode)n.left).type;
        }
        Type rightType=null;
    if(n.right != null){
        if(n.right instanceof IdentifierNode){
    
        ((IdentifierNode)n.right).accept(this);
        IdentifierNode rightId=(IdentifierNode)top.get(((IdentifierNode)n.right).w);
        if(rightId==null){
            error("BinExprnode: "+ ((IdentifierNode)n.right).id+" is not declared");
        }
        rightType=rightId.type;

       }
    else if (n.right instanceof NumNode){
  
        rightType=Type.Int;
        ((NumNode)n.right).accept(this);
       
        }else if(n.right instanceof RealNode){ 
            rightType=Type.Float;
        ((RealNode)n.right).accept(this);

        }
            else if(n.right instanceof ArrayAccessNode){
            ((ArrayAccessNode)n.right).accept(this);
        }else if(n.right instanceof ParenthesesNode){
            ((ParenthesesNode)n.right).accept(this);}
            else{
            ((BinExprNode)n.right).accept(this);
            rightType=((BinExprNode)n.right).type;
            }

    }else{
           
        
    }

            if(leftType==Type.Float || rightType==Type.Float){
                n.type=Type.Float;
            }else{
                n.type=Type.Int;
            }
        

    
}
public void visit(IdentifierNode n){
    
    n.printNode();
    //println(" ;");
}
public void visit(NumNode n){
  
    n.printNode();
}
public void visit(RealNode n){
 
    n.printNode();
}
}