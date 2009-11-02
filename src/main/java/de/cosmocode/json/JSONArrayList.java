package de.cosmocode.json;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.cosmocode.collections.utility.AbstractUtilityList;
import de.cosmocode.collections.utility.UtilityList;
import de.cosmocode.patterns.Adapter;

/**
 * An {@link Adapter} providing
 * a {@link List}-view on a {@link JSONArray}.
 *
 * See also {@link JSON#asList(JSONArray)}.
 * 
 * @author schoenborn@cosmocode.de
 */
@Adapter(List.class)
class JSONArrayList extends AbstractUtilityList<Object> implements List<Object>, UtilityList<Object> {

    private final JSONArray array;
    
    /**
     * Constructs a new {@link JSONArrayList} using the specified {@link JSONArray}.
     * 
     * @param array the {@link JSONArray} this list will be backed by
     */
    public JSONArrayList(JSONArray array) {
        if (array == null) throw new NullPointerException("JSONArray must not be null");
        this.array = array;
        if (contains(null)) throw new NullPointerException("JSONLIst must not contain null values");
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        final Object value = array.opt(index);
        if (value instanceof JSONObject) {
            final JSONObject json = JSONObject.class.cast(value);
            return JSON.asMap(json);
        } else if (value instanceof JSONArray) {
            final JSONArray json = JSONArray.class.cast(value);
            return JSON.asList(json);
        } else {
            return value;
        }
    }
    
    @Override
    public Object set(int index, Object element) {
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        if (element == null) throw new NullPointerException("Element must not be null");
        try {
            final Object oldValue = get(index);
            array.put(index, element);
            return oldValue;
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    @Override
    public void add(int index, Object element) {
        if (index < 0 || index > size()) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        if (element == null) throw new NullPointerException("Element must not be null");
        
        // save current position
        int i = index;
        
        // holds the next element
        Object next = element;

        // loop until the end
        while (true) {
            // is the current index after the last element?
            if (i == size()) {
                // just add the next element
                array.put(next);
                // stop looping after adding next element to the end
                break;
            } else {
                // we didn't reach the end, so just overwrite
                // the element at position i, save the overwritten one
                // and increase the index
                next = set(i++, next);
            }
        }
    }
    
    @Override
    public Object remove(int index) {
        throw new UnsupportedOperationException("Unable to delegate to JSONArray");
    }

    @Override
    public int size() {
        return array.length();
    }
    
    @Override
    public String toString() {
        return array.toString();
    }
    
}
