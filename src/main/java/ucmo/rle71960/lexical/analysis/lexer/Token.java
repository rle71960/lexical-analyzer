package ucmo.rle71960.lexical.analysis.lexer;

import java.io.IOException;

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
public class Token {

    protected TokenType type;
    protected String text;
    protected Source source;
    protected int position;
    protected int lineNumber;
    protected Object value;

    public Token(Source s) {
        this.source = s;
        this.position = s.getCurrentPosition();
        this.lineNumber = s.getLineNumber();

        try {
            extractToken();
        }
        catch (IOException e) {
            System.err.println("An exception was caught while constructing a Token");
            e.printStackTrace();
        }
    }

    public TokenType getType() {
        return this.type;
    }

    protected void extractToken() throws IOException {
        this.text = Character.toString(this.source.currentChar());
        this.value = null;
        this.source.nextChar();
    }

    protected char currentChar() throws IOException {
        return source.currentChar();
    }

    protected char nextChar() throws IOException {
        return source.nextChar();
    }

    protected char lookAheadOne() throws IOException {
        return source.lookAheadOne();
    }
}
