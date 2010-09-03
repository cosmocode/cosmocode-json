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

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.cosmocode.collections.utility.AbstractUtilityMap;
import de.cosmocode.collections.utility.Utility;
import de.cosmocode.collections.utility.UtilitySet;
import de.cosmocode.commons.Strings;
import de.cosmocode.patterns.Adapter;

/**
 * An {@link Adapter} providing
 * a {@link Map}-view on a {@link JSONObject}.
 * 
 * See also {@link JSON#asMap(JSONObject)}.
 * 
 * @author Willi Schoenborn
 */
@Adapter(Map.class)
final class JsonObjectMap extends AbstractUtilityMap<String, Object> {
    
    private static final Object NULL = null;

    private final JSONObject object;
    
    private final UtilitySet<Map.Entry<String, Object>> entrySet;
    
    /**
     * Constructs a new {@link JsonObjectMap} using the specified {@link JSONObject}.
     * 
     * @param object the {@link JSONObject} this map will be backed by
     */
    public JsonObjectMap(JSONObject object) {
        if (object == null) throw new IllegalArgumentException("JSONObject must not be null");
        this.object = object;
        this.entrySet = Utility.asUtilitySet(new EntrySet());
    }

    /**
     * Inner class serving the purpose of an {@link Entry} set.
     * 
     * @author Willi Schoenborn
     */
    private class EntrySet extends AbstractSet<Map.Entry<String, Object>> {

        /**
         * Inner class serving the purpose of an {@link Iterator} of the {@link Entry}s of
         * this {@link Map}.
         * 
         * @author Willi Schoenborn
         */
        private class EntrySetIterator implements Iterator<Map.Entry<String, Object>> {
            
            private final Iterator<?> iterator = object.keys();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }
            
            @Override
            public Map.Entry<String, Object> next() {
                final String key = iterator.next().toString();
                final Object value = get(key);
                return new AbstractMap.SimpleEntry<String, Object>(key, value);
            }
            
            @Override
            public void remove() {
                iterator.remove();
            }
            
        }
        
        @Override
        public Iterator<Map.Entry<String, Object>> iterator() {
            return new EntrySetIterator();
        }

        @Override
        public int size() {
            return JsonObjectMap.this.size();
        }
        
    }
    
    @Override
    public int size() {
        return object.length();
    }
    
    @Override
    public boolean containsKey(Object key) {
        return object.has(key == null ? null : key.toString());
    }
    
    @Override
    public Object get(Object key) {
        final Object value = object.opt(Strings.toString(key));
        if (value instanceof JSONObject) {
            final JSONObject json = JSONObject.class.cast(value);
            return JSON.asMap(json);
        } else if (value instanceof JSONArray) {
            final JSONArray json = JSONArray.class.cast(value);
            return JSON.asList(json);
        } else if (value == null || value.equals(NULL)) {
            return null;
        } else {
            return value;
        }
    }
    
    @Override
    public Object put(String key, Object value) {
        if (key == null) throw new NullPointerException("Key must not be null");
        if (value == null) throw new NullPointerException("Value must not be null");
        try {
            final Object oldValue = get(key);
            object.put(key, value);
            return oldValue;
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    @Override
    public Object remove(Object key) {
        return object.remove(Strings.toString(key));
    }
    
    @Override
    public UtilitySet<Map.Entry<String, Object>> entrySet() {
        return entrySet;
    }
    
    @Override
    public String toString() {
        return object.toString();
    }
    
}
