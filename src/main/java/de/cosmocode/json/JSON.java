package de.cosmocode.json;

import java.io.Writer;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONWriter;
import org.json.extension.JSONConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import de.cosmocode.collections.utility.UtilityList;
import de.cosmocode.collections.utility.UtilityMap;
import de.cosmocode.commons.TrimMode;

/**
 * Utility class providing static factory methods,
 * for all kind of methods regarding JSON.
 * 
 * @author schoenborn
 */
public final class JSON {

    private static final Logger log = LoggerFactory.getLogger(JSON.class);

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
        return new JSONArrayList(array);
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
        return new JSONObjectMap(object);
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
     * Creates a {@link JSONRenderer} which stores
     * his encoded JSON data internally. Call
     * {@link JSONRenderer#toString()} to render it.
     * 
     * @return a new {@link JSONRenderer}
     */
    public static JSONRenderer createJSONRenderer() {
        return JSON.asJSONRenderer(new JSONStringer());
    }

    /**
     * Returns a {@link JSONRenderer} which uses the given {@link TrimMode} to
     * trim all values passed to {@link JSONRenderer#value(CharSequence)} and
     * {@link JSONRenderer#plain(String)}.
     * 
     * @param renderer the renderer the trimming {@link JSONRenderer} will be backed by
     * @param trimMode the {@link TrimMode} to use
     * @return a new {@link JSONRenderer} backed by the given renderer
     */
    public static JSONRenderer trimming(JSONRenderer renderer, TrimMode trimMode) {
        return new TrimmingJSONRenderer(renderer, trimMode);
    }
    
    /**
     * Creates a {@link JSONRenderer} which directly writes
     * his encoded JSON data using the given writer instance.
     * 
     * @param writer the target of the json encoded data
     * @return a {@link JSONRenderer} writing to the {@link Writer} instance
     * @throws NullPointerException if writer is null
     */
    public static JSONRenderer createJSONRenderer(Writer writer) {
        if (writer == null) throw new NullPointerException("Writer must not be null");
        return JSON.asJSONRenderer(new JSONWriter(writer));
    }
    
    /**
     * Provides a {@link JSONRenderer}-based view on a {@link JSONConstructor}.
     * 
     * <p>
     *   The returned {@link JSONRenderer} will be backed by the {@link JSONConstructor}.
     * </p>
     * 
     * @param constructor the {@link JSONConstructor} which will be used as a {@link JSONRenderer}
     * @return a {@link JSONRenderer} backed by the constructor
     * @throws NullPointerException if constructor is null
     */
    public static JSONRenderer asJSONRenderer(JSONConstructor constructor) {
        if (constructor instanceof JSONRenderer) {
            return JSONRenderer.class.cast(constructor);
        } else {
            return new JSONConstructorRenderer(constructor);
        }
    }
    
    /**
     * Provides a {@link JSONRenderer}-based view on a {@link JSONWriter}.
     * 
     * <p>
     *   The returned {@link JSONRenderer} will be backed by the {@link JSONWriter}.
     * </p>
     * 
     * @param writer the {@link JSONWriter} which will be used as a {@link JSONRenderer}
     * @throws NullPointerException if writer is null
     * @return a {@link JSONRenderer} backed by the writer
     */
    public static JSONRenderer asJSONRenderer(JSONWriter writer) {
        return JSON.asJSONRenderer(JSON.asJSONConstructor(writer));
    }
    
    /**
     * Provides a {@link JSONConstructor}-based view on a {@link JSONRenderer}.
     * 
     * <p>
     *   The returned {@link JSONConstructor} will be backed by the {@link JSONConstructor}.
     * </p>
     * 
     * @param renderer the {@link JSONRenderer} which will be used as a {@link JSONConstructor}
     * @throws NullPointerException if renderer is null
     * @return a {@link JSONConstructor} backed by the renderer
     */
    public static JSONConstructor asJSONConstructor(JSONRenderer renderer) {
        if (renderer instanceof JSONConstructor) {
            return JSONConstructor.class.cast(renderer);
        } else {
            return new JSONRendererConstructor(renderer);
        }
    }
    
    /**
     * Provides a {@link JSONConstructor}-based view on a {@link JSONWriter}.
     * 
     * <p>
     *   The returned {@link JSONConstructor} will be backed by the {@link JSONWriter}.
     * </p>
     * 
     * @param writer the {@link JSONWriter} which will be used as a {@link JSONConstructor}
     * @throws NullPointerException if writer is null
     * @return a {@link JSONConstructor} backed by the writer
     */
    public static JSONConstructor asJSONConstructor(JSONWriter writer) {
        return new JSONWriterConstructor(writer);
    }
    
    /**
     * Retrieves the {@link Writer} instance a given {@link JSONWriter}
     * is using by reflecting the protected instance variable.
     *
     * <p>
     *   <strong>Note:</strong> This method is a dirty hack (and the author
     *   is aware of that fact). You should generally avoid to use this method
     *   or even the way it does its job. We just need it to provide backward
     *   compatability to {@link JSONWriter} and {@link JSONConstructor}
     * </p>
     * 
     * @param json the holder of the {@link Writer} instance
     * @throws NullPointerException if json is null
     * @return the stolen {@link Writer} instance
     */
    static Writer stealWriter(JSONWriter json) {
        if (json == null) throw new NullPointerException("JSONWriter must not be null");
        log.debug("Trying to extract {} from {}", Writer.class.getName(), json.getClass().getName());
        try {
            final Field field = JSONWriter.class.getDeclaredField("writer");
            log.debug("Getting field {}: {}", field.getName(), field);
            final boolean accessible = field.isAccessible();
            log.debug("Setting accessible to {}", Boolean.TRUE);
            field.setAccessible(true);
            final Object result = field.get(json);
            log.debug("Getting the field value of {}: {}", field.getName(), result.getClass().getName());
            field.setAccessible(accessible);
            log.debug("Setting accessible back to {}", Boolean.valueOf(accessible));
            return Writer.class.cast(result);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
    
}
