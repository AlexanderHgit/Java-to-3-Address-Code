package intercode ;


import intercode.lexer.* ;
import intercode.parser.* ;
import intercode.ast.*;
import intercode.unparser.*;
import intercode.typechecker.*;
import intercode.intercodegen.*;
public class Main {

    public static void main (String[] args) {

        Lexer lexer = new Lexer() ;
        Parser parser = new Parser(lexer) ;
        TreePrinter tree = new TreePrinter(parser);
        TypeChecker typechecker=new TypeChecker(parser);
        IntercodeGen tac=new IntercodeGen(parser);
        Unparser unparser = new Unparser(tac);
        
    }
}
