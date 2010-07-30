/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.json;

import org.json.JSONException;
import org.json.JSONStringer;
import org.json.JSONWriter;
import org.json.extension.JSONConstructor;
import org.json.extension.JSONEncoder;

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
    private final JSONConstructor adapter = JSON.asConstructor(this);
    
    @Override
    protected Renderer unknownValue(Object value) {
        if (value instanceof JSONEncoder) {
            try {
                JSONEncoder.class.cast(value).encodeJSON(adapter);
            } catch (JSONException e) {
                throw new RenderingException(e);
            }
            return this;
        } else {
            return super.unknownValue(value);
        }
    }
    
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
