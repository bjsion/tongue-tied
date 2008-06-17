package org.tonguetied.datatransfer.exporting;

import java.io.File;

import org.apache.commons.lang.SystemUtils;

/**
 * @author bsion
 *
 */
public interface Constants {
    static final File BASE_DIR = new File(SystemUtils.getUserDir(), "..");
    static final File APPLICATION_DIR = new File(BASE_DIR, "Application");
    static final File SERVER_DIR = new File(BASE_DIR, "Server");
    static final File TEST_RESOURCES_DIR = 
        new File(SystemUtils.getUserDir(), "resources");
    static final File TEST_CONFIG_DIR = 
        new File(TEST_RESOURCES_DIR, "config");
}
