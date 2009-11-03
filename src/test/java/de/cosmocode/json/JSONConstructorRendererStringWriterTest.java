package de.cosmocode.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class JSONConstructorRendererStringWriterTest extends AbstractJSONRendererWriterTest {

    @Override
    protected JSONRenderer doCreate(Writer writer) {
        return JSON.createJSONRenderer(writer);
    }
    
    @Override
    protected Writer createWriter() throws IOException {
        return new StringWriter();
    }
    
    @Override
    protected String toString(Writer writer) throws IOException {
        return writer.toString();
    }

}
