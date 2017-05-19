package com.example.abcd.vqa;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by abcd on 2017/4/21.
 */

public class SerializableBoolMap implements Serializable {
    private HashMap<Integer,Boolean> map;

    public Map<Integer, Boolean> getMap() {
        return map;
    }

    public void setMap(HashMap<Integer, Boolean> map) {
        this.map = map;
    }
}
