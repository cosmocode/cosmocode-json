package de.cosmocode.json;

import java.io.Writer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;
import org.json.extension.JSONConstructor;
import org.json.extension.JSONEncoder;

/**
 * Implementation of the {@link JSONConstructor} interface
 * which uses most of the functionality provided
 * by {@link JSONRenderer} by wrapping an instance of it.
 *
 * @author Willi Schoenborn
 */
final class JSONWriterConstructor implements JSONConstructor {

    private final JSONWriter json;
    private final Writer writer;

    public JSONWriterConstructor(JSONWriter json) {
        this.json = json;
        this.writer = JSON.stealWriter(json);
    }
    
    @Override
    public JSONConstructor array() throws JSONException {
        json.array();
        return this;
    }
    
    @Override
    public JSONConstructor endArray() throws JSONException {
        json.endArray();
        return this;
    }
    
    @Override
    public JSONConstructor object() throws JSONException {
        json.object();
        return this;
    }
    
    @Override
    public JSONConstructor endObject() throws JSONException {
        json.endObject();
        return this;
    }
    
    @Override
    public JSONConstructor key(String key) throws JSONException {
        json.key(key);
        return this;
    }
    
    @Override
    public JSONConstructor value(boolean value) throws JSONException {
        json.value(value);
        return this;
    }
    
    @Override
    public JSONConstructor value(long value) throws JSONException {
        json.value(value);
        return this;
    }
    
    @Override
    public JSONConstructor value(double value) throws JSONException {
        json.value(value);
        return this;
    }
    
    @Override
    public JSONConstructor value(Object value) throws JSONException {
        if (value instanceof JSONEncoder) {
            JSONEncoder.class.cast(value).encodeJSON(this);
        } else {
            json.value(value);
        }
        return this;
    }
    
    @Override
    public JSONConstructor plain(String value) throws JSONException {
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
        return json.toString();
    }
    
}
