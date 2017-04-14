package ucmo.rle71960.lexical.analysis.lexer;

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
    ERROR(".*")
    , FRACTION(TokenType.DOT + TokenType.DIGIT + TokenType.KLEENE + " " + TokenType.NONZERO + TokenType.UNION + TokenType.DOT + "0")
    , INTEGER(TokenType.NONZERO + " " + TokenType.DIGIT + TokenType.KLEENE + TokenType.UNION + "0")
    , FLOAT(TokenType.INTEGER + " " + TokenType.FRACTION)
    , NUM(TokenType.INTEGER + TokenType.UNION + TokenType.FLOAT)
    , ALPHANUM(TokenType.LETTER + TokenType.UNION + TokenType.DIGIT + TokenType.UNION + "_")
    , ID(TokenType.LETTER + " " + TokenType.ALPHANUM + TokenType.KLEENE)
    , RESERVED(TokenType.PUNCTUATION_OR_RESERVED)
    , COMMENT(TokenType.MULTI_LINE_COMMENT);

    public final String tokenPattern;

    private static final String NONZERO = "1-9";
    private static final String DIGIT = "0-9";
    private static final String LETTER = "a-zA-Z";
    private static final String PUNCTUATION_OR_RESERVED = "[=]?[=<>;,.+\\-*/\\[\\]{}()][=]?| and | not | or | if | then | else | for | class | int | float | get | return | void | main ";
    private static final String MULTI_LINE_COMMENT = "/\\*.*\\*/";

    private static final String UNION = "|";
    private static final String KLEENE = "*";
    private static final String DOT = "\\.";

    TokenType(String tokenPattern) {
        this.tokenPattern = tokenPattern;
    }
}
