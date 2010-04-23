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
