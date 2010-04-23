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

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.junit.After;

import com.google.common.collect.Maps;

public abstract class AbstractJSONRendererWriterTest extends AbstractJSONRendererTest {

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
