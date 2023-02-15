import lexico.StupidScanner;
import lexico.Token;
import parser.StupidParser;

public class Main {
    public static void main(String[] args)  {

        StupidScanner scan = new StupidScanner("tom.stupid");
        StupidParser parser = new StupidParser(scan);

        try {

            parser.E();
            System.out.println("Compiled");

        } catch(Exception e) {

            System.out.println(e.getMessage() + " - line: " + scan.getLine() + ", column: " + scan.getColumn());
        }

    }
}