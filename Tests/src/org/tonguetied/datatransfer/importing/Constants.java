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
package org.tonguetied.datatransfer.importing;

import java.io.File;

import org.apache.commons.lang.SystemUtils;

/**
 * @author bsion
 *
 */
public interface Constants
{
    static final File TEST_RESOURCES_DIR = new File(SystemUtils.getUserDir(), "resources");

    static final File TEST_DATA_DIR = new File(TEST_RESOURCES_DIR, "data");
}
