package de.cosmocode.json;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JSON {

    private static final Logger log = LoggerFactory.getLogger(JSON.class);

    /**
     * Prevent instantiation
     */
    private JSON() {
        
    }
    
    public static List<Object> asList(JSONArray array) {
        log.debug("Returning {} using {}", JSONList.class.getName(), array);
        return new JSONList(array);
    }
    
    public static Map<String, Object> asMap(JSONObject object) {
        log.debug("Returning {} using {}", JSONMap.class.getName(), object);
        return new JSONMap(object);
    }
    
}
