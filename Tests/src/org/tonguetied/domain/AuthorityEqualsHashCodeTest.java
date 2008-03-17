package org.tonguetied.domain;

import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;
import org.tonguetied.domain.UserRight.Permission;

import junitx.extensions.EqualsHashCodeTestCase;


/**
 * @author bsion
 *
 */
public class AuthorityEqualsHashCodeTest extends EqualsHashCodeTestCase {

    public AuthorityEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        Language language = new Language();
        language.setCode(LanguageCode.it);
        language.setName("Italian");
        Country country = new Country();
        country.setCode(CountryCode.IT);
        country.setName("Italy");
        Bundle bundle = new Bundle();
        bundle.setName("test");
        UserRight authority = 
            new UserRight(Permission.ROLE_USER, language, country, bundle);
        
        return authority;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        Language language = new Language();
        language.setCode(LanguageCode.it);
        language.setName("Italian");
        Country country = new Country();
        country.setCode(CountryCode.IT);
        country.setName("Italy");
        Bundle bundle = new Bundle();
        bundle.setName("test");
        UserRight authority = 
            new UserRight(Permission.ROLE_ADMIN, language, country, bundle);
        
        return authority;
    }
}
