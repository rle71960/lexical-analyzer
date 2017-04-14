package ucmo.rle71960.lexical.analysis.lexer;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    Result result;

    public Lexer() {
        result = new Result();
    }

    public Result scan(String toScan) {
        // TODO
        Pattern patterns = compiledPattern();
        Matcher matcher = patterns.matcher(toScan);

        while ( matcher.find() ) {
            if ( matcher.group(TokenType.ERROR.name()) != null) {
                result.addToken(new Token(TokenType.ERROR, matcher.group(TokenType.ERROR.name())));
            }
            else {
                continue;
            }
        }
        return result;
    }

    Pattern compiledPattern(final String formattableStringToUse) {
        final int AFTER_INITIAL_UNION = 1;
        StringBuilder tokenPatterns = new StringBuilder();
        for ( TokenType type : TokenType.values() ) {
            tokenPatterns.append(String.format(formattableStringToUse, type.name(), type.tokenPattern));
        }
        return Pattern.compile(tokenPatterns.substring(AFTER_INITIAL_UNION));
    }

    Pattern compiledPattern() {
        final String FORMATTABLE_PATTERN_STRING = "|(?<%s>%s)";
        return compiledPattern(FORMATTABLE_PATTERN_STRING);
    }
}
