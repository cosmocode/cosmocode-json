package de.cosmocode.json;

import org.json.JSONException;
import org.json.extension.JSONConstructor;

public interface JSONAdapter extends JSONRenderer, JSONConstructor {

    @Override
    JSONAdapter array();

    @Override
    JSONAdapter endArray();

    @Override
    JSONAdapter object();
    
    @Override
    JSONAdapter endObject();

    @Override
    JSONAdapter key(String key) throws JSONException;

    @Override
    JSONAdapter value(Object value);

    @Override
    JSONAdapter value(boolean value);
    
    @Override
    JSONAdapter value(long value);
    
    @Override
    JSONAdapter value(double value);
    
    @Override
    JSONAdapter plain(String json);
    
}
