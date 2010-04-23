/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.json;

/**
 * A level which defines the granularity of a rendered
 * json structure.
 * 
 * <p>
 *   Every level usually contains the direct preceding
 *   level. E.g. {@link RenderLevel#MEDIUM} includes {@link RenderLevel#SHORT}
 *   which itself is a superset of {@link RenderLevel#TINY}.
 * </p>
 *
 * @author Willi Schoenborn
 */
public enum RenderLevel {

    /**
     * Mainly used for auto completions.
     * Should contain the identifier and the name.
     */
    TINY,
    
    /**
     * Information needed for small lists. Usually some
     * boolean/bitset properties used to indicate or group
     * elements.
     */
    SHORT,
  
    /**
     * Meta information like timestamps (createdAt, lastModified, ...)
     * and some basic address and contact data.
     */
    MEDIUM,
    
    /**
     * Contains all direct field data, and usually no or very little
     * referenced object information.
     */
    LONG,
    
    /**
     * Contains all data, including referenced sub object graphs
     * like children, many-to-many relationships and so on. 
     */
    COMPLETE;
    
}
