package de.cosmocode.json;

public interface JSONMapable extends JSONRenderable {

    JSONRenderer renderAsMap(JSONRenderer renderer);
    
}
