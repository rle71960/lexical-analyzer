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

import java.io.BufferedReader;
import java.io.IOException;

/**
 * This class will represent the source that is to be scanned
 */
public class Source {
    public static final char EOL = '\n';
    public static final char EOF = (char) 0;

    private BufferedReader reader;
    private int lineNumber;
    private int currentPosition;
    private String line;

    public Source(BufferedReader reader) {
        this.lineNumber = 0;
        // -1 is a special value indicating no reads have occurred
        this.currentPosition = -1;
        this.reader = reader;
    }

    void readLine() throws IOException {
        line = this.reader.readLine();
        // reset the current position on the line to just before the first char
        if ( line != null ) {
            lineNumber += 1;
        }
    }

    public char currentChar() throws IOException {
        // if this is the first pass
        if ( currentPosition == -1 ) {
            readLine();
            return nextChar();
        }
        else if ( line == null ) {
            return EOF;
        }
        else if ( currentPosition == -1 || currentPosition == line.length() ) {
            return EOL;
        }
        else if ( currentPosition > line.length() ) {
            readLine();
            currentPosition = -1;
            return nextChar();
        }

        return line.charAt(currentPosition);
    }

    public char nextChar() throws IOException {
        currentPosition += 1;
        return currentChar();
    }

    public void close() {
        if ( reader != null ) {
            try {
                reader.close();
            }
            catch (IOException e) {
                System.err.println("An exception occurred trying to close the Source reader");
                e.printStackTrace();
            }
        }
    }

    /**
     * get the next char without consuming the current
     * @return
     * @throws IOException
     */
    public char lookAheadOne() throws IOException {
        currentChar();
        if ( line == null ) {
            return EOF;
        }
        int next = currentPosition + 1;
        return next < line.length() ? line.charAt(next) : EOL;
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

}
