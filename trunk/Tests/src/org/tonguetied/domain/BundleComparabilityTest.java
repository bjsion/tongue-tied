package org.tonguetied.domain;

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
        bundle.setResourceDestination("bbb");
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
        bundle.setResourceDestination("ccc");
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
        bundle.setResourceDestination("aaa");
        return bundle;
    }

}
