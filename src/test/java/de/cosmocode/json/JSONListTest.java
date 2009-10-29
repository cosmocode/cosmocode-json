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
 * Tests {@link JSONList}.
 * 
 * @author schoenborn@schoenborn
 */
public final class JSONListTest implements TestListGenerator<Object> {
    
    private JSONListTest() {
        
    }

    /**
     * Creates {@link Test}.
     * 
     * @return {@link Test}
     */
    public static Test suite() {
        return ListTestSuiteBuilder.using(new JSONListTest()).
            named(JSONListTest.class.getSimpleName()).
            withFeatures(
                CollectionSize.ANY,
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
