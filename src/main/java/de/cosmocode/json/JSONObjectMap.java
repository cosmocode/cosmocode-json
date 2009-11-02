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
import de.cosmocode.collections.utility.UtilityMap;
import de.cosmocode.collections.utility.UtilitySet;
import de.cosmocode.patterns.Adapter;

/**
 * An {@link Adapter} providing
 * a {@link Map}-view on a {@link JSONObject}.
 * 
 * See also {@link JSON#asMap(JSONObject)}.
 * 
 * @author schoenborn@cosmocode.de
 */
@Adapter(Map.class)
class JSONObjectMap extends AbstractUtilityMap<String, Object> 
    implements Map<String, Object>, UtilityMap<String, Object> {

    private final JSONObject object;
    
    private final UtilitySet<Map.Entry<String, Object>> entrySet;
    
    /**
     * Constructs a new {@link JSONObjectMap} using the specified {@link JSONObject}.
     * 
     * @param object the {@link JSONObject} this map will be backed by
     */
    public JSONObjectMap(JSONObject object) {
        if (object == null) throw new IllegalArgumentException("JSONObject can't be null");
        this.object = object;
        this.entrySet = Utility.asUtilitySet(new EntrySet());
    }

    /**
     * Inner class serving the purpose of an {@link Entry} set.
     * 
     * @author schoenborn@cosmocode.de
     */
    private class EntrySet extends AbstractSet<Map.Entry<String, Object>> {

        /**
         * Inner class serving the purpose of an {@link Iterator} of the {@link Entry}s of
         * this {@link Map}.
         * 
         * @author schoenborn@cosmocode.de
         */
        private class EntrySetIterator implements Iterator<Map.Entry<String, Object>> {
            
            private final Iterator<?> iterator = object.keys();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }
            
            @Override
            public Map.Entry<String, Object> next() {
                final String entryKey = iterator.next().toString();
                final Object value = get(entryKey);
                final Object entryValue;
                
                if (value instanceof JSONObject) {
                    final JSONObject json = JSONObject.class.cast(value);
                    entryValue = JSON.asMap(json);
                } else if (value instanceof JSONArray) {
                    final JSONArray json = JSONArray.class.cast(value);
                    entryValue = JSON.asList(json);
                } else {
                    entryValue = value;
                }
                
                return new AbstractMap.SimpleEntry<String, Object>(entryKey, entryValue);
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
            return JSONObjectMap.this.size();
        }
        
    }
    
    @Override
    public int size() {
        return object.length();
    }
    
    @Override
    public boolean containsKey(Object k) {
        final String key = String.class.cast(k);
        return object.has(key);
    }
    
    @Override
    public Object get(Object k) {
        try {
            final String key = String.class.cast(k);
            if (containsKey(key)) {
                return object.get(key);
            } else {
                return null;
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
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
    public Object remove(Object k) {
        final String key = String.class.cast(k);
        return object.remove(key);
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
