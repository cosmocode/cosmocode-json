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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.extension.JSONConstructor;
import org.json.extension.JSONEncoder;

import de.cosmocode.patterns.Adapter;

/**
 * An adapter from {@link JSONRenderer} to
 * {@link JSONConstructor}.
 *
 * @author Willi Schoenborn
 */
@Adapter(JSONConstructor.class)
final class JSONRendererConstructor implements JSONConstructor {

    private final JSONRenderer renderer;
    
    /**
     * Creates a new {@link JSONRendererConstructor} which
     * delegates all calls to an underlying {@link JSONRenderer} instance.
     * 
     * @param renderer the renderer this instance relies on
     */
    public JSONRendererConstructor(JSONRenderer renderer) {
        if (renderer == null) throw new NullPointerException("JSONRenderer must not be null");
        this.renderer = renderer;
    }

    @Override
    public JSONConstructor array() {
        renderer.array();
        return this;
    }
    
    @Override
    public JSONConstructor endArray() {
        renderer.endArray();
        return this;
    }
    
    @Override
    public JSONConstructor object() {
        renderer.object();
        return this;
    }
    
    @Override
    public JSONConstructor endObject() {
        renderer.endObject();
        return this;
    }
    
    @Override
    public JSONConstructor key(String key) {
        renderer.key(key);
        return this;
    }
    
    @Override
    public JSONConstructor value(boolean value) {
        renderer.value(value);
        return this;
    }
    
    @Override
    public JSONConstructor value(long value) {
        renderer.value(value);
        return this;
    }
    
    @Override
    public JSONConstructor value(double value) {
        renderer.value(value);
        return this;
    }
    
    @Override
    public JSONConstructor value(Object value) {
        if (value instanceof JSONEncoder) {
            renderer.object(JSONEncoder.class.cast(value));
        } else {
            renderer.value(value);
        }
        return this;
    }
    
    @Override
    public JSONConstructor plain(String value) {
        if (value == null) {
            return value(null);
        } else if (value.startsWith("{")) {
            try {
                return value(new JSONObject(value));
            } catch (JSONException e) {
                throw new IllegalArgumentException(e);
            }
        } else if (value.startsWith("[")) {
            try {
                return value(new JSONArray(value));
            } catch (JSONException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            throw new IllegalArgumentException("Illegal plain value '" + value + "'");
        }
    }
    
    @Override
    public String toString() {
        return renderer.toString();
    }
    
}
