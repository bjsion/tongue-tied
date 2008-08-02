package org.tonguetied.keywordmanagement;

import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Country.CountryCode;

import junitx.extensions.EqualsHashCodeTestCase;


/**
 * @author bsion
 *
 */
public class CountryEqualsHashCodeTest extends EqualsHashCodeTestCase {

    public CountryEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        Country country = new Country();
        country.setCode(CountryCode.SG);
        country.setName("Singapore");
        
        return country;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        Country country = new Country();
        country.setCode(CountryCode.SG);
        country.setName("Singapura");
        
        return country;
    }
}
