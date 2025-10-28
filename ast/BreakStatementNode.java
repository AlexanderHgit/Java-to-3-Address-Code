package intercode.ast;
import intercode.visitor.*;
import intercode.lexer.*;
import intercode.parser.*;
public class BreakStatementNode extends StatementNode {


public BreakStatementNode() {
}


public void accept (ASTVisitor v) {
v.visit(this);
}
}
