package org.jstlang.util;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;

public class ExtObject {

    @JsonIgnore
    private Map<String, Object> data;

    @JsonAnyGetter
    public Map<String, Object> getData() {
        return Optional.ofNullable(data)
                       .map(Collections::unmodifiableMap)
                       .orElse(Collections.emptyMap());
    }

    @JsonAnySetter
    public void setData(String name, Object value) {
        if (null == data) {
            data = Maps.newLinkedHashMap();
        }
        this.data.put(name, value);
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
