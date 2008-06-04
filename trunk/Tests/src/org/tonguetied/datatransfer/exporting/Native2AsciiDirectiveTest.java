package org.tonguetied.datatransfer.exporting;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * @author bsion
 *
 */
public class Native2AsciiDirectiveTest {

    private TemplateDirectiveBody body;
    private Environment env;
    private TemplateModel[] loopVars;
    private Map<Object, Object> params;
    private StringWriter out;
    private Reader in;
    private Template template;
    private TemplateHashModel rootDataModel;
//    private String expected;
//    private String input;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        out = new StringWriter();
        in = new StringReader("test");
        template = new Template("test", in, new Configuration(), null);
        env = new Environment(template, rootDataModel, out);
        
        loopVars = new TemplateModel[] {};
        params = new HashMap<Object, Object>();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(out);
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)}.
     */
    @Test
    public final void testExecuteWithAsciiOnly() throws Exception {
        body = new TestBody("abcd");
        Native2AsciiDirective native2Ascii = new Native2AsciiDirective();
        native2Ascii.execute(env, params, loopVars, body);
        StringBuffer buffer = out.getBuffer();
        assertEquals("abcd", buffer.toString());
    }
    
    /**
     * Test method for {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testExecuteWithNullBody() throws Exception {
        Native2AsciiDirective native2Ascii = new Native2AsciiDirective();
        native2Ascii.execute(env, params, loopVars, null);
    }
    
    /**
     * Test method for {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)}.
     */
    @Test(expected=TemplateModelException.class)
    public final void testExecuteWithInvalidParams() throws Exception {
        params.put("key", "value");
        Native2AsciiDirective native2Ascii = new Native2AsciiDirective();
        native2Ascii.execute(env, params, loopVars, body);
    }
    
    /**
     * Test method for {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)}.
     */
    @Test(expected=TemplateModelException.class)
    public final void testExecuteWithInvalidLoopVars() throws Exception {
        loopVars = new TemplateModel[] {null, null};
        Native2AsciiDirective native2Ascii = new Native2AsciiDirective();
        native2Ascii.execute(env, params, loopVars, body);
    }

    private static final class TestBody implements TemplateDirectiveBody 
    {
        private String input;

        /**
         * Create a new instance of TestBody.
         * 
         * @param input
         */
        public TestBody(String input) {
            this.input = input;
        }

        public void render(Writer newOut) throws IOException {
            newOut.write(input);
        }
    }
}
