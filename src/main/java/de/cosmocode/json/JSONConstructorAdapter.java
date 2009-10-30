package de.cosmocode.json;

import org.json.JSONException;
import org.json.extension.JSONConstructor;
import org.json.extension.JSONEncoder;

import de.cosmocode.patterns.Adapter;

/**
 * Implementation of the {@link JSONRenderer} interface
 * which uses most of the functionality provided
 * by {@link JSONConstructor} by wrapping an instance of it.
 *
 * @author Willi Schoenborn <schoenborn@cosmocode.de>
 */
@Adapter(JSONRenderer.class)
final class JSONConstructorAdapter extends AbstractJSONRenderer implements JSONRenderer {

    private final JSONConstructor constructor;
    
    /**
     * Creates a new {@link JSONConstructorAdapter} using the given constructor.
     * 
     * @param constructor the constructor this instance relies on
     */
    public JSONConstructorAdapter(JSONConstructor constructor) {
        if (constructor == null) throw new NullPointerException("JSONConstructor must not be null");
        this.constructor = constructor;
    }
    
    @Override
    public JSONRenderer array() {
        try {
            constructor.array();
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
        return this;
    }
    
    @Override
    public JSONRenderer endArray() {
        try {
            constructor.endArray();
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
        return this;
    }

    @Override
    public JSONRenderer object() {
        try {
            constructor.object();
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
        return this;
    }

    @Override
    public JSONRenderer endObject() {
        try {
            constructor.endObject();
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
        return this;
    }

    @Override
    public JSONRenderer key(CharSequence key) {
        try {
            constructor.key(key == null ? "null" : key.toString());
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
        return this;
    }
    
    @Override
    public JSONRenderer nullValue() {
        try {
            constructor.value(null);
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
        return this;
    }
    
    @Override
    public JSONRenderer value(boolean value) {
        try {
            constructor.value(value);
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
        return this;
    }

    @Override
    public JSONRenderer value(long value) {
        try {
            constructor.value(value);
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
        return this;
    }

    @Override
    public JSONRenderer value(double value) {
        try {
            constructor.value(value);
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
        return this;
    }

    @Override
    public JSONRenderer value(CharSequence value) {
        if (value == null) return nullValue();
        try {
            constructor.value(value.toString());
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
        return this;
    }

    @Override
    public <T extends JSONEncoder & NoObjectContext> JSONRenderer pairs(T pairs) {
        if (pairs == null) return this;
        try {
            pairs.encodeJSON(constructor);
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
        return this;
    }

    @Override
    public <T extends JSONEncoder & NoObjectContext> JSONRenderer object(T pairs) {
        return object().pairs(pairs).endObject();
    }

    @Override
    public JSONRenderer plain(String json) {
        try {
            constructor.plain(json);
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
        return this;
    }
    
}
