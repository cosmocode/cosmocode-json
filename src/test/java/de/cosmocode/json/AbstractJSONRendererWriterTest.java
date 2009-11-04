package de.cosmocode.json;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.junit.After;

import com.google.common.collect.Maps;

abstract class AbstractJSONRendererWriterTest extends AbstractJSONRendererTest {

    private final Map<JSONRenderer, Writer> cache = Maps.newHashMap();
    
    protected abstract Writer createWriter() throws IOException;
    
    protected abstract JSONRenderer doCreate(Writer writer); 
    
    protected abstract String toString(Writer writer) throws IOException;
    
    @Override
    protected final JSONRenderer create() {
        try {
            final Writer writer = createWriter();
            final JSONRenderer renderer = doCreate(writer);
            cache.put(renderer, writer);
            return renderer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    protected final String toJSONString(JSONRenderer renderer) {
        try {
            final Writer writer = cache.get(renderer);
            writer.flush();
            return toString(writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @After
    public final void after() throws IOException {
        for (Writer writer : cache.values()) {
            writer.close();
        }
        cache.clear();
    }
    
}
