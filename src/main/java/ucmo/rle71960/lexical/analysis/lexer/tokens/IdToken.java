package ucmo.rle71960.lexical.analysis.lexer.tokens;

import ucmo.rle71960.lexical.analysis.lexer.Source;
import ucmo.rle71960.lexical.analysis.lexer.Token;
import ucmo.rle71960.lexical.analysis.lexer.TokenType;

import java.io.IOException;

import static ucmo.rle71960.lexical.analysis.lexer.TokenType.ID;
import static ucmo.rle71960.lexical.analysis.lexer.TokenType.RESERVED_WORDS;

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
public class IdToken extends Token {

    public IdToken(Source s) {
        super(s);
    }

    @Override
    protected void extractToken() throws IOException {
        StringBuilder buffer = new StringBuilder();
        char currentChar = currentChar();

        while (Character.isLetterOrDigit(currentChar) || Character.toString(currentChar).equals("_")) {
            buffer.append(currentChar);
            currentChar = nextChar();
        }
        this.text = buffer.toString();

        if ( RESERVED_WORDS.contains(text.toLowerCase()) ) {
            this.type = TokenType.valueOf(text.toUpperCase());
        }
        else {
            this.type = ID;
        }
    }
}
