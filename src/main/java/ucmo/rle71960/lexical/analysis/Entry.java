package ucmo.rle71960.lexical.analysis;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import ucmo.rle71960.lexical.analysis.lexer.Lexer;
import ucmo.rle71960.lexical.analysis.lexer.Result;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
public class Entry {

    public static void main(String[] args) {
        if ( args.length < 1 ) {
            System.out.println("Usage: java -jar <this-jar> [path-to-file | string]");
            System.exit(1);
        }
        final int ONLY_THE_FIRST = 0;
        Result result = run(args[ONLY_THE_FIRST]);
        result.print(System.out::println);
    }

    public static Result run(String pathOrText) {
        InputStream is = null;
        Result result;
        try {
            is = stringOrFileToInputStream(pathOrText);
            Lexer lexer = new Lexer();
            result = lexer.scan(is);
        }
        catch(IOException e) {
            System.out.println("Couldn't read file '" + pathOrText + "'.");
            e.printStackTrace();
            result = null;
        }
        finally {
            IOUtils.closeQuietly(is);
        }
        return result;
    }

    private static InputStream stringOrFileToInputStream(String in) throws IOException {
        File file = new File(in);
        if ( file.isFile() ) {
            return FileUtils.openInputStream(file);
        }
        else {
            return IOUtils.toInputStream(in, "UTF-8");
        }
    }
}
