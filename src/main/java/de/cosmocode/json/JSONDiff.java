package de.cosmocode.json;

import java.io.StringWriter;
import java.io.Writer;
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
                                if (!compare(((JSONArray)oldJSON.optJSONArray(key)), ((JSONArray)newJSON.optJSONArray(key))))
                                    diff = true;
                        } else if (oldJSON.opt(key) instanceof JSONArray || oldJSON.opt(key) instanceof JSONObject) {
                            JSONObject recursiveDiff = getDifferences((JSONObject) oldJSON.opt(key), (JSONObject) newJSON.opt(key));
                            if((recursiveDiff.keys()).hasNext()){ 
                                result.put(key,recursiveDiff);
                            }
                        }
                        else {
                            diff = true;
                        }
                        diffCyclic.put(newJSON.opt(key));
                    } 
                    else if (!oldJSON.opt(key).equals(newJSON.opt(key))) {
                        diff = true;
                    }
                }
                if (diff) { 
                    result.put(key,newJSON.opt(key));
                    changes++;
                }
            }
        }

        keyIt = oldJSON.keys();
        key = null;
        while(keyIt.hasNext()){
            key = (String) keyIt.next();
            if(!oldJSON.isNull(key)){
                if(newJSON.isNull(key)){
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
        return getUpdated(oldJ,newJ);
        
    }
        
    private JSONObject getUpdated(JSONObject oldJSON, JSONObject newJSON) throws JSONException{
        JSONObject updated = new JSONObject();
        boolean diff;
        if(level == 0){
            diffCyclic = new JSONArray();
        }
        level += 1;

        Iterator<?> keyIt = newJSON.keys();
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
                        if ( newJSON.opt(key) instanceof JSONArray || newJSON.opt(key) instanceof JSONObject) {
                            if (indexOf(diffCyclic, newJSON.opt(key)) >= 0) {
                                break;
                            } else if ( newJSON.opt(key) instanceof JSONArray) {
                                if (((JSONArray) oldJSON.opt(key)).length() != ((JSONArray) newJSON.opt(key)).length() || oldJSON.opt( key) != newJSON.opt(key))
                                    if (!compare(((JSONArray) oldJSON.opt(key)), ((JSONArray) newJSON.opt(key))))
                                        diff = true;
                            } else if ( oldJSON.opt(key) instanceof JSONArray || oldJSON.opt(key) instanceof JSONObject) {
                                JSONObject recursiveUpd = getUpdated(
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
        JSONObject updatedExt = new JSONObject();
        boolean diffT;
        if(level == 0){
            diffCyclic = new JSONArray();
        }
        level += 1;

        Iterator<?> keyIt = newJSON.keys();
        String key = null;
        while (keyIt.hasNext()) {
            key = (String) keyIt.next();
            if (!newJSON.isNull(key)) {
                diffT = false;
                if (! oldJSON.isNull( key)) {
                    if (!oldJSON.opt(key).getClass().isInstance(newJSON.opt(key))) {
                        diffT = true;
                    }
                    else if (!oldJSON.opt( key).equals(newJSON.opt( key))) {
                        if ( newJSON.opt( key) instanceof JSONArray || newJSON.opt( key) instanceof JSONObject) {
                            if (indexOf(diffCyclic, newJSON.opt( key)) >= 0) {
                                break;
                            } else if ( newJSON.opt( key) instanceof JSONArray) {
                                if (((JSONArray) oldJSON.opt( key)).length() != ((JSONArray) newJSON.opt( key)).length() || oldJSON.opt( key) != newJSON.opt( key))
                                    if (!compare(((JSONArray) oldJSON.opt(key)), ((JSONArray) newJSON.opt( key))))
                                        diffT = true;
                            } else if ( oldJSON.opt( key) instanceof JSONArray || oldJSON.opt( key) instanceof JSONObject) {
                                JSONObject recursiveUpd = getUpdatedExt(
                                        (JSONObject) oldJSON.opt( key),
                                        (JSONObject) newJSON.opt( key));
                                if ((recursiveUpd.keys()).hasNext()) {
                                    updatedExt.put(key, recursiveUpd);
                                }
                            } else {
                                diffT = true;
                            }
                            diffCyclic.put(newJSON.opt( key));
                        }
                        else if (!oldJSON.opt( key).equals(newJSON.opt( key))) {
                            diffT = true;
                        }
                    }
                }
                if (diffT) {
                    updatedExt.put(key, getUpdatedValue(oldJSON,newJSON, key));
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
        JSONObject inserted = new JSONObject();
        boolean diff;
        if(level == 0){
            diffCyclic = new JSONArray();
        }
        level += 1;

        Iterator<?> keyIt = newJSON.keys();
        String key = null;
        while(keyIt.hasNext()){
            key = (String) keyIt.next();
            if(!newJSON.isNull(key)){
                diff = false;
                if (  oldJSON.isNull(key) ) {
                    diff = true; 
                }
                else if(!oldJSON.opt(key).equals(newJSON.opt(key))){
                    if( newJSON.opt(key) instanceof JSONArray || newJSON.opt(key) instanceof JSONObject){
                        if (indexOf(diffCyclic, newJSON.opt(key)) >= 0) {
                            break;
                        }
                        else if ( newJSON.opt(key) instanceof JSONArray){
                            if(((JSONArray)oldJSON.opt(key)).length() != ((JSONArray)newJSON.opt(key)).length()  || !oldJSON.opt(key).equals(newJSON.opt(key))){
                                if(!compare(((JSONArray)oldJSON.opt(key)), ((JSONArray)newJSON.opt(key)))){
//                                    diff = true;
                                }
                            }
                        }
                        else if(  oldJSON.opt(key) instanceof JSONArray || oldJSON.opt(key) instanceof JSONObject){
                            JSONObject recursiveIns = getInserted((JSONObject) oldJSON.opt(key), (JSONObject) newJSON.opt(key));
                            if((recursiveIns.keys()).hasNext()){
                                inserted.put(key,recursiveIns);
                            }
                        }
                        diffCyclic.put(newJSON.opt(key));
                    }
                }
                if (diff) {
                    inserted.put(key,newJSON.opt(key));
                    changes++;
                }
            }
        }
        
        level -= 1;
        return inserted;
    }
    
    public JSONObject getDeleted() throws JSONException {
        changes = 0;
        return getDeleted(oldJ,newJ);
    }
        
    private JSONObject getDeleted(JSONObject oldJSON, JSONObject newJSON) throws JSONException {
        JSONObject deleted = new JSONObject();
        boolean diff;
        if(level == 0){
            diffCyclic = new JSONArray();
        }
        level += 1;
        
        Iterator<?> keyIt = newJSON.keys();
        String key = null;
        while(keyIt.hasNext()){
            key = (String) keyIt.next();
            if(!newJSON.isNull(key)){
                diff = false;
                if( !newJSON.opt(key).equals(oldJSON.opt(key)) ){
                    if( newJSON.opt(key) instanceof JSONArray || newJSON.opt(key) instanceof JSONObject){
                        if (indexOf(diffCyclic, newJSON.opt(key)) >= 0) {
                            break;
                        }
                        else if ( newJSON.opt(key) instanceof JSONArray){
                            //newJSON.optJSONArray(key) instanceof JSONArray
                            if(((JSONArray)oldJSON.opt(key)).length() != ((JSONArray)newJSON.opt(key)).length() || !oldJSON.opt(key).equals(newJSON.opt(key))){
                                if(!compare(((JSONArray)oldJSON.opt(key)), ((JSONArray)newJSON.opt(key)))){
//                                    diff = true;
                                }
                            }
                        }
                        else if(  oldJSON.opt(key) instanceof JSONArray || oldJSON.opt(key) instanceof JSONObject){
                            JSONObject recursiveDel = getDeleted((JSONObject) oldJSON.opt(key), (JSONObject) newJSON.opt(key));
                            if((recursiveDel.keys()).hasNext()){
                                deleted.put(key,recursiveDel);
                            }
                        }
                        diffCyclic.put(newJSON.opt(key));
                    } 
                }
                if (diff) {
                    deleted.put(key,newJSON.opt(key));
                    changes++;
                }
            }
        }
        
        keyIt = oldJSON.keys();
        while(keyIt.hasNext()){
            key = (String) keyIt.next();
            if(!oldJSON.isNull(key)){
                if( newJSON.isNull(key)){
                    deleted.put(key, oldJSON.opt(key));
                    changes++;
                }
            }
        }
        
        level -= 1;
        return deleted;
    }
    
    private boolean compare(JSONArray first, JSONArray second) throws JSONException {
        
        if(first.length() == second.length()){
            for(int i=0;i<second.length(); i++){
                if( first.opt(i) instanceof JSONArray){
                    if(!compare((JSONArray) first.opt(i), (JSONArray) second.opt(i)))
                        return false;
                }
                else if(!first.opt(i).equals(second.opt(i)))
                    return false;
            }
            return true;
        }
        
        return false;
    }

    
    private int indexOf(JSONArray ja, Object o){
        
        for( int i=0; i < diffCyclic.length(); i++ ){
                if((ja.opt(i)).equals(o))
                    return i;
        }
        
        return -1;
    }
    
    
    private Object getUpdatedValue(JSONObject oldjo, JSONObject newjo, String jokey) throws JSONException{
        
        JSONObject jotemp = new JSONObject();
        
        jotemp.put("old", oldjo.get(jokey));
        jotemp.put("new", newjo.get(jokey));
        
        return jotemp;
        
    }
    
    private int getChanges() {
        return changes;
        
    }
    
    /**
     * TODO move to test case
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        try {
            
//            Test 1
            String json1 = new String();
            String json2 = new String();
            json1 = "{\"foo\": 42, \"bar\": 41, \"same\": 1, \"ary\": [1, 2, 3, 4], \"sameSub\": {\"value\": \"hello\", \"sameSubSub\": {\"inner\": \"outer\", \"diff\": 44}, \"snafu\": false}, \"recursiveDiffSub\": {\"type\": \"cone\"}, \"selfPtr\": \"json1\"}";
            JSONObject first = new JSONObject(json1);
            json2 = "{\"foo\": 43, \"worked\": true, \"same\": 1, \"ary\": [\"a\", \"b\", \"c\"], \"sameSub\": {\"snafu\": true, \"value\": \"hello\", \"sameSubSub\": {\"inner\": \"outer\", \"diff\": 45}, \"brand\": \"new\"}, \"recursiveDiffSub\": {\"type\": \"ball\", \"ptrJson2\": \"json2\"}}";
            JSONObject second = new JSONObject(json2);
            JSONDiff diff = new JSONDiff(first,second);
            
            System.out.println("\bfirst JSONObject");
            System.out.println(first.toString(3));
            System.out.println();
            
            System.out.println("\bsecond JSONObject");
            System.out.println(second.toString(3));
            System.out.println();
            
            JSONObject testJO = diff.getDifferences();
            Writer wr;
            wr = new StringWriter();
            testJO.write(wr);
            System.out.println("\bDifferences: " + diff.getChanges());

            print(wr);
            System.out.println();
            
            testJO = diff.getUpdated();
            wr = new StringWriter();
            testJO.write(wr);
            System.out.println("\bUpdates: " + diff.getChanges());
            print(wr);
            System.out.println();
            
            testJO = diff.getUpdatedExt();
            wr = new StringWriter();
            testJO.write(wr);
            System.out.println("\bExtended Updates: " + diff.getChanges());
            print(wr);
            System.out.println();
            
            testJO = diff.getInserted();
            wr = new StringWriter();
            testJO.write(wr);
            System.out.println("\bInserts: " + diff.getChanges());
            print(wr);
            System.out.println();
            
            testJO = diff.getDeleted();
            wr = new StringWriter();
            testJO.write(wr);
            System.out.println("\bDeletes: " + diff.getChanges());
            print(wr);
            System.out.println();

                    

//            Test 2
            
//            JSONObject first2 = new JSONObject("{                \"firstName\": \"Jesus\",                 \"lastName\": \"Ortiz Perez\",                 \"address\": {                     \"streetAddress\": \"Aalesunder 7\",                     \"city\": \"Berlin\",                     \"state\": \"Berlin\",                     \"postalCode\": 14000                 },                 \"phoneNumbers\": {\"home\": NULL,\"mobile\": [ \"No German number yet\", \"00346522228281\" ]}            }");   
//            JSONObject second2 = new JSONObject("{        \"completeName\": [\"Jesus\", \"Ortiz\", \"Perez\" ],        \"name\": \"Jesus\",                 \"lastName\": \"Ortiz\",        \"secondlastName\": \"Perez\",         \"address\": {                     \"streetAddress\": \"Aalesunder 7\",                     \"city\": \"Berlin\",                     \"state\": \"Berlin\",                     \"postalCode\": 14000                 },                 \"phoneNumbers\": {\"home\": \"I dont know!\",\"mobile\": [ \"00346522228281\" ]}            }");   
//            
//            JSONDiff diff2 = new JSONDiff(first2,second2);
//            JSONObject testJO2 = diff2.getDifferences();
//            Writer wr2;
//            wr2 = new StringWriter();
//            testJO2.write(wr2);
//            System.out.println("\btest 2: Differences");
//            print(wr2);
//            System.out.println();
            
            
//            Test 3
            
//            String json1 = new String();
//            String json2 = new String();
//            json1 = "{\"foo\": 42, \"bar\": 41, \"same\": 1, \"ary\": [1, 2, 3, 4], \"sameSub\": {\"value\": \"hello\", \"sameSubSub\": {\"inner\": \"outer\", \"diff\": 44}, \"snafu\": false}, \"recursiveDiffSub\": {\"type\": \"cone\"}, \"selfPtr\": \"json1\"}";
//            JSONObject first = new JSONObject(json1);
//            json2 = "{\"foo\": 43, \"worked\": true, \"same\": 1, \"ary\": [\"a\", \"b\", \"c\"], \"sameSub\": {\"snafu\": true, \"value\": \"hello\", \"sameSubSub\": {\"inner\": \"outer\", \"diff\": 45}, \"brand\": \"new\"}, \"recursiveDiffSub\": {\"type\": \"ball\", \"ptrJson2\": \"json2\"}}";
//            JSONObject second = new JSONObject(json2);
//            JSONDiff diff = new JSONDiff(first,second);
//            
//            System.out.println("\bfirst JSONObject");
//            System.out.println(first.toString(3));
//            System.out.println();
//            
//            System.out.println("\bsecond JSONObject");
//            System.out.println(second.toString(3));
//            System.out.println();
//            
//            JSONObject testJO = diff.getDifferences();
//            Writer wr;
//            
//            testJO = diff.getUpdated();
//            wr = new StringWriter();
//            testJO.write(wr);
//            System.out.println("\bUpdates");
//            print(wr);
//            System.out.println();
//            
//            testJO = diff.getUpdatedExt();
//            wr = new StringWriter();
//            testJO.write(wr);
//            System.out.println("\bExtended Updates");
//            print(wr);
//            System.out.println();
            
//            Test 4
            
//            JSONObject first2 = new JSONObject("{                \"firstName\": \"Jesus\",                 \"lastName\": \"Ortiz Perez\",                 \"address\": {                     \"streetAddress\": \"Aalesunder 7\",                     \"city\": \"Berlin\",                     \"state\": \"Berlin\",                     \"postalCode\": 14000                 },                 \"phoneNumbers\": {\"home\": NULL,\"mobile\": [ \"No German number yet\", \"00346522228281\" ]}            }");   
//            JSONObject second2 = new JSONObject("{        \"completeName\": [\"Jesus\", \"Ortiz\", \"Perez\" ],        \"name\": \"Jesus\",                 \"lastName\": \"Ortiz\",        \"secondlastName\": \"Perez\",         \"address\": {                     \"streetAddress\": \"Aalesunder 7\",                     \"city\": \"Berlin\",                     \"state\": \"Berlin\",                     \"postalCode\": 14000                 },                 \"phoneNumbers\": {\"home\": \"I dont know!\",\"mobile\": [ \"00346522228281\" ]}            }");   
//            
//            JSONDiff diff2 = new JSONDiff(first2,second2);
//            JSONObject testJO21 = diff2.getUpdated();
//            Writer wr21;
//            wr21 = new StringWriter();
//            testJO21.write(wr21);
//            System.out.println("\btest 4: Updates " + diff2.getChanges());
//            print(wr21);
//            System.out.println();
//            
//            
//            JSONObject testJO22 = diff2.getUpdatedExt();
//            Writer wr22;
//            wr22 = new StringWriter();
//            testJO22.write(wr22);
//            System.out.println("\btest 4: Extended Updates " + diff2.getChanges());
//            print(wr22);
//            System.out.println();
            
            
//            test 5
            
//            JSONObject first2 = new JSONObject("{\"firstName\": \"Jesus\",  \"lastName\": \"Ortiz Perez\"}");   
//            JSONObject second2 = new JSONObject("{\"firstName\": \"Jesus\",  \"lastName\": \"Ortiz Perez\"}");   
//            JSONDiff diff2 = new JSONDiff(first2,second2);
//            
//            JSONObject testJO23 = diff2.getDifferences();
//            Writer wr23;
//            wr23 = new StringWriter();
//            testJO23.write(wr23);
//            System.out.println("\n\n\btest 4: Differences " + diff2.getChanges());
//            print(wr23);
//            System.out.println();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void print(Writer w) throws Exception {
        System.out.println(new JSONObject(w.toString()).toString(5));
    }
    
}
