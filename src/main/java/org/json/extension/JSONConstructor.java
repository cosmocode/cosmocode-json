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

package org.json.extension;

import org.json.JSONException;

import de.cosmocode.json.JSONRenderer;

/**
 * A JSONConstructor provides several methods
 * for bean/pojo-classes to build their
 * own json structure.
 * 
 * @deprecated use {@link JSONRenderer} instead
 * 
 * @author schoenborn@cosmocode.de
 */
@Deprecated
public interface JSONConstructor {

    /**
     * Starts an array.
     * 
     * @return this
     * @throws JSONException if nesting is not allowed
     */
    JSONConstructor array() throws JSONException;
    
    /**
     * Ends an array.
     * 
     * @return this
     * @throws JSONException if nesting is not allowed
     */
    JSONConstructor endArray() throws JSONException;

    /**
     * Starts an object.
     * 
     * @return this
     * @throws JSONException if nesting is not allowed
     */
    JSONConstructor object() throws JSONException;
    
    /**
     * Ends an object.
     * 
     * @return this
     * @throws JSONException if nesting is not allowed
     */
    JSONConstructor endObject() throws JSONException;
    
    /**
     * Writes a key.
     * 
     * @param key the key to write
     * @return this
     * @throws JSONException if nesting is not allowed
     */
    JSONConstructor key(String key) throws JSONException;
    
    /**
     * Writes plain json code.
     * 
     * @param value a valid json string
     * @return this
     * @throws JSONException if nesting is not allowed
     */
    JSONConstructor plain(String value) throws JSONException;
    
    /**
     * Writes a boolean value.
     * 
     * @param value the value to write
     * @return this
     * @throws JSONException if nesting is not allowed
     */
    JSONConstructor value(boolean value) throws JSONException;

    /**
     * Writes a double value.
     * 
     * @param value the value to write
     * @return this
     * @throws JSONException if nesting is not allowed
     */
    JSONConstructor value(double value) throws JSONException;

    /**
     * Writes a long value.
     * 
     * @param value the value to write
     * @return this
     * @throws JSONException if nesting is not allowed
     */
    JSONConstructor value(long value) throws JSONException;

    /**
     * Writes an {@link Object} value.
     * 
     * @param value the value to write
     * @return this
     * @throws JSONException if nesting is not allowed
     */
    JSONConstructor value(Object value) throws JSONException;
    
}
