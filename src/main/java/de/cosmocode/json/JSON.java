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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

import com.google.common.collect.Maps;

import de.cosmocode.collections.utility.UtilityList;
import de.cosmocode.collections.utility.UtilityMap;
import de.cosmocode.rendering.Renderer;

/**
 * Utility class providing static factory methods,
 * for all kind of methods regarding JSON.
 * 
 * @author schoenborn
 */
public final class JSON {

    /**
     * Prevent instantiation.
     */
    private JSON() {
        
    }
    
    /**
     * Provides a {@link UtilityList}-based view on a {@link JSONArray}.
     * 
     * <p>
     *   The returned {@link UtilityList} will be backed by the {@link JSONArray}.
     *   Changes in the {@link JSONArray} will write through to the {@link UtilityList}
     *   and vice versa.
     * </p>
     * 
     * <p>
     *   <b>Note:</b> The returned {@link UtilityList} does NOT support remove operations.
     * </p>
     * 
     * @param array the {@link JSONArray} which will be used as a {@link UtilityList}
     * @throws NullPointerException if array is null
     * @return a {@link UtilityList} backed by the array
     */
    public static UtilityList<Object> asList(JSONArray array) {
        return new JsonArrayList(array);
    }
    
    /**
     * Provides a {@link UtilityMap}-based view on a {@link JSONObject}.
     * 
     * <p>
     *   The returned map will be backed by the json object.
     *   Changes in the json object will write through to the map
     *   and vice versa.
     * </p>
     * 
     * <p>
     *   The returned map is fully compliant and supports
     *   all optional operations specified in the {@link UtilityMap} interface.
     * </p>
     * 
     * @param object the json object which will be used as a map
     * @throws NullPointerException if object is null
     * @return a map backed by the given json object
     */
    public static UtilityMap<String, Object> asMap(JSONObject object) {
        return new JsonObjectMap(object);
    }
    
    /**
     * Creates a {@link JSONObject} based on a
     * {@link LinkedHashMap} which provides insertion
     * order.
     * 
     * @return a new {@link JSONObject} based on a {@link LinkedHashMap}
     */
    public static JSONObject createLinkedJSONObject() {
        final Map<Object, Object> map = Maps.newLinkedHashMap();
        return new JSONObject(map);
    }
    
    /**
     * Creates a {@link JSONObject} backed by a {@link SortedMap}
     * containing all pairs of the provided map.
     * 
     * @param <K> the generic key type
     * @param <V> the generic value type
     * @param map the source map all pairs will be copied from
     * @return a new {@link JSONObject} containing all pairs from map, backed by a sorted map
     */
    public static <K extends Comparable<K>, V> JSONObject createSortedJSONObject(Map<K, V> map) {
        final SortedMap<K, V> sorted = Maps.newTreeMap();
        sorted.putAll(map);
        return new JSONObject(sorted);
    }
    
    /**
     * Creates a new {@link Renderer}.
     * 
     * @since 2.1
     * @return a new {@link Renderer}
     */
    public static Renderer newRenderer() {
        return new JsonRenderer();
    }
    
    /**
     * Creates a new {@link JSONConstructor}.
     * 
     * @deprecated use {@link Renderer}
     * @since 2.1
     * @return a new {@link JSONConstructor}
     */
    @Deprecated
    public static org.json.extension.JSONConstructor newConstructor() {
        return new JsonRendererConstructor(newRenderer());
    }
    
    /**
     * Provides a {@link JSONConstructor}-based view on a {@link JSONWriter}.
     * 
     * <p>
     *   The returned {@link JSONConstructor} will be backed by the {@link JSONWriter}.
     * </p>
     * 
     * @deprecated use {@link Renderer}
     * @param writer the {@link JSONWriter} which will be used as a {@link Renderer}
     * @throws NullPointerException if writer is null
     * @return a {@link JSONConstructor} backed by the writer
     */
    @Deprecated
    public static org.json.extension.JSONConstructor asJSONConstructor(JSONWriter writer) {
        return new JsonWriterConstructor(writer);
    }
    
}
