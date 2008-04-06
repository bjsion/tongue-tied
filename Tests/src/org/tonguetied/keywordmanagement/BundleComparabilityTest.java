package org.tonguetied.keywordmanagement;

import org.tonguetied.keywordmanagement.Bundle;

import junitx.extensions.ComparabilityTestCase;

public class BundleComparabilityTest extends ComparabilityTestCase {
    
    /**
     * @param name
     */
    public BundleComparabilityTest(String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createEqualInstance()
     */
    @Override
    protected Comparable<?> createEqualInstance() throws Exception {
        Bundle bundle = new Bundle();
        bundle.setName("bundle");
        bundle.setDescription("description");
        bundle.setResourceName("bbb");
        bundle.setDefault(false);
        return bundle;
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createGreaterInstance()
     */
    @Override
    protected Comparable<?> createGreaterInstance() throws Exception {
        Bundle bundle = new Bundle();
        bundle.setName("bundle");
        bundle.setDescription("description");
        bundle.setResourceName("ccc");
        bundle.setDefault(false);
        return bundle;
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createLessInstance()
     */
    @Override
    protected Comparable<?> createLessInstance() throws Exception {
        Bundle bundle = new Bundle();
        bundle.setName("bundle");
        bundle.setDescription("description");
        bundle.setResourceName("aaa");
        bundle.setDefault(false);
        return bundle;
    }

}
