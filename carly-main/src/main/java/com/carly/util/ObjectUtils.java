package com.carly.util;

import java.lang.reflect.Method;

public class ObjectUtils {

    private ObjectUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Merges 2 Java objects. This works as long as you have POJOs with their own getters and setters.
     * The objects should be of the same class.
     * The method updates obj with non-null values from update.
     * It calls setParameter() on obj with the return value of getParameter() on update.
     * If a field is present in both objects (obj and update), then update's field overrides obj' s field.
     *
     * @param obj    Object
     * @param update Object
     */
    public static void merge(Object obj, Object update) {
        if (!obj.getClass().isAssignableFrom(update.getClass())) {
            return;
        }

        Method[] methods = obj.getClass().getMethods();

        for (Method fromMethod : methods) {
            if (fromMethod.getDeclaringClass().equals(obj.getClass())
                    && fromMethod.getName().startsWith("get")) {

                String fromName = fromMethod.getName();
                String toName = fromName.replace("get", "set");

                try {
                    Method method = obj.getClass().getMethod(toName, fromMethod.getReturnType());
                    Object value = fromMethod.invoke(update, (Object[]) null);
                    if (value != null) {
                        method.invoke(obj, value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
