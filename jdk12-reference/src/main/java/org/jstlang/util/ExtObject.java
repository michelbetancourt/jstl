package org.jstlang.util;

import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;

import lombok.Data;

@Data
public class ExtObject {

    @JsonIgnore
    private final Map<String, Object> data = Maps.newLinkedHashMap();

    @JsonAnyGetter
    public Map<String, Object> getData() {
        return Collections.unmodifiableMap(data);
    }

    @JsonAnySetter
    public void setData(String key, Object value) {
        this.data.put(key, value);
    }
    
    public Object removeData(String key) {
        return this.data.remove(key);
    }
    
    public int size() {
        return getData().size();
    }
    
    public boolean many() {
        return size() > 1;
    }
    
    public boolean any() {
        return size() > 0;
    }
}
