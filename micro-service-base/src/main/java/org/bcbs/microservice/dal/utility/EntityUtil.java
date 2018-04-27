package org.bcbs.microservice.dal.utility;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.stream.Stream;

public abstract class EntityUtil extends BeanUtils {

    public static void copyNonNullProperties(Object source, Object target) {
        copyProperties(source, target, getNullPropertyNames(source));
    }

    private static String[] getNullPropertyNames(Object obj) {
        final BeanWrapper wrapper = new BeanWrapperImpl(obj);
        return Stream.of(wrapper.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(name -> wrapper.getPropertyValue(name) == null)
                .toArray(String[]::new);
    }
}
