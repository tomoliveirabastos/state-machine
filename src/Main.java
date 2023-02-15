import lexico.StupidScanner;
import lexico.Token;
import parser.StupidParser;

public class Main {
    public static void main(String[] args) throws Exception {

        StupidScanner scan = new StupidScanner("tom.stupid");
        StupidParser parser = new StupidParser(scan);

        parser.E();
    }
}