package de.cosmocode.json;

import java.io.Writer;

public class JSONConstructorRendererFileWriterTest extends AbstractJSONRendererFileWriterTest {

    @Override
    protected JSONRenderer doCreate(Writer writer) {
        return JSON.createJSONRenderer(writer);
    }
    
    

}
