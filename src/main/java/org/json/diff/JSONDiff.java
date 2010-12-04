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

package org.json.diff;

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
 */
public class JSONDiff {

    private JSONObject oldJ;
    private JSONObject newJ;
    private JSONArray diffCyclic;
    private int changes;
    private int level;
    
    public JSONDiff(JSONObject oldJson, JSONObject newJson) {
        if (oldJson == null || newJson == null) {
            throw new IllegalArgumentException("Given arguments can not be null");
        }
        this.oldJ = oldJson;
        this.newJ = newJson;
    }
    
    /**
     * Calculates the differences.
     * 
     * @return the differences
     * @throws JSONException if something failed
     */
    public JSONObject getDifferences() throws JSONException {
        changes = 0;
        return getDifferences(oldJ, newJ);
    }
    
    /* CHECKSTYLE:OFF */
    private JSONObject getDifferences(JSONObject oldJSON, JSONObject newJSON) throws JSONException {
    /* CHECKSTYLE:ON */
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
                                oldJSON.opt(key) != newJSON.opt(key)) {
                                if (!compare(oldJSON.optJSONArray(key), newJSON.optJSONArray(key))) {
                                    diff = true;
                                }
                            }
                        } else if (oldJSON.opt(key) instanceof JSONArray || oldJSON.opt(key) instanceof JSONObject) {
                            final JSONObject recursiveDiff = getDifferences(
                                    oldJSON.optJSONObject(key), newJSON.optJSONObject(key));
                            if (recursiveDiff.keys().hasNext()) { 
                                result.put(key, recursiveDiff);
                            }
                        } else {
                            diff = true;
                        }
                        diffCyclic.put(newJSON.opt(key));
                    }  else if (!oldJSON.opt(key).equals(newJSON.opt(key))) {
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
        
    /**
     * Returns the updated pairs.
     * 
     * @return the update pairs
     * @throws JSONException if something failed
     */
    public JSONObject getUpdated() throws JSONException {
        changes = 0;
        return getUpdated(oldJ, newJ);
        
    }

    /* CHECKSTYLE:OFF */
    private JSONObject getUpdated(JSONObject oldJSON, JSONObject newJSON) throws JSONException{
    /* CHECKSTYLE:ON */
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
                    } else if (!oldJSON.opt(key).equals(
                            newJSON.opt(key))) {
                        if (newJSON.opt(key) instanceof JSONArray || newJSON.opt(key) instanceof JSONObject) {
                            if (indexOf(diffCyclic, newJSON.opt(key)) >= 0) {
                                break;
                            } else if (newJSON.opt(key) instanceof JSONArray) {
                                if ((oldJSON.optJSONArray(key)).length() != newJSON.optJSONArray(key).length() 
                                    || oldJSON.opt(key) != newJSON.opt(key)) {
                                    if (!compare(oldJSON.optJSONArray(key), newJSON.optJSONArray(key))) {
                                        diff = true;
                                    }
                                }
                            } else if (oldJSON.opt(key) instanceof JSONArray || 
                                    oldJSON.opt(key) instanceof JSONObject) {
                                final JSONObject recursiveUpd = getUpdated(
                                    oldJSON.optJSONObject(key),
                                    newJSON.optJSONObject(key));
                                if ((recursiveUpd.keys()).hasNext()) {
                                    updated.put(key, recursiveUpd);
                                }
                            } else {
                                diff = true;
                            }
                            diffCyclic.put(newJSON.opt(key));
                        } else if (!oldJSON.opt(key).equals(newJSON.opt(key))) {
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
    
    /**
     * Returns the updated pairs recursively?
     * 
     * @return update pairs
     * @throws JSONException if something failed
     */
    public JSONObject getUpdatedExt() throws JSONException {
        changes = 0;
        return getUpdatedExt(oldJ, newJ);
    }

    /* CHECKSTYLE:OFF */
    private JSONObject getUpdatedExt(JSONObject oldJSON, JSONObject newJSON) throws JSONException{
    /* CHECKSTYLE:ON */
        final JSONObject updatedExt = new JSONObject();
        boolean diffT;
        if (level == 0) {
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
                                if (oldJSON.optJSONArray(key).length() != newJSON.optJSONArray(key).length() 
                                        || oldJSON.opt(key) != newJSON.opt(key))
                                    if (!compare(oldJSON.optJSONArray(key), newJSON.optJSONArray(key)))
                                        diffT = true;
                            } else if (oldJSON.opt(key) instanceof JSONArray || 
                                    oldJSON.opt(key) instanceof JSONObject) {
                                final JSONObject recursiveUpd = getUpdatedExt(
                                        oldJSON.optJSONObject(key),
                                        newJSON.optJSONObject(key));
                                if ((recursiveUpd.keys()).hasNext()) {
                                    updatedExt.put(key, recursiveUpd);
                                }
                            } else {
                                diffT = true;
                            }
                            diffCyclic.put(newJSON.opt(key));
                        } else if (!oldJSON.opt(key).equals(newJSON.opt(key))) {
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
    
    /**
     * Returns the inserted pairs. 
     * 
     * @return the inserted pairs
     * @throws JSONException if something failed
     */
    public JSONObject getInserted() throws JSONException {
        changes = 0;
        return getInserted(oldJ, newJ);
    }

    /* CHECKSTYLE:OFF */
    private JSONObject getInserted(JSONObject oldJSON, JSONObject newJSON) throws JSONException {
    /* CHECKSTYLE:ON */
        final JSONObject inserted = new JSONObject();
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
                if (oldJSON.isNull(key)) {
                    diff = true; 
                } else if (!oldJSON.opt(key).equals(newJSON.opt(key))) {
                    if (newJSON.opt(key) instanceof JSONArray || newJSON.opt(key) instanceof JSONObject) {
                        if (indexOf(diffCyclic, newJSON.opt(key)) >= 0) {
                            break;
                        } else if (oldJSON.opt(key) instanceof JSONArray || oldJSON.opt(key) instanceof JSONObject) {
                            final JSONObject recursiveIns = getInserted(
                                oldJSON.optJSONObject(key), newJSON.optJSONObject(key));
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
    
    /**
     * Returns deleted pairs.
     * 
     * @return the deleted pairs
     * @throws JSONException if something failed
     */
    public JSONObject getDeleted() throws JSONException {
        changes = 0;
        return getDeleted(oldJ, newJ);
    }

    /* CHECKSTYLE:OFF */
    private JSONObject getDeleted(JSONObject oldJSON, JSONObject newJSON) throws JSONException {
    /* CHECKSTYLE:ON */        
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
                            final JSONObject recursiveDel = getDeleted(
                                    oldJSON.optJSONObject(key), newJSON.optJSONObject(key));
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
                    if (!compare(first.optJSONArray(i), second.optJSONArray(i))) {
                        return false;
                    }
                } else if (!first.opt(i).equals(second.opt(i))) {
                    return false;
                }
            }
            return true;
        }
        
        return false;
    }

    
    private int indexOf(JSONArray ja, Object o) {
        for (int i = 0; i < diffCyclic.length(); i++) {
            if ((ja.opt(i)).equals(o)) {
                return i;
            }
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
