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

import de.cosmocode.rendering.Renderer;

/**
 * An adapter from {@link Renderer} to
 * {@link JSONConstructor}.
 *
 * @deprecated use {@link Renderer}
 * @author Willi Schoenborn
 */
@Deprecated
final class JsonRendererConstructor implements JSONConstructor {

    private final Renderer renderer;
    
    /**
     * Creates a new {@link JsonRendererConstructor} which
     * delegates all calls to an underlying {@link Renderer} instance.
     * 
     * @param renderer the renderer this instance relies on
     */
    public JsonRendererConstructor(Renderer renderer) {
        if (renderer == null) throw new NullPointerException("JSONRenderer must not be null");
        this.renderer = renderer;
    }

    @Override
    public JSONConstructor array() throws JSONException {
        renderer.list();
        return this;
    }
    
    @Override
    public JSONConstructor endArray() throws JSONException {
        renderer.endList();
        return this;
    }
    
    @Override
    public JSONConstructor object() throws JSONException {
        renderer.map();
        return this;
    }
    
    @Override
    public JSONConstructor endObject() throws JSONException {
        renderer.endMap();
        return this;
    }
    
    @Override
    public JSONConstructor key(String key) throws JSONException {
        renderer.key(key);
        return this;
    }
    
    @Override
    public JSONConstructor value(boolean value) throws JSONException {
        renderer.value(value);
        return this;
    }
    
    @Override
    public JSONConstructor value(long value) throws JSONException {
        renderer.value(value);
        return this;
    }
    
    @Override
    public JSONConstructor value(double value) throws JSONException {
        renderer.value(value);
        return this;
    }
    
    @Override
    public JSONConstructor value(Object value) throws JSONException {
        if (value instanceof JSONEncoder) {
            JSONEncoder.class.cast(value).encodeJSON(this);
        } else {
            renderer.value(value);
        }
        return this;
    }
    
    @Override
    public JSONConstructor plain(String value) throws JSONException {
        if (value == null) {
            return value(null);
        } else if (value.startsWith("{")) {
            try {
                return value(JSON.asMap(new JSONObject(value)));
            } catch (JSONException e) {
                throw new IllegalArgumentException(e);
            }
        } else if (value.startsWith("[")) {
            try {
                return value(JSON.asList(new JSONArray(value)));
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
