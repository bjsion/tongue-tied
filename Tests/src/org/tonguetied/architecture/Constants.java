/*
 * Copyright 2008 The Tongue-Tied Authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at
 *  
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License. 
 */
package org.tonguetied.architecture;

import java.io.File;

import org.apache.commons.lang.SystemUtils;

/**
 * @author bsion
 *
 */
public interface Constants
{
    static final File BASE_DIR = new File(SystemUtils.getUserDir(), "..");
    static final File APPLICATION_DIR =  new File(BASE_DIR, "Application");
    static final File APPLICATION_CLASSES = new File(APPLICATION_DIR, "classes");
    static final File SERVER_DIR = new File(BASE_DIR, "Server");
    static final File SERVER_CLASSES = new File(SERVER_DIR, "classes");
    
    static final String[] PACKAGE_FILTER = new String[] {
        "au.com.bytecode.*",
        "fmpp*", "freemarker*", "java.*", "javax.*", "org.apache.*", 
        "org.hibernate*", "org.hsqldb*", "org.mortbay.*", 
        "org.springframework.*", "org.xml.*"
    };
}
