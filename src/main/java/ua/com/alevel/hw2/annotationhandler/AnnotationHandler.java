package ua.com.alevel.hw2.annotationhandler;


import org.reflections.Reflections;
import ua.com.alevel.hw2.annotation.Autowired;
import ua.com.alevel.hw2.annotation.Singleton;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public final class AnnotationHandler {

    private static Reflections reflections;
    private static Map<Class<?>, Object> caches;

    private AnnotationHandler() {
    }

    public static void createAndCasheWithAnnotation() {
        reflections = new Reflections("ua.com.alevel.hw2");
        caches = new HashMap<>();
        Set<Class<?>> singletonClasses = reflections.getTypesAnnotatedWith(Singleton.class);
        casheClassesWithSingletonAnnotation(singletonClasses);
    }

    private static void casheClassesWithSingletonAnnotation(Set<Class<?>> singletonsClasses) {
        singletonsClasses.forEach(aClass -> {
            Arrays.stream(aClass.getDeclaredConstructors()).forEach(constructor -> {
                try {
                    if (constructor.getParameterCount() == 0 && constructor.isAnnotationPresent(Autowired.class)) {
                        constructor.setAccessible(true);
                        Object object = constructor.newInstance();
                        Field field = aClass.getDeclaredField("instance");
                        field.setAccessible(true);
                        field.set(object, object);
                        caches.put(aClass, object);
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            });
        });

        singletonsClasses.forEach(aClass -> {
            Arrays.stream(aClass.getDeclaredConstructors()).forEach(constructor -> {
                try {
                    if (constructor.getParameterCount() == 1 && constructor.isAnnotationPresent(Autowired.class)) {
                        constructor.setAccessible(true);
                        Class repository = constructor.getParameterTypes()[0];
                        Object object = constructor.newInstance(caches.get(repository));
                        Field field = aClass.getDeclaredField("instance");
                        field.setAccessible(true);
                        field.set(object, object);
                        caches.put(aClass, object);
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
