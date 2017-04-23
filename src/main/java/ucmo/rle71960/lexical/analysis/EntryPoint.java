package ucmo.rle71960.lexical.analysis;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import ucmo.rle71960.lexical.analysis.lexer.*;
import ucmo.rle71960.lexical.analysis.lexer.tokens.EofToken;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * lexical-analyzer
 * <p>
 * Copyright (c) 2017 rle71960
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class EntryPoint {

    static Map<TokenType, List<Token>> tokens;
    static {
        tokens = new HashMap<>();
        for ( TokenType type : TokenType.values() ) {
            tokens.put(type, new ArrayList<>());
        }
    }

    public static void main(String[] args) {
        if ( args.length < 1 ) {
            System.out.println("Usage: java -jar <this-jar> [path-to-file | string]");
            System.exit(1);
        }
        EntryPoint ep = new EntryPoint();
        final int ONLY_THE_FIRST = 0;
        // TODO delete Result class?
        // TODO register listeners for our token types
        // TODO print from token type listeners
        run(args[ONLY_THE_FIRST]);
        ep.printOperators();
        ep.printIds();
        ep.printNum();
        ep.printErrors();
    }

    public static void run(String pathOrText) {
        Source toScan;
        try {
            toScan = new Source(maybeFileToString(pathOrText));
            Lexer lexer = new Lexer(toScan);
            Token token = lexer.getToken();
            while ( token.getType() != TokenType.END_OF_FILE ) {
                // TODO add to lists or something
                tokens.get(token.getType()).add(token);
                token = lexer.getToken();
            }
        }
        catch(IOException e) {
            System.out.println("Couldn't read file '" + pathOrText + "'.");
            e.printStackTrace();
        }
    }

    private static BufferedReader maybeFileToString(String maybeFile) throws IOException {
        File file = new File(maybeFile);
        if ( file.exists() && file.isFile() ) {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            return new BufferedReader(isr);
        }
        else {
            ByteArrayInputStream is = new ByteArrayInputStream(maybeFile.getBytes());
            return new BufferedReader(new InputStreamReader(is));
        }
    }

    public void printOperators() {
        System.out.println("Operators, punctuations, and reserved words:");
        List<Token> operatorTokens = tokens.get(TokenType.OPERATOR);
        for (Token token : operatorTokens) {
            System.out.print(token.getText() + " ");
        }
        System.out.println("\n");
    }

    public void printIds() {
        System.out.println("id:");
        List<Token> idTokens = tokens.get(TokenType.ID);
        for (Token token : idTokens) {
            System.out.print(token.getText() + " ");
        }
        System.out.println("\n");
    }

    public void printNum() {
        System.out.println("num:");
        List<Token> numTokens = tokens.get(TokenType.NUM);
        for (Token token : numTokens) {
            System.out.print(token.getText() + " ");
        }
        System.out.println("\n");
    }

    public void printErrors() {
        System.out.println("error token:");
        List<Token> errorTokens = tokens.get(TokenType.ERROR);
        for (Token token : errorTokens) {
            System.out.print(token.getText() + " ");
        }
        System.out.println("\n");
    }
}
