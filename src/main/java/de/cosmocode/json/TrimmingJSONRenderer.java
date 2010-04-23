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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cosmocode.commons.TrimMode;

/**
 * Allows to hock into the rendering algorithm
 * and trim values before inserting them.
 * 
 * <p>
 *   It's possible to change the trim behaviour
 *   by providing a specific {@link TrimMode}.
 * <p>
 *
 * @author Willi Schoenborn
 */
public class TrimmingJSONRenderer extends ForwardingJSONRenderer {

    private static final Logger log = LoggerFactory.getLogger(TrimmingJSONRenderer.class);
    
    private final JSONRenderer renderer;
    private final TrimMode trimMode;
    
    public TrimmingJSONRenderer(JSONRenderer renderer, TrimMode trimMode) {
        if (renderer == null) throw new NullPointerException("JSONRenderer must not be null");
        if (trimMode == null) throw new NullPointerException("TrimMode must not be null");
        this.renderer = renderer;
        this.trimMode = trimMode;
    }
    
    @Override
    protected JSONRenderer delegate() {
        return renderer;
    }
    
    @Override
    public JSONRenderer value(CharSequence value) {
        final CharSequence trimmed = trimMode.trim(value);
        log.debug("Trimmed {} to {}", value, trimmed);
        return super.value(trimmed);
    }
    
}
