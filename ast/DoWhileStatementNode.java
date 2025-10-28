package intercode.ast;
import intercode.visitor.*;
import intercode.lexer.*;
public class DoWhileStatementNode extends StatementNode {

public ParenthesesNode cond;
public StatementNode stmt;

public DoWhileStatementNode() {
}
public DoWhileStatementNode ( StatementNode stmt,ParenthesesNode cond) {
this.cond= cond;
this.stmt= stmt ;

}


public void accept (ASTVisitor v) {
v.visit(this);
}
}
