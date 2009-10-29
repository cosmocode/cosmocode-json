package de.cosmocode.json;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * The returned {@link List} will be backed by the {@link JSONArray}.
     * Changes in the {@link JSONArray} will write through to the {@link List}
     * and vice versa.
     * 
     * The returned {@link List} is fully compliant and supports
     * all optional operations specified in the {@link List} interface.
     * 
     * @param array the {@link JSONArray} which will be used as a {@link List}
     * @return a {@link List} backed by the array
     */
    public static List<Object> asList(JSONArray array) {
        log.debug("Returning {} using {}", JSONList.class.getName(), array);
        return new JSONList(array);
    }
    
    /**
     * Provides a {@link Map}-based view on a {@link JSONObject}.
     * 
     * The returned {@link Map} will be backed by the {@link JSONObject}.
     * Changes in the {@link JSONObject} will write through to the {@link Map}
     * and vice versa.
     * 
     * The returned {@link Map} is fully compliant and supports
     * all optional operations specified in the {@link Map} interface.
     * 
     * @param object the {@link JSONObject} which will be used as {@link Map}
     * @return a {@link Map} backed be the object
     */
    public static Map<String, Object> asMap(JSONObject object) {
        log.debug("Returning {} using {}", JSONMap.class.getName(), object);
        return new JSONMap(object);
    }
    
}
