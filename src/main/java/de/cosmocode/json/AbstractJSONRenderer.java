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

import java.util.Iterator;
import java.util.Map;

import de.cosmocode.rendering.AbstractRenderer;
import de.cosmocode.rendering.Renderer;
import de.cosmocode.rendering.RenderingException;

/**
 * Abstract skeleton implementation of the
 * {@link JSONRenderer} interface.
 * 
 * @author Willi Schoenborn
 */
public abstract class AbstractJSONRenderer extends AbstractRenderer implements JSONRenderer {

    @Override
    public JSONRenderer key(Object key) {
        super.key(key);
        return this;
    }
    
    @Override
    public JSONRenderer value(Object value) {
        super.value(value);
        return this;
    }
    
    @Override
    public Renderer list() throws RenderingException {
        return array();
    }
    
    @Override
    public Renderer endList() throws RenderingException {
        return endArray();
    }

    @Override
    public Renderer map() throws RenderingException {
        return object();
    }

    @Override
    public Renderer endMap() throws RenderingException {
        return endObject();
    }

    @Override
    public JSONRenderer value(Object... values) throws RenderingException {
        super.value(values);
        return this;
    }
    
    @Override
    public JSONRenderer value(Iterable<?> values) throws RenderingException {
        super.value(values);
        return this;
    }
    
    @Override
    public JSONRenderer value(Iterator<?> values) throws RenderingException {
        super.value(values);
        return this;
    }
    
    @Override
    public JSONRenderer value(Map<?, ?> pairs) throws RenderingException {
        super.value(pairs);
        return this;
    }
    
    @Override
    public JSONRenderer values(Object... values) {
        if (values == null) return this;
        for (Object value : values) {
            value(value);
        }
        return this;
    }

    @Override
    public JSONRenderer values(Iterable<?> values) {
        if (values == null) return this;
        for (Object value : values) {
            value(value);
        }
        return this;
    }
    
    @Override
    public JSONRenderer values(Iterator<?> values) {
        if (values == null) return this;
        while (values.hasNext()) {
            value(values.next());
        }
        return this;
    }

    @Override
    public JSONRenderer array(Object... values) {
        return array().values(values).endArray();
    }

    @Override
    public JSONRenderer array(Iterable<?> values) {
        return array().values(values).endArray();
    }

    @Override
    public JSONRenderer array(Iterator<?> values) {
        return array().values(values).endArray();
    }

    @Override
    public JSONRenderer pairs(Map<?, ?> pairs) {
        if (pairs == null) return this;
        for (Map.Entry<?, ?> entry : pairs.entrySet()) {
            key(entry.getKey()).value(entry.getValue());
        }
        return this;
    }

    @Override
    public JSONRenderer object(Map<?, ?> pairs) {
        return object().pairs(pairs).endObject();
    }

    @Override
    public abstract String toString();
    
    @Override
    public String build() throws RenderingException {
        return toString();
    }

}
