package org.tonguetied.keywordmanagement;

import org.tonguetied.keywordmanagement.Bundle;

import junitx.extensions.EqualsHashCodeTestCase;


/**
 * @author bsion
 *
 */
public class BundleEqualsHashCodeTest extends EqualsHashCodeTestCase {

    public BundleEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        Bundle bundle = new Bundle();
        bundle.setName("tonguetied Server");
        bundle.setDescription("tonguetied resources");
        bundle.setResourceName("tonguetied");
        bundle.setDefault(false);
        
        return bundle;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        Bundle bundle = new Bundle();
        bundle.setName("tonguetied Server");
        bundle.setDescription("tonguetied resources");
        bundle.setResourceName("tonguetied2");
        bundle.setDefault(false);
        
        return bundle;
    }
}
