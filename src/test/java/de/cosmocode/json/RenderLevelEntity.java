package de.cosmocode.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RenderLevelEntity implements JSONMapable {

    private static final Logger LOG = LoggerFactory.getLogger(RenderLevelEntity.class);

    @Override
    public JSONRenderer renderAsMap(JSONRenderer renderer) {
        switch (renderer.currentLevel()) {
            case COMPLETE: {
                
            }
            case LONG: {
                
            }
            case MEDIUM: {
                
            }
            case SHORT: {
                
            }
            case TINY: {
                
            }
            default: {
                return renderer;
            }
        }
    }

}
