package com.testvagrant.mpem.mapper;

import org.openqa.selenium.support.FindBy;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AnnotationMapper {

    private Class annotation;
    public AnnotationMapper(Class annotation) {
       this.annotation = annotation;
    }

    public <T> T map(T obj1, T obj2) {
        Reflections obj1Reflection = new Reflections(obj1.getClass().getName(), new FieldAnnotationsScanner());
        Reflections obj2Reflection= new Reflections(obj2.getClass().getName(), new FieldAnnotationsScanner());
        Set<Field> fieldsAnnotatedWithObj1 = obj1Reflection.getFieldsAnnotatedWith(annotation);
        Set<Field> fieldsAnnotatedWithObj2 = obj2Reflection.getFieldsAnnotatedWith(annotation);
        fieldsAnnotatedWithObj1.stream().forEach(field -> {
            System.out.println(field);
            Annotation existingAnnotation = field.getAnnotation(annotation);
            Field f = getField(field.getName(), fieldsAnnotatedWithObj2);
            Annotation annotationToSwap = f.getAnnotation(annotation);
            changeAnnotationValue(existingAnnotation, annotationToSwap);
        });
        return obj1;
    }

    private Field getField(String key, Set<Field> toLookInto) {
        Optional<Field> first = toLookInto.stream().filter(field -> field.getName().equals(key)).findFirst();
        return first.get();
    }

    @SuppressWarnings("unchecked")
    public void changeAnnotationValue(Annotation annotation, Annotation annotation1){
        Map<String, Object> memberValues = getAnnotationMemberValues(annotation);
        Map<String, Object> memberValuesToBeUpdated = getAnnotationMemberValues(annotation1);
        System.out.println("Before swapping : " + memberValues.get("xpath"));
        memberValues.entrySet().stream().forEach(entry -> {
            memberValues.put(entry.getKey(), memberValuesToBeUpdated.get(entry.getKey()));
        });
        System.out.println("After swapping  : " + memberValues.get("xpath"));
    }

    private Map<String, Object> getAnnotationMemberValues(Annotation annotation) {
        Object handler = Proxy.getInvocationHandler(annotation);
        Field f;
        try {
            f = handler.getClass().getDeclaredField("memberValues");
        } catch (NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(e);
        }
        f.setAccessible(true);
        Map<String, Object> memberValues;
        try {
            memberValues = (Map<String, Object>) f.get(handler);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        return memberValues;
    }
}
