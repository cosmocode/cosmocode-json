package de.cosmocode.json;

import org.json.JSONException;
import org.json.JSONStringer;
import org.json.JSONWriter;

import de.cosmocode.rendering.AbstractRenderer;
import de.cosmocode.rendering.Renderer;
import de.cosmocode.rendering.RenderingException;

/**
 * Json based {@link Renderer} implementation.
 *
 * @since 2.1
 * @author Willi Schoenborn
 */
public final class JsonRenderer extends AbstractRenderer implements Renderer {

    private final JSONWriter writer = new JSONStringer();
    
    @Override
    public Renderer list() throws RenderingException {
        try {
            writer.array();
        } catch (JSONException e) {
            throw new RenderingException(e);
        }
        return this;
    }

    @Override
    public Renderer endList() throws RenderingException {
        try {
            writer.endArray();
        } catch (JSONException e) {
            throw new RenderingException(e);
        }
        return this;
    }

    @Override
    public Renderer map() throws RenderingException {
        try {
            writer.object();
        } catch (JSONException e) {
            throw new RenderingException(e);
        }
        return this;
    }

    @Override
    public Renderer endMap() throws RenderingException {
        try {
            writer.endObject();
        } catch (JSONException e) {
            throw new RenderingException(e);
        }
        return this;
    }

    @Override
    public Renderer key(CharSequence key) throws RenderingException {
        try {
            writer.key(key == null ? "null" : key.toString());
        } catch (JSONException e) {
            throw new RenderingException(e);
        }
        return this;
    }

    @Override
    public Renderer nullValue() throws RenderingException {
        try {
            writer.value(null);
        } catch (JSONException e) {
            throw new RenderingException(e);
        }
        return this;
    }

    @Override
    public Renderer value(boolean value) throws RenderingException {
        try {
            writer.value(value);
        } catch (JSONException e) {
            throw new RenderingException(e);
        }
        return this;
    }

    @Override
    public Renderer value(long value) throws RenderingException {
        try {
            writer.value(value);
        } catch (JSONException e) {
            throw new RenderingException(e);
        }
        return this;
    }

    @Override
    public Renderer value(double value) throws RenderingException {
        try {
            writer.value(value);
        } catch (JSONException e) {
            throw new RenderingException(e);
        }
        return this;
    }

    @Override
    public Renderer value(CharSequence value) throws RenderingException {
        try {
            writer.value(value);
        } catch (JSONException e) {
            throw new RenderingException(e);
        }
        return this;
    }

    @Override
    public String build() throws RenderingException {
        return writer.toString();
    }

}
