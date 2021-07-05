package com.testvagrant.mpem.mapper;

public class ElementsFileFinder {

    private static final String IOS_ELEMENTS = "com.testvagrant.mpem.elements.ios";
    private static final String MOBILE_WEB_ELEMENTS = "com.testvagrant.mpem.elements.mobileweb";

    public  <T> T getElementLocatorsSwapObj(T obj) {
        // Platform
        String nameOfClass = obj.getClass().getName();
        String elementClass = nameOfClass.replace("com.testvagrant.mpem.pages", IOS_ELEMENTS) + "Elements";
        try {
            return (T) Class.forName(elementClass).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
