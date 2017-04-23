package ucmo.rle71960.lexical.analysis.lexer.tokens;

import ucmo.rle71960.lexical.analysis.lexer.Source;
import ucmo.rle71960.lexical.analysis.lexer.Token;

import java.io.IOException;

import static ucmo.rle71960.lexical.analysis.lexer.TokenType.*;

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
public class NumToken extends Token {

    public NumToken(Source s) {
        super(s);
    }

    @Override
    protected void extractToken() throws IOException {
        StringBuilder buffer = new StringBuilder();
        extractNumber(buffer);
    }

    protected void extractNumber(StringBuilder buffer) throws IOException {
        String beforeDecimal = null;
        String afterDecimal = null;
        boolean hasDecimal = false;
        char currentChar;

        this.type = INTEGER;

        // here we consume up to the decimal point
        beforeDecimal = integerDigits(buffer);
        if ( this.type == ERROR ) {
            return;
        }

        // this will be a decimal if it's a float
        currentChar = currentChar();
        if ( currentChar == '.' ) {
            this.type = FLOATINGPOINT;
            buffer.append(currentChar);

            afterDecimal = integerDigits(buffer);
            if (this.type == ERROR) {
                return;
            }
        }

        if ( this.type == INTEGER ) {
            this.type = NUM;
            try {
                Integer integer = Integer.valueOf(buffer.toString());
                this.value = integer;
                this.text = integer.toString();
            }
            catch(NumberFormatException e) {
                this.type = ERROR;
                this.value = beforeDecimal;
            }
        }
        else if ( this.type == FLOATINGPOINT ) {
            this.type = NUM;
            try {
                Float floatValue = Float.valueOf(buffer.toString());
                this.value = floatValue;
                this.text = floatValue.toString();
            }
            catch (NumberFormatException e) {
                this.type = ERROR;
                this.value = beforeDecimal + "." + afterDecimal;
            }
        }
    }

    private String integerDigits(StringBuilder buffer) throws IOException {
        char currentChar = currentChar();
        if ( Character.toString(currentChar).equals("."))
        {
            currentChar = nextChar();
        }

        if ( !Character.isDigit(currentChar)) {
            this.type = ERROR;
            this.text = Character.toString(currentChar);
            return null;
        }

        StringBuilder integerDigits = new StringBuilder();
        while (Character.isDigit(currentChar)) {
            buffer.append(currentChar);
            integerDigits.append(currentChar);
            currentChar = nextChar();
        }

        return integerDigits.toString();
    }

}
