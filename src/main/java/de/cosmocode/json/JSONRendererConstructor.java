package de.cosmocode.json;

import org.json.extension.JSONConstructor;

import de.cosmocode.patterns.Adapter;

/**
 * An adapter from {@link JSONRenderer} to
 * {@link JSONConstructor}.
 *
 * @author Willi Schoenborn <schoenborn@cosmocode.de>
 */
@Adapter(JSONConstructor.class)
final class JSONRendererConstructor implements JSONConstructor {

    private final JSONRenderer renderer;
    
    /**
     * Creates a new {@link JSONRendererConstructor} which
     * delegates all calls to an underlying {@link JSONRenderer} instance.
     * 
     * @param renderer the renderer this instance relies on
     */
    public JSONRendererConstructor(JSONRenderer renderer) {
        if (renderer == null) throw new NullPointerException("JSONRenderer must not be null");
        this.renderer = renderer;
    }

    @Override
    public JSONConstructor array() {
        renderer.array();
        return this;
    }
    
    @Override
    public JSONConstructor endArray() {
        renderer.endArray();
        return this;
    }
    
    @Override
    public JSONConstructor object() {
        renderer.object();
        return this;
    }
    
    @Override
    public JSONConstructor endObject() {
        renderer.endObject();
        return this;
    }
    
    @Override
    public JSONConstructor key(String key) {
        renderer.key(key);
        return this;
    }
    
    @Override
    public JSONConstructor value(boolean value) {
        renderer.value(value);
        return this;
    }
    
    @Override
    public JSONConstructor value(long value) {
        renderer.value(value);
        return this;
    }
    
    @Override
    public JSONConstructor value(double value) {
        renderer.value(value);
        return this;
    }
    
    @Override
    public JSONConstructor value(Object value) {
        renderer.value(value);
        return this;
    }
    
    @Override
    public JSONConstructor plain(String value) {
        renderer.value(value);
        return this;
    }
    
    @Override
    public String toString() {
        return renderer.toString();
    }
    
}
