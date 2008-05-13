package org.tonguetied.datatransfer.importing;

import java.io.File;

import org.apache.commons.lang.SystemUtils;

/**
 * @author bsion
 *
 */
public interface Constants {
    static final File TEST_RESOURCES_DIR = new File(SystemUtils.getUserDir(), "resources");

    static final File TEST_DATA_DIR = new File(TEST_RESOURCES_DIR, "data");
}
