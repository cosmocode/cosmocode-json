package de.cosmocode.json;

public class JSONConstructorRendererTest extends AbstractJSONRendererTest {

    @Override
    protected JSONRenderer create() {
        return JSON.createJSONRenderer();
    }
    
    @Override
    protected String toJSONString(JSONRenderer renderer) {
        return renderer.toString();
    }
    
}
