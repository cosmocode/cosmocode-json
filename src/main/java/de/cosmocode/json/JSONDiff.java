package de.cosmocode.json;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * The jsonDiff is capable of extracting all differences 
 * between two JSON structures, thus returning a JSON 
 * structure only contaning diff key/value pairs.
 * 
 * Changes: Adapted from the original programming language to Java.
 * Adapted also getDifferences to match the splitted cases get inserted,
 * updated and deleted. Implemented required minor methods. 
 * 
 * @author Michael Schøler
 * @author Jesus Ortiz
 * 
 * @see http://www.xn--schler-dya.net/blog/2008/01/15/diffing_json_objects/
 *
 */
public final class JSONDiff {

    private final JSONObject oldJ;
    private final JSONObject newJ;
    private JSONArray diffCyclic;
    
    // TODO remove, its never read except when changes++
    private int changes;
    
    private int level;
    
    public JSONDiff(JSONObject oldJson, JSONObject newJson) throws Exception{
        if (oldJson == null || newJson == null) {
            throw new IllegalArgumentException("Given arguments can not be null");
        }
        this.oldJ = oldJson;
        this.newJ = newJson;
    }
    
    public JSONObject getDifferences() throws JSONException {
        changes = 0;
        return getDifferences(oldJ, newJ);
    }
    
    private JSONObject getDifferences(JSONObject oldJSON, JSONObject newJSON) throws JSONException {
        final JSONObject result = new JSONObject();
        boolean diff;
        if (level == 0) {
            diffCyclic = new JSONArray();
        }
        level += 1;
        Iterator<?> keyIt = newJSON.keys();
        String key = null;
        while (keyIt.hasNext()) {
            key = (String) keyIt.next();
            if (!newJSON.isNull(key)) {
                diff = false;
                if (oldJSON.isNull(key) || !oldJSON.opt(key).getClass().isInstance(newJSON.opt(key))) {
                    diff = true;
                } else if (!oldJSON.opt(key).equals(newJSON.opt(key))) {
                    if (newJSON.opt(key) instanceof JSONArray || newJSON.opt(key) instanceof JSONObject) {
                        if (indexOf(diffCyclic, newJSON.opt(key)) >= 0) {
                            break;
                        } else if (newJSON.opt(key) instanceof JSONArray) { 
                            if (((JSONArray) oldJSON.opt(key)).length() != ((JSONArray) newJSON.opt(key)).length() || 
                                oldJSON.opt(key) != newJSON.opt(key))
                                if (!compare((JSONArray) oldJSON.optJSONArray(key), 
                                    (JSONArray) newJSON.optJSONArray(key)))
                                    diff = true;
                        } else if (oldJSON.opt(key) instanceof JSONArray || oldJSON.opt(key) instanceof JSONObject) {
                            final JSONObject recursiveDiff = getDifferences((JSONObject) oldJSON.opt(key), 
                                (JSONObject) newJSON.opt(key));
                            if ((recursiveDiff.keys()).hasNext()) { 
                                result.put(key, recursiveDiff);
                            }
                        } else {
                            diff = true;
                        }
                        diffCyclic.put(newJSON.opt(key));
                    } else if (!oldJSON.opt(key).equals(newJSON.opt(key))) {
                        diff = true;
                    }
                }
                if (diff) { 
                    result.put(key, newJSON.opt(key));
                    changes++;
                }
            }
        }

        keyIt = oldJSON.keys();
        key = null;
        while (keyIt.hasNext()) {
            key = (String) keyIt.next();
            if (!oldJSON.isNull(key)) {
                if (newJSON.isNull(key)) {
                    result.put(key, oldJSON.opt(key));
                    changes++;
                }
            }
        }
        level -= 1;
        return result;
        
    }
        
    public JSONObject getUpdated() throws JSONException {
        changes = 0;
        return getUpdated(oldJ, newJ);
        
    }
        
