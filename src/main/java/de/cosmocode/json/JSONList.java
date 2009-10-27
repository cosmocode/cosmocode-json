package de.cosmocode.json;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;

import de.cosmocode.collections.utility.AbstractUtilityList;

class JSONList extends AbstractUtilityList<Object> implements List<Object> {

    private final JSONArray array;
    
    public JSONList(JSONArray array) {
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
    public int size() {
        return array.length();
    }
    
    @Override
    public String toString() {
        return "[" + StringUtils.join(this, ", ") + "]";
    }
    
}