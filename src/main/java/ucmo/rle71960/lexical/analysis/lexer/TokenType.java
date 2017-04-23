package ucmo.rle71960.lexical.analysis.lexer;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

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
public enum TokenType {
    /*
     Operators, punctuation and reserved words
      */
    // reserved words
    AND, NOT, OR, PUT, IF, THEN, ELSE, FOR, CLASS, INT, FLOAT, GET, RETURN, VOID, MAIN

    // operators and punctuation
    , EQUALS("=="), NOT_EQUALS("<>"), LESS_THAN("<"), GREATER_THAN(">"), LESS_EQUALS("<=")
    , GREATER_EQUALS(">="), SEMICOLON(";"), COMMA(","), DOT("."), PLUS("+"), MINUS("-")
    , STAR("*"), SLASH("/"), ASSIGNMENT("="), SLASH_SLASH("//"), LEFT_PAREN("("), RIGHT_PAREN(")")
    , LEFT_BRACE("{"), RIGHT_BRACE("}"), LEFT_BRACKET("["), RIGHT_BRACKET("]"), SLASH_STAR("/*"), STAR_SLASH("*/")

    , ID, ALPHANUM, NUM, INTEGER, FLOATINGPOINT, FRACTION, LETTER, DIGIT, NONZERO, ERROR, END_OF_FILE;

    private static final int FIRST_RESERVED_INDEX = AND.ordinal();
    private static final int LAST_RESERVED_INDEDX = MAIN.ordinal();

    private static final int FIRST_OPERATOR_INDEX = EQUALS.ordinal();
    private static final int LAST_OPERATOR_INDEX = STAR_SLASH.ordinal();

    private String text;

    TokenType() {
        this.text = this.toString().toLowerCase();
    }

    TokenType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static Set<String> RESERVED_WORDS = new HashSet<>();
    public static Hashtable<String, TokenType> OPERATORS = new Hashtable<>();
    static {
        TokenType[] values = TokenType.values();
        for (int i = FIRST_RESERVED_INDEX; i <= LAST_RESERVED_INDEDX; i++) {
            RESERVED_WORDS.add(values[i].getText().toLowerCase());
        }
        for (int i = FIRST_OPERATOR_INDEX; i <= LAST_OPERATOR_INDEX; i++) {
            OPERATORS.put(values[i].getText(), values[i]);
        }
    }
}
