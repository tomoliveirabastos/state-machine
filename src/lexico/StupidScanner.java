package lexico;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StupidScanner {
    private char[] content;
    private int estado;
    private int pos = 0;
    private int line = 0;
    private int column = 0;

    public StupidScanner(String filename) {

        try {

            String textContent;

            textContent = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);

            System.out.println("DEBUG");
            System.out.println(textContent);
            System.out.println("------");

            content = textContent.toCharArray();
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
    private boolean isDigit(char c) {

        return c >= '0' && c <= '9';
    }

    private boolean isChar(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }


    public boolean isOperator(char c) {

        return c == '>' || c == '<' || c == '=' || c == '!' || c == '+' || c == '-' || c == '/' || c == '*';
    }

    private boolean isSpace(char c) {
        if(c == '\n' || c == '\r') {
            line++;
            column = 0;
        }

        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }


    private char nextChar() {
        column++;

        return content[pos++];
    }

    private boolean isEOF() {

        return pos == content.length - 1;
    }

    public Token nextToken() throws Exception {
        char currentChar;
        Token token;
        String appendText = "";
        estado = 0;

        while (true) {
            if (isEOF()) {
                return null;
            }

            currentChar = nextChar();

            switch (estado) {
                case 0:
                    if (isChar(currentChar)) {
                        appendText += currentChar;
                        estado = 1;
                    } else if (isDigit(currentChar)) {
                        estado = 3;
                        appendText += currentChar;
                    } else if (isSpace(currentChar)) {
                        estado = 0;
                    } else if (isOperator(currentChar)) {
                        estado = 5;
                    } else {
                        throw new Exception("Error simbol");
                    }
                    break;
                case 1:
                    if (isChar(currentChar) || isDigit(currentChar)) {
                        estado = 1;
                        appendText += currentChar;
                    } else if (isSpace(currentChar) || isOperator(currentChar)) {

                        estado = 2;
                    } else {

                        throw new Exception("Malformed identifier");
                    }
                    break;
                case 2:
                    back();
                    token = new Token();
                    token.setType(Token.TK_IDENTIFIER);
                    token.setText(appendText);
                    return token;

                case 3:
                    if (isDigit(currentChar)) {
                        estado = 3;
                        appendText += currentChar;
                    } else if (!isChar(currentChar)) {
                        appendText += currentChar;
                        estado = 4;
                    } else {
                        throw new Exception("Not number");
                    }
                    break;
                case 4:
                    token = new Token();
                    token.setType(Token.TK_NUMBER);
                    token.setText(appendText);
                    back();
                    return token;
                case 5:
                    appendText += currentChar;
                    token = new Token();
                    token.setType(Token.TK_OPERATOR);
                    token.setText(appendText);
                    return token;
            }
        }
    }

    private void back() {
        pos--;
    }
}

