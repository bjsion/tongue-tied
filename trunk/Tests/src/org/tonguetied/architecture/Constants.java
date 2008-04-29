package org.tonguetied.architecture;

import java.io.File;

/**
 * @author bsion
 *
 */
public interface Constants {

    static final File APPLICATION_CLASSES = 
        new File(System.getProperty("user.dir")+"/../Application/classes");
    static final File SERVER_CLASSES = 
        new File(System.getProperty("user.dir")+"/../Server/classes");
}
