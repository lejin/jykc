package org.jesusyouth.jykc.jykcadmin.common;

public class GroupFee {

    public static Integer calculateFee(String category) {
        switch (category) {
            case "family":
                return 1500;
            case "student":
                return 800;
            case "others":
                return 1000;
        }
        return 0;
    }

}
