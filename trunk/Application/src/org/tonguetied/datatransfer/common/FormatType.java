package org.tonguetied.datatransfer.common;



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
     * Comma separated file type
     */
    csv {
        /* (non-Javadoc)
         * @see org.tonguetied.web.ExportParameters.FormatType#getDefaultFileExtension()
         */
        @Override
        public String getDefaultFileExtension() {
            return "csv";
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
    };
    
    public abstract String getDefaultFileExtension();
}