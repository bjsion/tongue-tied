package org.tonguetied.keywordmanagement;

import junitx.extensions.ComparabilityTestCase;

import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Country.CountryCode;

/**
 * Test class to ensure the {@link Country} class is compliant with the
 * <code>Comparable</code> interface.
 * 
 * @author bsion
 * 
 */
public class CountryComparabilityTest extends ComparabilityTestCase
{

    /**
     * @param name
     */
    public CountryComparabilityTest(String name)
    {
        super(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see junitx.extensions.ComparabilityTestCase#createEqualInstance()
     */
    @Override
    protected Comparable<Country> createEqualInstance() throws Exception
    {
        Country finland = new Country();
        finland.setCode(CountryCode.FI);
        finland.setName("Finland");
        return finland;
    }

    /*
     * (non-Javadoc)
     * 
     * @see junitx.extensions.ComparabilityTestCase#createGreaterInstance()
     */
    @Override
    protected Comparable<Country> createGreaterInstance() throws Exception
    {
        Country norway = new Country();
        norway.setCode(CountryCode.NO);
        norway.setName("Norway");
        return norway;
    }

    /*
     * (non-Javadoc)
     * 
     * @see junitx.extensions.ComparabilityTestCase#createLessInstance()
     */
    @Override
    protected Comparable<Country> createLessInstance() throws Exception
    {
        Country defaultCountry = new Country();
        defaultCountry.setCode(CountryCode.DEFAULT);
        defaultCountry.setName("Default");
        return defaultCountry;
    }

}
