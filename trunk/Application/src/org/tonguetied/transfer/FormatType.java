package org.tonguetied.datatransfer;


/**
 * The type of output format the results of the export query should take. 
 * Exported data will be put into a file format commensurate with the 
 * export type.
 * 
 * @author bsion
 *
 */
public enum FormatType {
    /**
     * Comma seperated file type
     */
    csv {
        /* (non-Javadoc)
         * @see org.tonguetied.web.ExportParameters.FormatType#getDefaultFileExtension()
         */
        @Override
        public String getDefaultFileExtension() {
            return "csv";
        }

        /* (non-Javadoc)
         * @see org.tonguetied.domain.ExportParameters.FormatType#getPostProcessor()
         */
        @Override
        public ExportDataPostProcessor getPostProcessor() {
            return null;
        }
    },
    /**
     * Excel spreadsheet
     */
    xls {
        /* (non-Javadoc)
         * @see org.tonguetied.web.ExportParameters.FormatType#getDefaultFileExtension()
         */
        @Override
        public String getDefaultFileExtension() {
            return "xls";
        }

        /* (non-Javadoc)
         * @see org.tonguetied.domain.ExportParameters.FormatType#getPostProcessor()
         */
        @Override
        public ExportDataPostProcessor getPostProcessor() {
            return null;
        }
    },
    /**
     * Excel spreadsheet in a language centric view
     */
    xlsLanguage {
        /* (non-Javadoc)
         * @see org.tonguetied.web.ExportParameters.FormatType#getDefaultFileExtension()
         */
        @Override
        public String getDefaultFileExtension() {
            return "xls";
        }

        /* (non-Javadoc)
         * @see org.tonguetied.domain.ExportParameters.FormatType#getPostProcessor()
         */
        @Override
        public ExportDataPostProcessor getPostProcessor() {
            return new LanguageCentricProcessor();
        }
    },
    /**
     * Java properties file
     */
    properties {
        /* (non-Javadoc)
         * @see org.tonguetied.web.ExportParameters.FormatType#getDefaultFileExtension()
         */
        @Override
        public String getDefaultFileExtension() {
            return "properties";
        }

        /* (non-Javadoc)
         * @see org.tonguetied.domain.ExportParameters.FormatType#getPostProcessor()
         */
        @Override
        public ExportDataPostProcessor getPostProcessor() {
            return null;
        }
    },
    /**
     * C# resources file
     */
    resources {
        /* (non-Javadoc)
         * @see org.tonguetied.web.ExportParameters.FormatType#getDefaultFileExtension()
         */
        @Override
        public String getDefaultFileExtension() {
            return "resx";
        }

        /* (non-Javadoc)
         * @see org.tonguetied.domain.ExportParameters.FormatType#getPostProcessor()
         */
        @Override
        public ExportDataPostProcessor getPostProcessor() {
            return null;
        }
    };
    
    public abstract String getDefaultFileExtension();
    
    public abstract ExportDataPostProcessor getPostProcessor();
}