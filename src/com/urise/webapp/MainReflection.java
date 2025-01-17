package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        // TODO : invoke r.toString via reflection
        System.out.println(r);
        Class clazz = r.getClass();
        System.out.println(clazz.getMethod("toString").invoke(r));

//        Method[] methods = clazz.getMethods();
//        Method toStringReflect = null;
//        for (Method method : methods) {
//            if (method.getName().startsWith("toString")) {
//                toStringReflect = method;
//                break;
//            }
//        }
//        System.out.println(toStringReflect.invoke(r));
    }
}