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

import java.io.StringWriter;
import java.io.Writer;

import org.json.JSONException;
import org.json.JSONWriter;
import org.junit.Assert;
import org.junit.Test;

public class JSONTest {

    @Test
    public void stealNoOperation() {
        final Writer expected = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(expected);
        final Writer actual = JSON.stealWriter(jsonWriter);
        
        Assert.assertSame(expected, actual);
    }

    @Test
    public void stealWrite() throws JSONException {
        final Writer expected = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(expected);
        final Writer actual = JSON.stealWriter(jsonWriter);
        
        jsonWriter.object().key("test").value(new Object()).endObject();
        
        Assert.assertSame(expected, actual);
    }
    
}
