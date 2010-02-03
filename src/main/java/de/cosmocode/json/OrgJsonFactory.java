package de.cosmocode.json;

import java.io.Writer;

import org.json.JSONStringer;
import org.json.JSONWriter;

/**
 * <p> A {@link JsonFactory} that creates JsonRenderer implementations
 * that are backed by the org.json package.
 * Namely: JsonStringer and JsonWriter.
 * </p>
 * 
 * @author Oliver Lorenz
 */
public class OrgJsonFactory extends AbstractJsonFactory implements JsonFactory {
    
    @Override
    public JSONRenderer create() {
        return JSON.createJSONRenderer();
    }

    @Override
    public JSONRenderer create(RenderLevel level) {
        // TODO use a more direct implementation
        return new JSONConstructorRenderer(JSON.asJSONConstructor(new JSONStringer()), level);
    }
    
    @Override
    public JSONRenderer create(Writer writer) {
        return JSON.createJSONRenderer(writer);
    }

    @Override
    public JSONRenderer create(Writer writer, RenderLevel level) {
        // TODO use a more direct implementation
        return new JSONConstructorRenderer(JSON.asJSONConstructor(new JSONWriter(writer)), level);
    }

}
