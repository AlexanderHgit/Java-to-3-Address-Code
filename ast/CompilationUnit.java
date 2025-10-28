package intercode.ast ;

import intercode.visitor.* ;

public class CompilationUnit extends Node {

    //Node ast ;
    public BlockStatementNode block;
    public CompilationUnit(){}
    public CompilationUnit(BlockStatementNode block){
        this.block=block;
    }
    public void accept(ASTVisitor v){
        v.visit(this);
    }
}

