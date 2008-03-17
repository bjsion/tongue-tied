package org.tonguetied.domain;

import org.tonguetied.domain.Country.CountryCode;

import junitx.extensions.ComparabilityTestCase;

public class CountryComparabilityTest extends ComparabilityTestCase {
    
    /**
     * @param name
     */
    public CountryComparabilityTest(String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createEqualInstance()
     */
    @Override
    protected Comparable<?> createEqualInstance() throws Exception {
        Country finland = new Country();
        finland.setCode(CountryCode.FI);
        finland.setName("Finland");
        return finland;
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createGreaterInstance()
     */
    @Override
    protected Comparable<?> createGreaterInstance() throws Exception {
        Country norway = new Country();
        norway.setCode(CountryCode.NO);
        norway.setName("Norway");
        return norway;
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createLessInstance()
     */
    @Override
    protected Comparable<?> createLessInstance() throws Exception {
        Country defaultCountry = new Country();
        defaultCountry.setCode(CountryCode.DEFAULT);
        defaultCountry.setName("Default");
        return defaultCountry;
    }

}
