package ucmo.rle71960.lexical.analysis;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ucmo.rle71960.lexical.analysis.lexer.Result;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
public class EntryPointIT {

    EntryPoint entryPoint;

    @Before
    public void setup() {
        entryPoint = new EntryPoint();
    }

    @Test
    public void only_error_token_test() {
        File file = new File(EntryPointIT.class.getClassLoader().getResource("testfile1").getFile());
        Assert.assertNotNull(file);
        EntryPoint.run(file.getAbsolutePath());
        entryPoint.printOperators();
        entryPoint.printIds();
        entryPoint.printNum();
        entryPoint.printErrors();
    }

    @Test
    public void main_test() {
        File file = new File(EntryPointIT.class.getClassLoader().getResource("main").getFile());
        Assert.assertNotNull(file);
        EntryPoint.run(file.getAbsolutePath());
        entryPoint.printOperators();
        entryPoint.printIds();
        entryPoint.printNum();
        entryPoint.printErrors();
    }

    @Test
    public void semicolon_test() {
        Map<String, String>  testmap = new HashMap<>();
        testmap.put(";", ";");
        Assert.assertTrue(testmap.containsKey(Character.toString(';')));
    }
}
