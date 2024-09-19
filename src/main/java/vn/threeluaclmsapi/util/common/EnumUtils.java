package vn.threeluaclmsapi.util.common;

import vn.threeluaclmsapi.util.enums.Gender;

public class EnumUtils {

    public static Gender parseGender(String genderStr) {
        try {
            return Gender.valueOf(genderStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid gender value: " + genderStr);
        }
    }

}
