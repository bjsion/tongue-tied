/**
 * 
 */
package org.tonguetied.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.tonguetied.domain.Keyword;
import org.tonguetied.domain.Translation;


/**
 * Helper class to transform a list {@link Keyword} into a {@link List} of 
 * {@link Translation}s. 
 * 
 * @author bsion
 *
 */
public class TranslationTransformer {

    /**
     * Transform a list {@link Keyword} into a {@link List} of 
     * {@link Translation}s. If the key word does not contain any 
     * {@link Translation}s, then an empty {@link Translation} is created and
     * added to the list as a place holder for the {@link Keyword}.
     * 
     * @param keywords the list of {@link Keyword}s to transform
     * @return a list of {@link Translation}s
     */
    public static List<Translation> transform(final List<Keyword> keywords) 
    {
        List<Translation> translations = new ArrayList<Translation>();
  
        if (keywords != null) {
            Translation translation;
            for (Keyword keyword: keywords) {
                if (CollectionUtils.isEmpty(keyword.getTranslations())) {
                    translation = new Translation();
                    translation.setKeyword(keyword.clone());
                    translations.add(translation);
                }
                else {
                    translations.addAll(keyword.getTranslations());
                }
            }
        }
        
        return translations;
    }

}