    private JSONObject getUpdated(JSONObject oldJSON, JSONObject newJSON) throws JSONException {
        final JSONObject updated = new JSONObject();
        boolean diff;
        if (level == 0) {
            diffCyclic = new JSONArray();
        }
        level += 1;

        final Iterator<?> keyIt = newJSON.keys();
        String key = null;
        while (keyIt.hasNext()) {
            key = (String) keyIt.next();
            if (!newJSON.isNull(key)) {
                diff = false;
                if (! oldJSON.isNull(key)) {
                    if (! oldJSON.opt(key).getClass().isInstance(newJSON.opt(key))) {
                        diff = true;
                    }
                    else if (!oldJSON.opt(key).equals(
                            newJSON.opt(key))) {
                        if (newJSON.opt(key) instanceof JSONArray || newJSON.opt(key) instanceof JSONObject) {
                            if (indexOf(diffCyclic, newJSON.opt(key)) >= 0) {
                                break;
                            } else if (newJSON.opt(key) instanceof JSONArray) {
                                if (((JSONArray) oldJSON.opt(key)).length() != ((JSONArray) newJSON.opt(key)).length()
                                    || oldJSON.opt(key) != newJSON.opt(key))
                                    if (!compare((JSONArray) oldJSON.opt(key), (JSONArray) newJSON.opt(key)))
                                        diff = true;
                            } else if (oldJSON.opt(key) instanceof JSONArray || 
                                oldJSON.opt(key) instanceof JSONObject) {
                                final JSONObject recursiveUpd = getUpdated(
                                        (JSONObject) oldJSON.opt(key),
                                        (JSONObject) newJSON.opt(key));
                                if ((recursiveUpd.keys()).hasNext()) {
                                    updated.put(key, recursiveUpd);
                                }
                            } else {
                                diff = true;
                            }
                            diffCyclic.put(newJSON.opt(key));
                        }
                        else if (!oldJSON.opt(key).equals(newJSON.opt(key))) {
                            diff = true;
                        }
                    }
                }
                if (diff) {
                    updated.put(key, newJSON.opt(key));
                    changes++;
                }
            }
        }
        level -= 1;
        return updated;
    }
    
    public JSONObject getUpdatedExt() throws JSONException {
        changes = 0;
        return getUpdatedExt(oldJ,newJ);
    }
        
    private JSONObject getUpdatedExt(JSONObject oldJSON, JSONObject newJSON) throws JSONException{
        final JSONObject updatedExt = new JSONObject();
        boolean diffT;
        if (level == 0){
            diffCyclic = new JSONArray();
        }
        level += 1;

        final Iterator<?> keyIt = newJSON.keys();
        String key = null;
        while (keyIt.hasNext()) {
            key = (String) keyIt.next();
            if (!newJSON.isNull(key)) {
                diffT = false;
                if (! oldJSON.isNull(key)) {
                    if (!oldJSON.opt(key).getClass().isInstance(newJSON.opt(key))) {
                        diffT = true;
                    } else if (!oldJSON.opt(key).equals(newJSON.opt(key))) {
                        if (newJSON.opt(key) instanceof JSONArray || newJSON.opt(key) instanceof JSONObject) {
                            if (indexOf(diffCyclic, newJSON.opt(key)) >= 0) {
                                break;
                            } else if (newJSON.opt(key) instanceof JSONArray) {
                                if (((JSONArray) oldJSON.opt(key)).length() != 
                                    ((JSONArray) newJSON.opt(key)).length() || oldJSON.opt(key) != newJSON.opt(key))
                                    if (!compare((JSONArray) oldJSON.opt(key), (JSONArray) newJSON.opt(key)))
                                        diffT = true;
                            } else if (oldJSON.opt(key) instanceof JSONArray || 
                                oldJSON.opt(key) instanceof JSONObject) {
                                final JSONObject recursiveUpd = getUpdatedExt(
                                        (JSONObject) oldJSON.opt(key),
                                        (JSONObject) newJSON.opt(key));
                                if ((recursiveUpd.keys()).hasNext()) {
                                    updatedExt.put(key, recursiveUpd);
                                }
                            } else {
                                diffT = true;
                            }
                            diffCyclic.put(newJSON.opt(key));
                        }
                        else if (!oldJSON.opt(key).equals(newJSON.opt(key))) {
                            diffT = true;
                        }
                    }
                }
                if (diffT) {
                    updatedExt.put(key, getUpdatedValue(oldJSON, newJSON, key));
                    changes++;
                }
            }
        }
        level -= 1;
        return updatedExt;
    }
    
    
    public JSONObject getInserted() throws JSONException {
        changes = 0;
        return getInserted(oldJ,newJ);
    }
        
