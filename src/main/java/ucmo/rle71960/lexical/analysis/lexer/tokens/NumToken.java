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
        this.text = buffer.toString();
    }

    protected void extractNumber(StringBuilder buffer) throws IOException {
        // TODO cleanup to use my number types
        /*
        TODO i think since we're doing fairly simple numbers, we can consume
        until either whitespace or non-valid/non-digit, then either split
        on ".", check that there are only 2 entries at most in the array
        then use parseInt or go the simplest route and use float.parseFloat
        ... or something like that, this seems like overkill regardless of the approach we take
         */
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

        // this will be a decimal
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
            int integerValue = getIntegerValue(beforeDecimal);
            this.type = NUM;
            if ( this.type != ERROR ) {
                this.value = Integer.valueOf(integerValue);
            }
        }
        else if ( this.type == FLOATINGPOINT ) {
            this.type = NUM;
            try {
                this.value = Float.valueOf(beforeDecimal + "." + afterDecimal);
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
            // TODO does this output the correct value as the ERROR token?
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

    private int getIntegerValue(String numbers) {
        if ( numbers == null ) {
            return 0;
        }

        int integerValue = 0;
        int previousValue = -1;
        int index = 0;

        while ( index < numbers.length() && integerValue >= previousValue ) {
            previousValue = integerValue;
            integerValue = 10 * integerValue + Character.getNumericValue(numbers.charAt(index));
            index += 1;
        }

        if (integerValue >= previousValue) {
            return integerValue;
        }

        this.type = ERROR;
        // TODO does this output the correct value on ERROR? Is this even an issue?
        this.value = integerValue;
        return 0;
    }

}
