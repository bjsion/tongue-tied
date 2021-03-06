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
package org.tonguetied.audit;

/**
 * This interface marks all classes that support audit log attributes but are
 * not auditable themselves.
 * 
 * @author bsion
 *
 */
public interface AuditSupport
{
    /**
     * Format the object to display the string value of this object for an 
     * Audit Log entry.
     * 
     * @return the string value of the object
     */
    String toLogString();
}
