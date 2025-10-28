package intercode.lexer ;
import intercode.lexer.*;
import java.io.* ;
import java.util.* ;
import intercode.*;
public class Lexer {
    private FileInputStream in;
    private BufferedInputStream bin;
    public int line = 1 ;
    private char peek = ' ' ;
    private Hashtable<String, Word> words = new Hashtable<String, Word>() ;
    //private Hashtable words = new Hashtable() ;

    public Lexer () {
              reserve( new Word("if",    Tag.IF)    );
      reserve( new Word("else",  Tag.ELSE)  );
      reserve( new Word("while", Tag.WHILE) );
      reserve( new Word("do",    Tag.DO)    );
      reserve( new Word("break", Tag.BREAK) );
reserve( Type.Int );
reserve( Type.Char );
reserve( Type.Bool );
reserve( Type.Float);
        reserve(Word.True) ;
        reserve(Word.False) ;
            reserve(Word.eof);

        setupIOSrteam();
    }

    void reserve (Word w) {

        words.put(w.lexeme, w) ;
    }
    void setupIOSrteam(){
        try{
            
in=new FileInputStream("input.txt");
bin=new BufferedInputStream(in);

        }catch(IOException e){
            System.out.println("IOException");

        }
    }
   void readch() throws IOException { peek = (char)bin.read(); 
   //System.out.println(peek);
   }
   boolean readch(char c) throws IOException {
      readch();
      if( peek != c ) return false;
      peek = ' ';
      return true;
   }

    public Token scan() throws IOException {

        //System.out.println("scan() in Lexer") ;

        for ( ; ; readch()) {

            if (peek == ' ' || peek == '\t') 
                continue ;
            else if (peek == '\n') 
                line = line + 1 ;
            else 
                break ;
        }
      switch( peek ) {
      case '&':
         if( readch('&') ) return Word.and;  else return new Token('&');
      case '|':
         if( readch('|') ) return Word.or;   else return new Token('|');
      case '=':
      
         if( readch('=') ){ 
            //System.out.println("op: ==");
            return Word.eq;}   else return new Token('=');
      case '!':
         if( readch('=') ) return Word.ne;   else return new Token('!');
      case '<':
         if( readch('=') ) return Word.le;   else return new Token('<');
      case '>':
         if( readch('=') ) return Word.ge;   else return new Token('>');
      }
        if (Character.isDigit(peek)) {

            int v = 0 ;

            do {

                v = 10 * v + Character.digit(peek, 10) ;
                readch() ;

            } while (Character.isDigit(peek)) ;

            //m.out.println("v: " + v) ;
            //System.out.println("n: "+v);
            
        if(peek!='.')
            return new Num(v);
        float x=v; float d=10;
        for(;;){
            readch();
            if(!Character.isDigit(peek)) break;
            x=x+Character.digit(peek,10)/d; d=d*10;
        }
        return new Real(x);
        
        }

      if( Character.isLetter(peek) ) {
         StringBuffer b = new StringBuffer();
         do {
            b.append(peek); readch();
         } while( Character.isLetterOrDigit(peek) );
         String s = b.toString();
         Word w = (Word)words.get(s);
         if( w != null ) return w;
         w = new Word(s, Tag.ID);
         words.put(s, w);
         return w;
      } 
        if((int)peek==65535){
            System.out.println("@@@@@@@@@@ EOF reached...");
            return Word.eof;
        }
        Token t = new Token(peek) ; 
       //System.out.println("t: " + t.toString()) ;
        peek = ' ' ;

        return t ;
    }
}
