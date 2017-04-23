package ucmo.rle71960.lexical.analysis.lexer.tokens;

import ucmo.rle71960.lexical.analysis.lexer.Source;
import ucmo.rle71960.lexical.analysis.lexer.Token;

import java.io.IOException;

import static ucmo.rle71960.lexical.analysis.lexer.TokenType.ERROR;
import static ucmo.rle71960.lexical.analysis.lexer.TokenType.FLOATINGPOINT;
import static ucmo.rle71960.lexical.analysis.lexer.TokenType.INTEGER;

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
        String beforeDecimal = null;
        String afterDecimal = null;
        boolean hasDecimal = false;
        char currentChar;

        this.type = INTEGER;

        beforeDecimal = integerDigits(buffer);
        if ( this.type == ERROR ) {
            return;
        }

        // this will be a decimal
        currentChar = currentChar();
        buffer.append(currentChar);
        currentChar = currentChar();

        afterDecimal = integerDigits(buffer);
        if ( this.type == ERROR ) {
            return;
        }

        if ( this.type == INTEGER ) {
            int integerValue = getIntegerValue(beforeDecimal);
            if ( this.type != ERROR ) {
                this.value = Integer.valueOf(integerValue;
            }
        }
        else if ( this.type == FLOATINGPOINT ) {
            float floatValue = getFloatValue(beforeDecimal, afterDecimal);
            if ( this.type != ERROR ) {
                this.value = Float.valueOf(floatValue);
            }
        }
    }

    private String integerDigits(StringBuilder buffer) throws IOException {
        char currentChar = currentChar();

        if ( !Character.isDigit(currentChar)) {
            this.type = ERROR;
            this.value = INVALID_NUMBER; // TODO currentChar ?
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
        this.value = RANGE_INTEGER;
        return 0;
    }

    private float getFloatValue(String beforeDecimal, String afterDecimal) {
        // I pretty much took this part from the book without the exponent part...not sure this works
        // TODO on further examination I think this method won't work, we can simplify
        double floatValue = 0.0;
        String numbers = beforeDecimal;
        numbers += afterDecimal;

        int index = 0;
        while (index < numbers.length()) {
            floatValue = 10 * floatValue + Character.getNumericValue(numbers.charAt(index));
        }

        return (float) floatValue;
    }
}
