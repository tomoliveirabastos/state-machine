package parser;

import lexico.StupidScanner;
import lexico.Token;

import java.rmi.server.ExportException;

public class StupidParser {

    private StupidScanner scanner;
    private Token token;

    //receive lexical analyser from contructor like parameter cause it will invoke on demand
    public StupidParser(StupidScanner scanner) {
        this.scanner = scanner;
    }

    public void E() throws Exception {
        T();
        EL();
    }

    public void EL() throws Exception {

        token = scanner.nextToken();
        if (token != null) {
            OP();
            T();
            EL();
        }
    }

    public void T() throws Exception {
        token = scanner.nextToken();

        if (token.getType() != 0 && token.getType() != 1) {
            throw new Exception("Invalid character");
        }

    }

    public void OP() throws Exception {

        if(token.getType() != Token.TK_OPERATOR) {
            throw new Exception("Expected operator");
        }
    }
}
