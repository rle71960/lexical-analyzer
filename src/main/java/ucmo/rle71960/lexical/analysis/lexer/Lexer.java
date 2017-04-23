package ucmo.rle71960.lexical.analysis.lexer;

import ucmo.rle71960.lexical.analysis.lexer.tokens.*;

import java.io.IOException;

import static ucmo.rle71960.lexical.analysis.lexer.Source.EOF;
import static ucmo.rle71960.lexical.analysis.lexer.Source.EOL;
import static ucmo.rle71960.lexical.analysis.lexer.TokenType.END_OF_FILE;

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
public class Lexer {

    protected Source source;
    private Token currentToken;

    public Lexer(Source s) {
        this.source = s;
    }

    public Token currentToken() {
        return currentToken;
    }

    public Token nextToken() throws IOException {
        currentToken = getToken();
        return currentToken;
    }

    // extract method
    public Token getToken() throws IOException {
        skipWhiteSpace();
        Token token;
        char currentChar = currentChar();

        if ( currentChar == EOF ) {
            token = new EofToken(source, END_OF_FILE);
        }
        else if ( Character.isLetter(currentChar) ) {
            token = new IdToken(source);
        }
        else if ( Character.isDigit(currentChar) ) {
            token = new NumToken(source);
        }
        else if ( TokenType.OPERATORS.containsKey(Character.toString(currentChar)) ) {
            token = new OperatorToken(source);
        }
        else {
            token = new ErrorToken(source, Character.toString(currentChar));
            nextChar();
        }
        return token;
    }

    /**
     * Get the char at the cursor
     * @return
     * @throws IOException
     */
    public char currentChar() throws IOException {
        return source.currentChar();
    }

    /**
     * Advance the cursor and consume the current character
     *  ( or the next character, depending on your viewpoint )
     * @return
     * @throws IOException
     */
    public char nextChar() throws IOException {
        return source.nextChar();
    }

    // TODO: unit test

    /**
     * Skip whitespace and comments
     *
     * @throws IOException
     */
    void skipWhiteSpace() throws IOException {
        char currentChar = currentChar();
        while ( Character.isWhitespace(currentChar) || currentChar == '/' ) {
            char next = nextChar();
            if ( currentChar == '/' && ( next == '*' || next == '/') ) {
                if ( next == '*' ) {
                    do {
                        currentChar = next;
                        next = nextChar();
                    } while ( currentChar != '*' && next != '/' );
                }
                else if ( next == '/' ) {
                    do {
                        currentChar = nextChar();
                    }
                    while ( currentChar != EOL );
                }
            }
            else {
                currentChar = next;
            }
        }
    }
}
