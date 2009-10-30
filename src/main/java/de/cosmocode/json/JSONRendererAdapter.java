package de.cosmocode.json;

import org.json.JSONException;
import org.json.extension.JSONConstructor;

import de.cosmocode.patterns.Adapter;

/**
 * An adapter from {@link JSONRenderer} to
 * {@link JSONConstructor}.
 *
 * @author Willi Schoenborn <schoenborn@cosmocode.de>
 */
@Adapter(JSONConstructor.class)
class JSONRendererAdapter implements JSONConstructor {

    private final JSONRenderer renderer;
    
    /**
     * Creates a new {@link JSONRendererAdapter} which
     * delegates all calls to an underlying {@link JSONRenderer} instance.
     * 
     * @param renderer the renderer this instance relies on
     */
    public JSONRendererAdapter(JSONRenderer renderer) {
        if (renderer == null) throw new NullPointerException("JSONRenderer must not be null");
        this.renderer = renderer;
    }

    @Override
    public JSONConstructor array() throws JSONException {
        renderer.array();
        return this;
    }
    
    @Override
    public JSONConstructor endArray() throws JSONException {
        renderer.endArray();
        return this;
    }
    
    @Override
    public JSONConstructor object() throws JSONException {
        renderer.object();
        return this;
    }
    
    @Override
    public JSONConstructor endObject() throws JSONException {
        renderer.endObject();
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
        renderer.value(value);
        return this;
    }
    
    @Override
    public JSONConstructor plain(String value) throws JSONException {
        renderer.value(value);
        return this;
    }
    
}
