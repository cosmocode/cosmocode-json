package de.cosmocode.json;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * <p> An abstract implementation of {@link JsonFactory}. 
 * It provides default redirects for every method except
 * {@link #create(Writer, RenderLevel)} and {@link #create(RenderLevel)}.
 * </p>
 * 
 * @author Oliver Lorenz
 */
public abstract class AbstractJsonFactory implements JsonFactory {
    
    @Override
    public final JSONRenderer create(OutputStream stream) throws IOException {
        final Writer writer = new OutputStreamWriter(stream, DEFAULT_ENCODING);
        return create(writer);
    }
    
    @Override
    public JSONRenderer create(OutputStream stream, RenderLevel level) throws IOException {
        final Writer writer = new OutputStreamWriter(stream, DEFAULT_ENCODING);
        return create(writer, level);
    }
    
    @Override
    public JSONRenderer create(OutputStream stream, String encoding) throws IOException {
        final Writer writer = new OutputStreamWriter(stream, encoding);
        return create(writer);
    }
    
    @Override
    public JSONRenderer create(OutputStream stream, Charset encoding) throws IOException {
        final Writer writer = new OutputStreamWriter(stream, encoding);
        return create(writer);
    }
    
    @Override
    public JSONRenderer create(OutputStream stream, String encoding, RenderLevel level) throws IOException {
        final Writer writer = new OutputStreamWriter(stream, encoding);
        return create(writer, level);
    }
    
    @Override
    public JSONRenderer create(OutputStream stream, Charset encoding, RenderLevel level) throws IOException {
        final Writer writer = new OutputStreamWriter(stream, encoding);
        return create(writer, level);
    }
    
    @Override
    public JSONRenderer create() {
        return create(JSONRenderer.DEFAULT_LEVEL);
    }
    
    @Override
    public JSONRenderer create(Writer writer) {
        return create(writer, JSONRenderer.DEFAULT_LEVEL);
    }
    
    @Override
    public abstract JSONRenderer create(RenderLevel level);
    
    @Override
    public abstract JSONRenderer create(Writer writer, RenderLevel level);
    
}
