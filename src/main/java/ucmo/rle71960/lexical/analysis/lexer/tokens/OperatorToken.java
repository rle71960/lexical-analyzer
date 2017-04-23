package ucmo.rle71960.lexical.analysis.lexer.tokens;

import ucmo.rle71960.lexical.analysis.lexer.Source;
import ucmo.rle71960.lexical.analysis.lexer.Token;

import java.io.IOException;

import static ucmo.rle71960.lexical.analysis.lexer.TokenType.ERROR;
import static ucmo.rle71960.lexical.analysis.lexer.TokenType.OPERATORS;

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
public class OperatorToken extends Token {

    public OperatorToken(Source s) {
        super(s);
    }

    @Override
    protected void extractToken() throws IOException {
        char currentChar = currentChar();
        this.text = Character.toString(currentChar);
        this.type = null;

        switch (currentChar) {
            case '+':
            case '-':
            case '*':
            case '/':
            case ',':
            case ';':
            case '\'':
            case '.':
            case '(':
            case ')':
            case '{':
            case '}':
            case '[':
            case ']':
                nextChar();
                break;
            case '<':
            case '>':
            case '=':
                currentChar = nextChar();
                if ( currentChar == '=') {
                    this.text += currentChar;
                    nextChar();
                }
                break;
            default:
                nextChar();
                this.type = ERROR;
                this.value = currentChar;
                break;
        }

        if ( this.type == null ) {
            this.type = OPERATORS.get(this.text);
        }
    }
}