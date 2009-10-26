package de.cosmocode.json;

import java.util.AbstractList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class JSONArrayListAdapter extends AbstractList<Object> implements List<Object> {

    private static final Logger log = LoggerFactory.getLogger(JSONArrayListAdapter.class);

    private final JSONArray array;
    
    public JSONArrayListAdapter(JSONArray array) {
        if (array == null) throw new NullPointerException("JSONArray must not be null");
        this.array = array;
    }

    @Override
    public Object get(int index) {
        try {
            return array.get(index);
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    @Override
    public Object set(int index, Object element) {
        final Object oldValue = array.opt(index);
        try {
            array.put(index, element);
            log.debug("Set element at {} to {}", index, element);
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
        return oldValue;
    }
    
    @Override
    public void add(int index, Object element) {
        log.debug("Attempt to add {} at index {} in {}", new Object[] {element, index, this});
        Object next = element;
        while (index < size()) {
            next = set(index++, next);
        }
    }
    
    @Override
    public int size() {
        return array.length();
    }
    
    @Override
    public String toString() {
        return "[" + StringUtils.join(this, ", ") + "]";
    }
    
}