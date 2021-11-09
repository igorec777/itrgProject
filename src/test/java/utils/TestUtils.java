package utils;

import java.lang.reflect.Field;

public class TestUtils {
    public static boolean isAllClassFieldsNotNullOfGivenObject(Object obj) throws IllegalAccessException {
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(obj) == null) {
                return false;
            }
        }
        return true;
    }
}
