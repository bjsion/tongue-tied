package org.tonguetied.datatransfer;

import org.tonguetied.keywordmanagement.KeywordService;


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

        /* (non-Javadoc)
         * @see org.tonguetied.datatransfer.FormatType#getImporter(org.tonguetied.keywordmanagement.KeywordService)
         */
        @Override
        public Importer getImporter(KeywordService keywordService) {
            return new CsvImporter(keywordService);
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

        /* (non-Javadoc)
         * @see org.tonguetied.datatransfer.FormatType#getImporter(org.tonguetied.keywordmanagement.KeywordService)
         */
        @Override
        public Importer getImporter(KeywordService keywordService) {
            return new ExcelImporter(keywordService);
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

        /* (non-Javadoc)
         * @see org.tonguetied.datatransfer.FormatType#getImporter(org.tonguetied.keywordmanagement.KeywordService)
         */
        @Override
        public Importer getImporter(KeywordService keywordService) {
            return new ExcelImporter(keywordService);
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

        /* (non-Javadoc)
         * @see org.tonguetied.datatransfer.FormatType#getImporter(org.tonguetied.keywordmanagement.KeywordService)
         */
        @Override
        public Importer getImporter(KeywordService keywordService) {
            return new PropertiesImporter(keywordService);
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

        /* (non-Javadoc)
         * @see org.tonguetied.datatransfer.FormatType#getImporter(org.tonguetied.keywordmanagement.KeywordService)
         */
        @Override
        public Importer getImporter(KeywordService keywordService) {
            return null;
        }
    };
    
    public abstract String getDefaultFileExtension();
    
    public abstract ExportDataPostProcessor getPostProcessor();
    
    /**
     * Factory method to create the appropriate <code>Importer</code>
     * 
     * @param keywordService interface to persistent storage
     * @return The newly created <code>Importer</code>
     */
    public abstract Importer getImporter(KeywordService keywordService);
}