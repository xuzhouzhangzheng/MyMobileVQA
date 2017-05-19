package com.example.abcd.vqa;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by abcd on 2017/4/9.
 */

public class SerializableMap implements Serializable {
    private Map<String,String> map;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