    private JSONObject getInserted(JSONObject oldJSON, JSONObject newJSON) throws JSONException {
        final JSONObject inserted = new JSONObject();
        boolean diff;
        if (level == 0){
            diffCyclic = new JSONArray();
        }
        level += 1;

        final Iterator<?> keyIt = newJSON.keys();
        String key = null;
        while (keyIt.hasNext()) {
            key = (String) keyIt.next();
            if (!newJSON.isNull(key)) {
                diff = false;
                if (oldJSON.isNull(key)) {
                    diff = true; 
                } else if (!oldJSON.opt(key).equals(newJSON.opt(key))) {
                    if (newJSON.opt(key) instanceof JSONArray || newJSON.opt(key) instanceof JSONObject) {
                        if (indexOf(diffCyclic, newJSON.opt(key)) >= 0) {
                            break;
                        } else if (oldJSON.opt(key) instanceof JSONArray || oldJSON.opt(key) instanceof JSONObject) {
                            final JSONObject recursiveIns = getInserted((JSONObject) oldJSON.opt(key), 
                                (JSONObject) newJSON.opt(key));
                            if ((recursiveIns.keys()).hasNext()) {
                                inserted.put(key, recursiveIns);
                            }
                        }
                        diffCyclic.put(newJSON.opt(key));
                    }
                }
                if (diff) {
                    inserted.put(key, newJSON.opt(key));
                    changes++;
                }
            }
        }
        
        level -= 1;
        return inserted;
    }
    
    public JSONObject getDeleted() throws JSONException {
        changes = 0;
        return getDeleted(oldJ, newJ);
    }
        
    private JSONObject getDeleted(JSONObject oldJSON, JSONObject newJSON) throws JSONException {
        final JSONObject deleted = new JSONObject();
        boolean diff;
        if (level == 0) {
            diffCyclic = new JSONArray();
        }
        level += 1;
        
        Iterator<?> keyIt = newJSON.keys();
        String key = null;
        while (keyIt.hasNext()) {
            key = (String) keyIt.next();
            if (!newJSON.isNull(key)) {
                diff = false;
                if (!newJSON.opt(key).equals(oldJSON.opt(key))) {
                    if (newJSON.opt(key) instanceof JSONArray || newJSON.opt(key) instanceof JSONObject) {
                        if (indexOf(diffCyclic, newJSON.opt(key)) >= 0) {
                            break;
                        } else if (oldJSON.opt(key) instanceof JSONArray || oldJSON.opt(key) instanceof JSONObject) {
                            final JSONObject recursiveDel = getDeleted((JSONObject) oldJSON.opt(key), 
                                (JSONObject) newJSON.opt(key));
                            if ((recursiveDel.keys()).hasNext()) {
                                deleted.put(key, recursiveDel);
                            }
                        }
                        diffCyclic.put(newJSON.opt(key));
                    } 
                }
                if (diff) {
                    deleted.put(key, newJSON.opt(key));
                    changes++;
                }
            }
        }
        
        keyIt = oldJSON.keys();
        while (keyIt.hasNext()) {
            key = (String) keyIt.next();
            if (!oldJSON.isNull(key)) {
                if (newJSON.isNull(key)) {
                    deleted.put(key, oldJSON.opt(key));
                    changes++;
                }
            }
        }
        
        level -= 1;
        return deleted;
    }
    
    private boolean compare(JSONArray first, JSONArray second) throws JSONException {
        if (first.length() == second.length()) {
            for (int i = 0; i < second.length(); i++) {
                if (first.opt(i) instanceof JSONArray) {
                    if (!compare((JSONArray) first.opt(i), (JSONArray) second.opt(i)))
                        return false;
                } else if (!first.opt(i).equals(second.opt(i)))
                    return false;
            }
            return true;
        }
        return false;
    }

    private int indexOf(JSONArray ja, Object o) {
        for (int i = 0; i < diffCyclic.length(); i++) {
            if ((ja.opt(i)).equals(o))
                return i;
        }
        
        return -1;
    }
    
    
    private Object getUpdatedValue(JSONObject oldjo, JSONObject newjo, String jokey) throws JSONException {
        final JSONObject jotemp = new JSONObject();
        jotemp.put("old", oldjo.get(jokey));
        jotemp.put("new", newjo.get(jokey));
        return jotemp;
    }
    
}
