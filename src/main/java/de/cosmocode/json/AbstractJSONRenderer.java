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

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.google.common.base.Preconditions;

import de.cosmocode.commons.DateMode;
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

    protected static final DateMode DEFAULT_DATEMODE = DateMode.JAVA;
    
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
    
    /**
     * A hook allowing sub classes to add behaviour to 
     * the {@link AbstractJSONRenderer#value(Object)} method.
     * 
     * <p>
     *   This implementation uses {@link Object#toString()}.
     * </p>
     * 
     * @deprecated use {@link #setMapping(de.cosmocode.rendering.Mapping)} instead
     * @param value the value of an unknown type, is never null
     * @return this
     */
    @Deprecated
    protected JSONRenderer unknownValue(Object value) {
        return value(value.toString());
    }
    
    @Override
    public <T> JSONRenderer value(T value, ValueRenderer<? super T> renderer) {
        Preconditions.checkNotNull(renderer, "Renderer");
        renderer.render(value, this);
        return this;
    }
    
    @Override
    public JSONRenderer value(Date value) {
        return value == null ? nullValue() : value(value, DEFAULT_DATEMODE);
    }

    @Override
    public JSONRenderer value(Date value, DateMode mode) {
        if (mode == null) throw new NullPointerException("DateMode must not be null");
        if (value == null) return nullValue();
        return value(mode.format(value));
    }

    @Override
    public JSONRenderer value(Enum<?> value) {
        return value == null ? nullValue() : value(value.name());
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
    public <T> JSONRenderer values(Iterable<? extends T> values, ValueRenderer<? super T> renderer) {
        Preconditions.checkNotNull(renderer, "Renderer");
        if (values == null) return this;
        for (T value : values) {
            value(value, renderer);
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
    public JSONRenderer values(JSONListable values) {
        return values == null ? this : values.renderAsList(this);
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
    public JSONRenderer array(JSONListable values) {
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
    public JSONRenderer pairs(JSONMapable pairs) {
        return pairs == null ? this : pairs.renderAsMap(this);
    }

    @Override
    public JSONRenderer object(Map<?, ?> pairs) {
        return object().pairs(pairs).endObject();
    }

    @Override
    public JSONRenderer object(JSONMapable pairs) {
        return object().pairs(pairs).endObject();
    }
    
    @Override
    public boolean lt(RenderLevel level) {
        return currentLevel().compareTo(level) < 0;
    }
    
    @Override
    public boolean le(RenderLevel level) {
        return currentLevel().compareTo(level) <= 0;
    }
    
    @Override
    public boolean eq(RenderLevel level) {
        return currentLevel().compareTo(level) == 0;
    }
    
    @Override
    public boolean ge(RenderLevel level) {
        return currentLevel().compareTo(level) >= 0;
    }
    
    @Override
    public boolean gt(RenderLevel level) {
        return currentLevel().compareTo(level) > 0;
    }
    
    @Override
    public abstract String toString();
    
    @Override
    public String build() throws RenderingException {
        return toString();
    }

}
