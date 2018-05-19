package org.jesusyouth.jykc.jykcadmin.Constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ZoneNames {

    public static final  Map<Integer,String> ZONE_NAME_MAP=initMap();

    private static Map<Integer,String> initMap(){
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "ny");
        map.put(2, "tv");
        map.put(3, "kl");
        map.put(4, "pr");
        map.put(5, "cy");
        map.put(6, "ky");
        map.put(7, "kj");
        map.put(8, "pl");
        map.put(9, "ap");
        map.put(10, "ct");
        map.put(11, "ek");
        map.put(12, "ik");
        map.put(13, "kp");
        map.put(14, "kg");
        map.put(15, "am");
        map.put(16, "ij");
        map.put(17, "th");
        map.put(18, "pk");
        map.put(19, "mv");
        map.put(20, "ca");
        map.put(21, "tl");
        map.put(22, "kn");
        map.put(23, "kd");
        return Collections.unmodifiableMap(map);
    }

}
