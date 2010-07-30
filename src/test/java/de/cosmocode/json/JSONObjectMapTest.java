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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.Test;

import org.json.JSONObject;

import com.google.common.collect.testing.MapTestSuiteBuilder;
import com.google.common.collect.testing.SampleElements;
import com.google.common.collect.testing.TestMapGenerator;
import com.google.common.collect.testing.features.CollectionFeature;
import com.google.common.collect.testing.features.CollectionSize;
import com.google.common.collect.testing.features.MapFeature;

/**
 * Test {@link JsonObjectMap}.
 *
 * @author Willi Schoenborn
 */
public final class JSONObjectMapTest implements TestMapGenerator<String, Object> {
    
    private JSONObjectMapTest() {
        
    }
    
    /**
     * Creates {@link Test}.
     * 
     * @return {@link Test}
     */
    public static Test suite() {
        return MapTestSuiteBuilder.using(new JSONObjectMapTest()).
            named(JSONObjectMapTest.class.getSimpleName()).
            withFeatures(
                CollectionSize.ANY,
                CollectionFeature.NON_STANDARD_TOSTRING,
                MapFeature.GENERAL_PURPOSE,
                MapFeature.RESTRICTS_KEYS,
                MapFeature.RESTRICTS_VALUES
            ).createTestSuite();
    }
    
    @Override
    public Map<String, Object> create(Object... elements) {
        final Map<String, Object> map = JSON.asMap(new JSONObject());
        for (Object e : elements) {
            @SuppressWarnings("unchecked")
            final Map.Entry<String, Object> entry = Map.Entry.class.cast(e);
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Entry<String, Object>[] createArray(int length) {
        return new Map.Entry[length];
    }
    
    @Override
    public String[] createKeyArray(int length) {
        return new String[length];
    }
    
    @Override
    public Object[] createValueArray(int length) {
        return new Object[length];
    }
    
    @Override
    public Iterable<Entry<String, Object>> order(List<Entry<String, Object>> insertionOrder) {
        return insertionOrder;
    }
    
    @Override
    public SampleElements<Entry<String, Object>> samples() {
        return SampleElements.mapEntries(
            new SampleElements<String>("key", "test", "name", "size", "string"),
            new SampleElements<Object>(Boolean.TRUE, Integer.MAX_VALUE, 123, String.CASE_INSENSITIVE_ORDER, "test")
        );
    }

}
