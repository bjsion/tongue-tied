/*
 * Copyright 2008 The Tongue-Tied Authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at
 *  
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License. 
 */
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
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * Unit test class to test the custom freemarker directive 
 * {@link Native2AsciiDirective}.
 * 
 * @author bsion
 * 
 */
public class Native2AsciiDirectiveTest
{
    private TemplateDirectiveBody body;
    private Environment env;
    private TemplateModel[] loopVars;
    private Map<Object, Object> params;
    private StringWriter out;
    private Reader in;
    private Template template;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        out = new StringWriter();
        in = new StringReader("test");
        template = new Template("test", in, new Configuration(), null);
        env = new Environment(template, null, out);

        loopVars = new TemplateModel[] {};
        params = new HashMap<Object, Object>();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(out);
    }

    /**
     * Test method for
     * {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)}.
     */
    @Test
    public final void testExecuteWithAsciiOnly() throws Exception
    {
        body = new TestBody("abcd");
        Native2AsciiDirective native2Ascii = new Native2AsciiDirective();
        native2Ascii.execute(env, params, loopVars, body);
        StringBuffer buffer = out.getBuffer();
        assertEquals("abcd", buffer.toString());
    }

    /**
     * Test method for
     * {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testExecuteWithNullBody() throws Exception
    {
        Native2AsciiDirective native2Ascii = new Native2AsciiDirective();
        native2Ascii.execute(env, params, loopVars, null);
    }

    /**
     * Test method for
     * {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)}.
     */
    @Test
    public final void testExecuteWithValidParams() throws Exception
    {
        body = new TestBody("abcd");
        params.put("iskey", TemplateBooleanModel.FALSE);
        Native2AsciiDirective native2Ascii = new Native2AsciiDirective();
        native2Ascii.execute(env, params, loopVars, body);
        StringBuffer buffer = out.getBuffer();
        assertEquals("abcd", buffer.toString());
    }

    /**
     * Test method for
     * {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)}.
     */
    @Test
    public final void testExecuteWithSpaceInKey() throws Exception
    {
        body = new TestBody("abcd efg");
        params.put("iskey", TemplateBooleanModel.TRUE);
        Native2AsciiDirective native2Ascii = new Native2AsciiDirective();
        native2Ascii.execute(env, params, loopVars, body);
        StringBuffer buffer = out.getBuffer();
        assertEquals("abcd\\ efg", buffer.toString());
    }

    /**
     * Test method for
     * {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)}.
     */
    @Test(expected=TemplateModelException.class)
    public final void testExecuteWithInvalidParamValue() throws Exception
    {
        body = new TestBody("abcd");
        params.put("iskey", "invalid");
        Native2AsciiDirective native2Ascii = new Native2AsciiDirective();
        native2Ascii.execute(env, params, loopVars, body);
    }

    /**
     * Test method for
     * {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)}.
     */
    @Test(expected=TemplateModelException.class)
    public final void testExecuteWithInvalidParams() throws Exception
    {
        body = new TestBody("abcd");
        params.put("key", "value");
        Native2AsciiDirective native2Ascii = new Native2AsciiDirective();
        native2Ascii.execute(env, params, loopVars, body);
    }

    /**
     * Test method for
     * {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)}.
     */
    @Test(expected=TemplateModelException.class)
    public final void testExecuteWithInvalidLoopVars() throws Exception
    {
        loopVars = new TemplateModel[] { null, null };
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
        public TestBody(String input)
        {
            this.input = input;
        }

        public void render(Writer newOut) throws IOException
        {
            newOut.write(input);
        }
    }
}
