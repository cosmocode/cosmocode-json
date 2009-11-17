package de.cosmocode.json;

import org.json.JSONException;
import org.json.extension.JSONConstructor;
import org.json.extension.JSONEncoder;
import org.json.extension.NoObjectContext;

import de.cosmocode.patterns.Adapter;

/**
 * Implementation of the {@link JSONRenderer} interface
 * which uses most of the functionality provided
 * by {@link JSONConstructor} by wrapping an instance of it.
 *
 * @author Willi Schoenborn
 */
@Adapter(JSONRenderer.class)
final class JSONConstructorRenderer extends AbstractJSONRenderer implements JSONRenderer {

    private final JSONConstructor constructor;
    private final JSONConstructor adapter;
    
    /**
     * Creates a new {@link JSONConstructorRenderer} using the given constructor and dateMode.
     * 
     * @param constructor the constructor this instance relies on
     */
    public JSONConstructorRenderer(JSONConstructor constructor) {
        if (constructor == null) throw new NullPointerException("JSONConstructor must not be null");
        this.constructor = constructor;
        this.adapter = JSON.asJSONConstructor(this);
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
    protected JSONRenderer unknownValue(Object value) {
        try {
            constructor.value(value);
            return this;
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
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
        if (Double.isInfinite(value)) throw new IllegalArgumentException("Double must not be infinite");
        if (Double.isNaN(value)) throw new IllegalArgumentException("Double must not be NaN");
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
    public JSONRenderer pairs(NoObjectContext pairs) {
        if (pairs == null) return this;
        try {
            pairs.encodeJSON(adapter);
            return this;
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    @Override
    public JSONRenderer object(NoObjectContext pairs) {
        return object().pairs(pairs).endObject();
    }

    @Override
    public JSONRenderer object(JSONEncoder object) {
        if (object == null) return object().endObject();
        try {
            object.encodeJSON(adapter);
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
        return this;
    }

    @Override
    public String toString() {
        return constructor.toString();
    }
    
}
