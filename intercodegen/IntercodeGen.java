package intercode.intercodegen;
import intercode.parser.*;
import intercode.visitor.*;
import intercode.ast.*;
import java.util.*;
import intercode.lexer.*;
import intercode.intercodeast.*;
public class IntercodeGen extends ASTVisitor{

    public Parser parser=null;
   
    public BlockStatementNode mainLoopBlock=new BlockStatementNode(null);
     public List<StatementNode> stmts= new ArrayList<StatementNode>();
      public List<Token> stmtOperators= new ArrayList<Token>();
      public List<Node> stmtOperands= new ArrayList<Node>();
            public List<Token> assignOperators= new ArrayList<Token>();
      public List<Node> assignOperands= new ArrayList<Node>();
      public int loop_scope;
    int level = 0;
    String indent="...";
    int loop=0;
    int temp=0;
    boolean equal_op=false;
    public IntercodeGen(Parser parser){
        this.parser = parser;
        visit(this.parser.cu);
    }
    public IntercodeGen(){
        visit(this.parser.cu);
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
int indent_level=0;
void indentUp(){
   indent_level++;
}
void indentDown(){
    indent_level--;
}
void printIndent(){
    String s ="";
    for(int i=0; i<indent_level; i++){
        s+=" ";
    }
    print(s);
}
void printDotDotDot(){
    String s="";
    for(int i=0;i<indent_level;i++)
    s+="...";

    print(s);
}

public void visit(CompilationUnit n){

       println("INTERCODE GENERATION");
        
        level++;

        n.block.accept(this) ;
        level--;
   
 System.out.println(stmts);
}
public void visit(BlockStatementNode n){
    
    printIndent();
       println("{");
    indentUp();
           for(DeclarationNode decl :n.decls)
        decl.accept(this);
    indentDown();

    indentUp();
           for(StatementNode stmt:n.stmts)
        stmt.accept(this);
    indentDown();
    printIndent();
   println("}");
}

public void visit(DeclarationNode n){
    n.type.accept(this);
    print(" ");
    n.id.accept(this);
    println(";");
}
/* public void visit(Declarations n){
    if(n.decls!=null){
        n.decl.accept(this);
        n.decls.accept(this);
    }
} */
public void visit(TypeNode n){
    printIndent();
    print(n.basic.toString());
    if(n.array != null)
    n.array.accept(this);
}
public void visit(ArrayTypeNode n){
    print("[");
    print(""+n.size);
    print("]");
    if(n.type!=null)
    n.type.accept(this);
}
/* public void visit(Statements n){
    if(n.stmts != null){
        n.stmt.accept(this);
        n.stmts.accept(this);
    }
} */
public void visit(StatementNode n){

}
public void visit (ParenthesesNode n) { 
print("(");
n.expr.accept(this);
print(")");
}

public void visit (IfStatementNode n) {
    int elseloop=++loop;
    int endifloop=++loop;
    printIndent();
    print("if ");
    equal_op=false;
    n.cond.accept(this);

    println("");
   equal_op=false;
    IfFalseNode ifFalseNode=new IfFalseNode(n.cond,new GotoNode(elseloop));
   stmts.add(ifFalseNode);
    if (!(n.stmt instanceof BlockStatementNode))
        indentUp();
        if((n.stmt instanceof BlockStatementNode))
        loop_scope=endifloop;
    n.stmt.accept(this);

    if(!(n.stmt instanceof BlockStatementNode))
        indentDown();
    if((n.stmt instanceof BlockStatementNode))
        indentDown();
    if(n.else_stmt !=null){
    GotoNode gotoend= new GotoNode(endifloop);
       stmts.add(gotoend);
       
        printIndent();
        print("else ");
        println("");

    }
    LoopNode stmtloopNode= new LoopNode(elseloop);
        stmts.add(stmtloopNode);
    if(n.else_stmt !=null){
                if(!(n.else_stmt instanceof BlockStatementNode))
            indentUp();
           if((n.else_stmt instanceof BlockStatementNode))
        loop_scope=endifloop;
        n.else_stmt.accept(this);
        if(!(n.else_stmt instanceof BlockStatementNode))
            indentDown();
                LoopNode stmtloopNode1= new LoopNode(endifloop);
        stmts.add(stmtloopNode1);
    }
    
}
public void visit (WhileStatementNode n) {
 
    int ln1=++loop;
    int ln2=++loop;
    printIndent();
    print("while ");
    n.cond.accept(this);
    LoopNode stmtloopNode= new LoopNode(ln1);
        stmts.add(stmtloopNode);
    IfFalseNode ifFalseNode=new IfFalseNode(n.cond,new GotoNode(ln2));
   stmts.add(ifFalseNode);
    println("");
    if (!(n.stmt instanceof BlockStatementNode))
        indentUp();
            if((n.stmt instanceof BlockStatementNode))
        loop_scope=ln2;
    n.stmt.accept(this);
 
    GotoNode gotostart= new GotoNode(ln1);
       stmts.add(gotostart);
    LoopNode stmtloopNode2= new LoopNode(ln2);
        stmts.add(stmtloopNode2);
 

    if(!(n.stmt instanceof BlockStatementNode))
        indentDown();
    

}
public void visit (DoWhileStatementNode n) {
    int doloop=++loop;
    int outloop=++loop;
    printIndent();
    print("do ");
    println("");
    if (!(n.stmt instanceof BlockStatementNode))
        indentUp();
            LoopNode stmtloopNode= new LoopNode(doloop);
        stmts.add(stmtloopNode);
                    if((n.stmt instanceof BlockStatementNode))
        loop_scope=outloop;
    n.stmt.accept(this);
    if(!(n.stmt instanceof BlockStatementNode))
        indentDown();
    
    //println("");
    printIndent();
    print("while");
      equal_op=false;
    n.cond.accept(this);
  
        IfNode ifNode=new IfNode(n.cond,new GotoNode(doloop));
   stmts.add(ifNode);

    println(";");

}
public void visit(ArrayAccessNode n){
   // stmtOperators.add(new Token('['));
    //stmtOperands.add(n.id);
    //stmtOperands.add(n.index);

    n.id.accept(this);
    n.index.accept(this);
    //System.out.println(equal_op);
        if(!equal_op){

        TempNode tempNode=new TempNode(++temp);
         AssignmentNode assignNode=new AssignmentNode(tempNode,n);
         stmts.add(assignNode);
        }
    
}
public void visit(ArrayDimsNode n){
    print("[");
    n.size.accept(this);
    print("]");
    TempNode tempNode=new TempNode(++temp);
    AssignmentNode assignNode=new AssignmentNode(tempNode,new BinExprNode(new Token('*'),n.size,new NumNode(new Num(8))));
    stmts.add(assignNode);
    n.size=tempNode;
    if(n.dim!=null)
        n.dim.accept(this);
}
public void visit(AssignmentNode n){
    printIndent();
    equal_op=true;
   stmtOperands.clear();
   stmtOperators.clear();

    n.left.accept(this);
   
    print(" = ");


if(n.right instanceof IdentifierNode){
    
        ((IdentifierNode)n.right).accept(this);
       }
    else if (n.right instanceof NumNode){
      
        ((NumNode)n.right).accept(this);
        }else if(n.right instanceof RealNode){ 

        ((RealNode)n.right).accept(this);

        }else if(n.right instanceof ArrayAccessNode){
            ((ArrayAccessNode)n.right).accept(this);
            if(n.left instanceof IdentifierNode){

            }else{
              TempNode t=new TempNode(++temp);
              AssignmentNode ass=new AssignmentNode(t,n.right);
              stmts.add(ass);
              n.right=t;
            }
        }
            else if(n.right instanceof BinExprNode){
            ((BinExprNode)n.right).accept(this);
        }else{

        }
    /* 
    println(" ;");
    stmtOperators.add(0,new Token('='));
    stmtOperands.add(0,n.left);
    if(stmtOperands.get(0) instanceof ArrayAccessNode){
        TempNode tempNode=new TempNode(temp);
         AssignmentNode assignNode=new AssignmentNode(tempNode,stmtOperands.get(0));
         stmts.add(assignNode);
        stmtOperands.remove(0);
        stmtOperands.remove(0);
        stmtOperands.remove(0);
        stmtOperators.remove(1);
       stmtOperands.add(0,tempNode);
    }

     if(stmtOperators.size() > 0){
         int last_temp_node=temp;
         int temp_offset=0;
        while(stmtOperators.size() != 0){
           
                    //System.out.println(stmtOperands);
  //   System.out.println(stmtOperators);      

System.out.println(stmtOperators.get(stmtOperators.size()-1).toString()+"current op"); 
System.out.println(stmtOperators.get(stmtOperators.size()-1).toString().equals("=")); 
            if(!stmtOperators.get(stmtOperators.size()-1).toString().equals("=")){
                 System.out.println(stmtOperands);
                 System.out.println(stmtOperators);
                System.out.println("start parse***********");
            
                if(stmtOperators.get(stmtOperators.size()-1).toString().equals("[")){
                    TempNode tempNode=new TempNode(++last_temp_node);
                        System.out.println("start array parse***********");
                     
                        IdentifierNode id=new IdentifierNode();
                        ArrayDimsNode dim=new ArrayDimsNode();
                        boolean remove= false;
                        int removed=0;
                        for(int i=stmtOperands.size()-1; i>=0;i--){
                                System.out.println("start array loop***********");
                            System.out.println("iteration: "+i+ " size: "+(stmtOperands.size()-1));
                              if(removed>=2){
                                removed=0;
                                break;
                              }
                            if(stmtOperands.get(i) instanceof IdentifierNode){
                            if(stmtOperands.get(i) instanceof TempNode){

                            }else{
                                id=(IdentifierNode)stmtOperands.get(i);
                                remove=true;
                            }}
                            if(stmtOperands.get(i) instanceof ArrayDimsNode){
                                dim=(ArrayDimsNode)stmtOperands.get(i);
                                remove=true;
                            }
                             System.out.println("removed = "+removed);
                            if(remove){
                                System.out.println(stmtOperands);
                                stmtOperands.remove(i);
                                System.out.println(stmtOperands);
                                ++removed;
                            }
                        }
                          System.out.println("end array loop***********");
                    ArrayAccessNode arr=new ArrayAccessNode(id,dim);
                    System.out.println(stmtOperands);
                     AssignmentNode assignNode=new AssignmentNode(tempNode,arr);
                  
                     stmts.add(assignNode);
                stmtOperands.add(tempNode);
                  System.out.println(stmtOperands);
                stmtOperators.remove(stmtOperators.size()-1);
                

                }else{
                TempNode tempNode=new TempNode(++last_temp_node);
                BinExprNode binNode=new BinExprNode(stmtOperators.get(stmtOperators.size()-1),(ExprNode)stmtOperands.get(stmtOperands.size()-2),(ExprNode)stmtOperands.get(stmtOperands.size()-1));
               // System.out.println(stmtOperators.size());
               // System.out.println(stmtOperands.size());
                stmtOperands.remove(stmtOperands.size()-1);
                stmtOperands.remove(stmtOperands.size()-1);
                stmtOperands.add(tempNode);
                stmtOperators.remove(stmtOperators.size()-1);
                AssignmentNode assignNode=new AssignmentNode(tempNode,binNode);
                stmts.add(assignNode);
                }
            }else{
                  
                    System.out.println("start equal parse***********");
            AssignmentNode assignNode=new AssignmentNode((ExprNode)stmtOperands.get(0),(ExprNode)stmtOperands.get(1));
            stmtOperators.remove(stmtOperators.size()-1);
            stmts.add(assignNode);

            }
         }
        
        
        
     }

   stmtOperators= new ArrayList<Token>();
   stmtOperands= new ArrayList<Node>(); */
   stmts.add(n);
   print(";");
   println("");
}
public void visit(BreakStatementNode n){
    GotoNode breaks=new GotoNode(loop_scope);
    stmts.add(breaks);
  printIndent();
  println("break ;");
}
public void visit(TrueNode n){

    print("true");
}
public void visit(FalseNode n){

    print("false");
}
public void visit(BinExprNode n){
        if(n.left instanceof ParenthesesNode)
            ((ParenthesesNode)n.left).accept(this);
        if(n.left instanceof IdentifierNode){
    
        ((IdentifierNode)n.left).accept(this);
       }
    else if (n.left instanceof NumNode){
      
        ((NumNode)n.left).accept(this);
        }else if(n.left instanceof RealNode){ 

        ((RealNode)n.left).accept(this);

        }
            else if(n.left instanceof ArrayAccessNode){
                 ((ArrayAccessNode)n.left).accept(this);
                TempNode t=new TempNode(temp);
                n.left=t;
           
        }else if(n.left instanceof BinExprNode){
            ((BinExprNode)n.left).accept(this);
        }else{

        }
        if(n.op !=null)
            print(" "+n.op.toString()+" ");
        if(n.right != null){
            //stmtOperands.add(n.left);
            //stmtOperators.add(n.op);
            //stmtOperands.add(n.right);
        if(n.right instanceof ParenthesesNode)
            ((ParenthesesNode)n.right).accept(this);
        if(n.right instanceof IdentifierNode){
    
        ((IdentifierNode)n.right).accept(this);
       }
    else if (n.right instanceof NumNode){
      
        ((NumNode)n.right).accept(this);
        }else if(n.right instanceof RealNode){ 

        ((RealNode)n.right).accept(this);

        }
            else if(n.right instanceof ArrayAccessNode){
            ((ArrayAccessNode)n.right).accept(this);
        }else if(n.right instanceof BinExprNode){
            ((BinExprNode)n.right).accept(this);
        }else{

        }



        }
  
    
}
public void visit(IdentifierNode n){
   print(n.id);
}
public void visit(NumNode n){
    print(""+n.value);
}
public void visit(RealNode n){
    print(""+n.value);
}
}