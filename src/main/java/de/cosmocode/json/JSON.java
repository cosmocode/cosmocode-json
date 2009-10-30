package de.cosmocode.json;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.extension.JSONConstructor;
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
        log.debug("Returning {} using {}", JSONList.class.getName(), array);
        return new JSONList(array);
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
        log.debug("Returning {} using {}", JSONMap.class.getName(), object);
        return new JSONMap(object);
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
            JSONConstructorAdapter.class.getName(), JSONRenderer.class.getName(), constructor.getClass().getName()
        });
        return new JSONConstructorAdapter(constructor);
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
            JSONRendererAdapter.class.getName(), JSONConstructor.class.getName(), renderer.getClass().getName()
        });
        return new JSONRendererAdapter(renderer);
    }
    
}
