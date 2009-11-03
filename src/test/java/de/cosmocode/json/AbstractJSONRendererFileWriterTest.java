package de.cosmocode.json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import com.google.common.collect.Maps;

public abstract class AbstractJSONRendererFileWriterTest extends AbstractJSONRendererWriterTest {

    private final Map<Writer, File> cache = Maps.newHashMap();
    
    @Override
    protected final Writer createWriter() throws IOException {
        final long time = System.currentTimeMillis();
        final String time2 = Long.toString(time);
        final String name = DigestUtils.md5Hex(time2);
        final File file = File.createTempFile(name, null);
        final Writer writer = new FileWriter(file);
        cache.put(writer, file);
        return writer;
    }
    
    @Override
    protected final String toString(Writer writer) throws IOException {
        final File file = cache.get(writer);
        return FileUtils.readFileToString(file);
    }

}
