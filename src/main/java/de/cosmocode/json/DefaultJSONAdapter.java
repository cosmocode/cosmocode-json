package de.cosmocode.json;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.json.JSONException;
import org.json.extension.JSONConstructor;
import org.json.extension.JSONEncoder;
import org.json.extension.NoObjectContext;

import de.cosmocode.commons.DateMode;

final class DefaultJSONAdapter extends AbstractJSONRenderer implements JSONAdapter {

    private final JSONRenderer renderer;
    
    public DefaultJSONAdapter(JSONConstructor constructor, DateMode dateMode) {
        this(JSON.asJSONRenderer(constructor), dateMode);
    }
    
    public DefaultJSONAdapter(JSONRenderer renderer, DateMode dateMode) {
        super(dateMode);
        if (renderer == null) throw new NullPointerException("JSONRenderer must not be null");
        this.renderer = renderer;
    }

    @Override
    public JSONAdapter array() {
        renderer.array();
        return this;
    }

    @Override
    public JSONAdapter endArray() {
        renderer.endArray();
        return this;
    }

    @Override
    public JSONAdapter object() {
        renderer.object();
        return this;
    }

    @Override
    public JSONAdapter endObject() {
        renderer.endObject();
        return this;
    }

    @Override
    public JSONAdapter key(String key) throws JSONException {
        return key(CharSequence.class.cast(key));
    }

    @Override
    public JSONAdapter key(CharSequence key) {
        renderer.key(key);
        return this;
    }

    @Override
    public JSONRenderer nullValue() {
        renderer.nullValue();
        return this;
    }

    @Override
    public JSONAdapter value(Object value) {
        renderer.value(value);
        return this;
    }

    @Override
    public JSONAdapter value(boolean value) {
        renderer.value(value);
        return this;
    }
    
    @Override
    public JSONAdapter value(long value) {
        renderer.value(value);
        return this;
    }
    
    @Override
    public JSONAdapter value(double value) {
        renderer.value(value);
        return this;
    }
    
    @Override
    public JSONRenderer value(BigInteger value) {
        renderer.value(value);
        return this;
    }

    @Override
    public JSONRenderer value(BigDecimal value) {
        renderer.value(value);
        return this;
    }

    @Override
    public JSONRenderer value(CharSequence value) {
        renderer.value(value);
        return this;
    }

    @Override
    public JSONRenderer pairs(NoObjectContext pairs) {
        renderer.object(pairs);
        return this;
    }

    @Override
    public JSONRenderer object(NoObjectContext pairs) {
        renderer.object(pairs);
        return this;
    }

    @Override
    public JSONRenderer object(JSONEncoder object) {
        renderer.object(object);
        return this;
    }

    @Override
    public JSONAdapter plain(String json) {
        renderer.plain(json);
        return this;
    }

    @Override
    public String toString() {
        return renderer.toString();
    }

}
