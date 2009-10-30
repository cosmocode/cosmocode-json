package de.cosmocode.json;

import java.io.Writer;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONWriter;
import org.json.extension.JSONConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import de.cosmocode.commons.DateMode;

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
     * Provides a {@link List}-based view on a {@link JSONArray}.
     * 
     * <p>
     *   The returned {@link List} will be backed by the {@link JSONArray}.
     *   Changes in the {@link JSONArray} will write through to the {@link List}
     *   and vice versa.
     * </p>
     * 
     * <p>
     *   <b>Note:</b> The returned {@link List} does NOT support remove operations.
     * </p>
     * 
     * @param array the {@link JSONArray} which will be used as a {@link List}
     * @throws NullPointerException if array is null
     * @return a {@link List} backed by the array
     */
    public static List<Object> asList(JSONArray array) {
        log.debug("Returning {} using {}", JSONArrayList.class.getName(), array);
        return new JSONArrayList(array);
    }
    
    /**
     * Provides a {@link Map}-based view on a {@link JSONObject}.
     * 
     * <p>
     *   The returned {@link Map} will be backed by the {@link JSONObject}.
     *   Changes in the {@link JSONObject} will write through to the {@link Map}
     *   and vice versa.
     * </p>
     * 
     * <p>
     *   The returned {@link Map} is fully compliant and supports
     *   all optional operations specified in the {@link Map} interface.
     * </p>
     * 
     * @param object the {@link JSONObject} which will be used as {@link Map}
     * @throws NullPointerException if object is null
     * @return a {@link Map} backed be the object
     */
    public static Map<String, Object> asMap(JSONObject object) {
        log.debug("Returning {} using {}", JSONObjectMap.class.getName(), object);
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
        return new JSONObject(Maps.newLinkedHashMap());
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
     * Creates a {@link JSONRenderer} which directly writes
     * his encoded JSON data using the given writer instance.
     * 
     * @param writer the target of the json encoded data
     * @throws NullPointerException if writer is null
     * @return a {@link JSONRenderer} writing to the {@link Writer} instance
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
     * @throws NullPointerException if constructor is null
     * @return a {@link JSONRenderer} backed by the constructor
     */
    public static JSONRenderer asJSONRenderer(JSONConstructor constructor) {
        log.debug("Returning new {} as {} using {}", new Object[] {
            JSONConstructorRenderer.class.getName(), JSONRenderer.class.getName(), constructor.getClass().getName()
        });
        return new JSONConstructorRenderer(constructor, DateMode.JAVA);
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
        log.debug("Returning new {} as {} using {}", new Object[] {
            JSONRendererConstructor.class.getName(), JSONConstructor.class.getName(), renderer.getClass().getName()
        });
        return new JSONRendererConstructor(renderer);
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
        log.debug("Returning new {} as {} using", new Object[] {
            JSONWriterConstructor.class.getName(), JSONConstructor.class.getName(), writer.getClass().getName()
        });
        return new JSONWriterConstructor(writer);
    }
    
    /**
     * Retrieves the {@link Writer} instance a given {@link JSONWriter}
     * is using by reflecting the protected instance variable.
     *
     * <p>
     *   <strong>Note:</strong> This method is a dirty hack (and the author
     *   is aware of that fact). You should generally avoid to use this method
     *   or even the way it does things. We just need it to provide backward
     *   compatability to {@link JSONWriter} and {@link JSONConstructor}
     * </p>
     * 
     * @param json the holder of the {@link Writer} instance
     * @throws NullPointerException if json is null
     * @return the stolen {@link Writer} instance
     */
    static Writer stealWriter(JSONWriter json) {
        if (json == null) throw new NullPointerException("JSONWriter must not be null");
        final Class<JSONWriter> type = JSONWriter.class;
        log.debug("Trying to extract {} from {}", Writer.class.getName(), json.getClass().getName());
        try {
            final Field field = type.getDeclaredField("writer");
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
