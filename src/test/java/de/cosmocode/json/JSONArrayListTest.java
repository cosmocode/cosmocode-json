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

import java.util.Arrays;
import java.util.List;

import junit.framework.Test;

import org.json.JSONArray;

import com.google.common.collect.testing.ListTestSuiteBuilder;
import com.google.common.collect.testing.SampleElements;
import com.google.common.collect.testing.TestListGenerator;
import com.google.common.collect.testing.features.CollectionFeature;
import com.google.common.collect.testing.features.CollectionSize;
import com.google.common.collect.testing.features.ListFeature;

/**
 * Test {@link JsonArrayList}.
 *
 * @author Willi Schoenborn
 */
public final class JSONArrayListTest implements TestListGenerator<Object> {
    
    private JSONArrayListTest() {
        
    }

    /**
     * Creates {@link Test}.
     * 
     * @return {@link Test}
     */
    public static Test suite() {
        return ListTestSuiteBuilder.using(new JSONArrayListTest()).
            named(JSONArrayListTest.class.getSimpleName()).
            withFeatures(
                CollectionSize.ANY,
                CollectionFeature.NON_STANDARD_TOSTRING,
                CollectionFeature.SUPPORTS_ADD,
                CollectionFeature.SUPPORTS_ADD_ALL,
                CollectionFeature.KNOWN_ORDER,
                ListFeature.SUPPORTS_SET,
                ListFeature.SUPPORTS_ADD_WITH_INDEX,
                ListFeature.SUPPORTS_ADD_ALL_WITH_INDEX
            ).createTestSuite();
    }

    @Override
    public List<Object> create(Object... elements) {
        return JSON.asList(new JSONArray(Arrays.asList(elements)));
    }

    @Override
    public Object[] createArray(int length) {
        return new Object[length];
    }

    @Override
    public Iterable<Object> order(List<Object> insertionOrder) {
        return insertionOrder;
    }

    @Override
    public SampleElements<Object> samples() {
        return new SampleElements<Object>(
            Boolean.TRUE, 
            Integer.MAX_VALUE, 
            123, 
            new Object(), 
            "test"
        );
    }

}
