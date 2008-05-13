package org.tonguetied.datatransfer.exporting;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang.CharUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * Freemarker directive that transforms the output of its nested content from
 * native text to ASCII representation. The content will be escaped to its 
 * unicode characters. 
 * 
 * @author bsion
 * @see <a href="http://freemarker.sourceforge.net/docs/pgui_datamodel_directive.html">Directives</a>
 */
public class Native2AsciiDirective implements TemplateDirectiveModel {

    /* (non-Javadoc)
     * @see freemarker.template.TemplateDirectiveModel#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)
     */
    public void execute(Environment env,
            Map params, 
            TemplateModel[] loopVars,
            TemplateDirectiveBody body) throws TemplateException, IOException 
    {
        // check validity of call
        if (!params.isEmpty()) {
            throw new TemplateModelException(
                    "This directive doesn't allow parameters.");
        }
        if (loopVars.length != 0) {
                throw new TemplateModelException(
                    "This directive doesn't allow loop variables.");
        }
        if (body == null)
            throw new IllegalArgumentException("missing body");
        
        body.render(new Native2AsciiWriter(env.getOut()));
    }

    /**
     * A wrapper around an existing {@linkplain Writer} that transforms the 
     * character stream from native format to ASCII format. 
     * 
     * @author bsion
     *
     */
    static class Native2AsciiWriter extends Writer {
        private final Writer out;

        /**
         * Create a new instance of Native2AsciiWriter.
         * 
         * @param out
         */
        Native2AsciiWriter(Writer out) {
            this.out = out;
        }

        /* (non-Javadoc)
         * @see java.io.Writer#close()
         */
        @Override
        public void close() throws IOException {
            out.close();
        }

        /* (non-Javadoc)
         * @see java.io.Writer#flush()
         */
        @Override
        public void flush() throws IOException {
            out.flush();
        }

        /* (non-Javadoc)
         * @see java.io.Writer#write(char[], int, int)
         */
        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < len; i++) {
                if (CharUtils.isAscii(cbuf[i]))
                    builder.append(cbuf[i]);
                else
                    builder.append(CharUtils.unicodeEscaped(cbuf[i]));
            }
            
            out.write(builder.toString());
        }
    }
}
