package ucmo.rle71960.lexical.analysis.lexer;

import java.util.*;
import java.util.function.Consumer;

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
public class Result {

    Map<TokenType, List<Token>> foundTokens;
    Map<TokenType, String> printHeaders;

    /**
     * Only classes from the {@link ucmo.rle71960.lexical.analysis.lexer} package
     * should create Results
     */
    Result() {
        foundTokens = new LinkedHashMap<>();
        printHeaders = new HashMap<>();
        for (TokenType type : TokenType.values()) {
            foundTokens.put(type, new ArrayList<>());
            printHeaders.put(type, type.name());
        }
    }

    void addToken(Token token) {
        this.foundTokens.get(token.getType()).add(token);
    }

    public void print(Consumer<Token> consumer) {
        // TODO iterate this printing all tokens
        //consumer.accept(foundTokens.get(TokenType.ERROR).get(0));
        for ( Map.Entry<TokenType,List<Token>> entry : foundTokens.entrySet() ) {
            System.out.println(printHeaders.get(entry.getKey()));
            for (Token token : entry.getValue()) {
                consumer.accept(token);
            }
        }
    }
}
