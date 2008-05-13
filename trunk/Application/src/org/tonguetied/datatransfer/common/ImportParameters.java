package org.tonguetied.datatransfer.common;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Translation.TranslationState;


/**
 * Value object to transport export selection criteria. This object does not
 * have a state.
 * 
 * @author bsion
 *
 */
public class ImportParameters {

    private String fileName;
    private byte[] data;
    private FormatType formatType;
    private TranslationState translationState;

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    /**
     * The raw data to import. This data can be in text or binary format. This 
     * will be specific to the importer, as data can be imported from different
     * sources.
     * 
     * @return the raw data to import
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @param data the data to be translated to Keywords and Translations
     */
    public void setData(final byte[] data) {
        this.data = data;
    }

    /**
     * @return the format of the uploaded file
     */
    public FormatType getFormatType() {
        return formatType;
    }

    /**
     * @param formatType the format of the uploaded file
     */
    public void setFormatType(final FormatType formatType) {
        this.formatType = formatType;
    }

    /**
     * @return the {@link TranslationState} to set all imported 
     * {@link Translation}s
     */
    public TranslationState getTranslationState() {
        return translationState;
    }

    /**
     * @param translationState the {@link TranslationState} to set all imported
     * {@link Translation}s
     */
    public void setTranslationState(final TranslationState translationState) {
        this.translationState = translationState;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, 
                ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
