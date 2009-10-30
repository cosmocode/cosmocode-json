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
