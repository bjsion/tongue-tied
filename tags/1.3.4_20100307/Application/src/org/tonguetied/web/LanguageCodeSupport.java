/**
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
package org.tonguetied.web;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.tonguetied.keywordmanagement.Language.LanguageCode;

/**
 * Support class to map enum {@link LanguageCode} to a string key in the web
 * tier. This class is used for rendering purposes.
 * 
 * @author bsion
 * 
 */
public class LanguageCodeSupport extends PropertyEditorSupport
{

    @Override
    public String getAsText()
    {
        final Object value = getValue();
        return value == null ? "" : ((LanguageCode) value).toString();
    }

    @Override
    public void setAsText(final String string) throws IllegalArgumentException
    {
        LanguageCode value;

        if (StringUtils.isEmpty(string))
            value = null;
        else
            value = LanguageCode.valueOf(string);

        setValue(value);
    }
}
