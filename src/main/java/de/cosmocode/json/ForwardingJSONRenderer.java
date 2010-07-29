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

import org.json.extension.JSONEncoder;
import org.json.extension.NoObjectContext;

import de.cosmocode.commons.DateMode;
import de.cosmocode.rendering.ForwardingRenderer;
import de.cosmocode.rendering.RenderingException;

/**
 * Forwards all calls to an underlying {@link JSONRenderer}
 * allowing sub classes to hook into executions.
 *
 * @author Willi Schoenborn
 */
public abstract class ForwardingJSONRenderer extends ForwardingRenderer implements JSONRenderer {

    @Override
    protected abstract JSONRenderer delegate();

    @Override
    public JSONRenderer array() {
        delegate().array();
        return this;
    }

    @Override
    public JSONRenderer array(Object... values) {
        delegate().array(values);
        return this;
    }

    @Override
    public JSONRenderer array(Iterable<?> values) {
        delegate().array(values);
        return this;
    }

    @Override
    public JSONRenderer array(Iterator<?> values) {
        delegate().array(values);
        return this;
    }

    @Override
    public JSONRenderer endArray() {
        delegate().endArray();
        return this;
    }

    @Override
    public JSONRenderer endObject() {
        delegate().endObject();
        return this;
    }

    @Override
    public JSONRenderer key(CharSequence key) {
        delegate().key(key);
        return this;
    }

    @Override
    public JSONRenderer key(Object key) {
        delegate().key(key);
        return this;
    }

    @Override
    public JSONRenderer nullValue() {
        delegate().nullValue();
        return this;
    }

    @Override
    public JSONRenderer object() {
        delegate().object();
        return this;
    }

    @Override
    public JSONRenderer object(JSONEncoder object) {
        delegate().object(object);
        return this;
    }

    @Override
    public JSONRenderer object(JSONMapable pairs) {
        delegate().object(pairs);
        return this;
    }

    @Override
    public JSONRenderer object(Map<?, ?> pairs) {
        delegate().object(pairs);
        return this;
    }

    @Override
    public JSONRenderer object(NoObjectContext pairs) {
        delegate().object(pairs);
        return this;
    }

    @Override
    public JSONRenderer pairs(JSONMapable pairs) {
        delegate().pairs(pairs);
        return this;
    }

    @Override
    public JSONRenderer pairs(Map<?, ?> pairs) {
        delegate().pairs(pairs);
        return this;
    }

    @Override
    public JSONRenderer pairs(NoObjectContext pairs) {
        delegate().pairs(pairs);
        return this;
    }

    @Override
    public String toString() {
        return delegate().toString();
    }

    @Override
    public JSONRenderer value(boolean value) {
        delegate().value(value);
        return this;
    }

    @Override
    public JSONRenderer value(CharSequence value) {
        delegate().value(value);
        return this;
    }

    @Override
    public JSONRenderer value(Date value, DateMode mode) {
        delegate().value(value, mode);
        return this;
    }

    @Override
    public JSONRenderer value(Date value) {
        delegate().value(value);
        return this;
    }

    @Override
    public JSONRenderer value(double value) {
        delegate().value(value);
        return this;
    }

    @Override
    public JSONRenderer value(Enum<?> value) {
        delegate().value(value);
        return this;
    }

    @Override
    public JSONRenderer value(long value) {
        delegate().value(value);
        return this;
    }

    @Override
    public JSONRenderer value(Object value) {
        delegate().value(value);
        return this;
    }

    @Override
    public JSONRenderer value(Object... values) throws RenderingException {
        delegate().value(values);
        return this;
    }
    @Override
    public JSONRenderer value(Iterable<?> values) throws RenderingException {
        delegate().value(values);
        return this;
    }
    
    @Override
    public JSONRenderer value(Iterator<?> values) throws RenderingException {
        delegate().value(values);
        return this;
    }
    
    @Override
    public JSONRenderer value(Map<?, ?> pairs) throws RenderingException {
        delegate().value(pairs);
        return this;
    }
    
    @Override
    public <T> JSONRenderer value(T value, ValueRenderer<? super T> renderer) {
        delegate().value(value, renderer);
        return this;
    }

    @Override
    public JSONRenderer values(Iterable<?> values) {
        delegate().values(values);
        return this;
    }
    
    @Override
    public <T> JSONRenderer values(Iterable<? extends T> values, ValueRenderer<? super T> renderer) {
        delegate().values(values, renderer);
        return this;
    }

    @Override
    public JSONRenderer values(Iterator<?> values) {
        delegate().values(values);
        return this;
    }

    @Override
    public JSONRenderer values(Object... values) {
        delegate().values(values);
        return this;
    }
    
    @Override
    public String build() throws RenderingException {
        return delegate().build();
    }
    
}
