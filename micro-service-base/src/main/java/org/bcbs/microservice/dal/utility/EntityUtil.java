package org.bcbs.microservice.dal.utility;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public abstract class EntityUtil extends BeanUtils {

    public static void copyNonNullProperties(Object source, Object target) {
        copyProperties(source, target, getNullPropertyNames(source));
    }

    private static String[] getNullPropertyNames(Object obj) {
        final BeanWrapper wrapper = new BeanWrapperImpl(obj);
        PropertyDescriptor[] pds = wrapper.getPropertyDescriptors();

        Set<String> result = new HashSet<>();
        for (PropertyDescriptor pd : pds)
            if (wrapper.getPropertyValue(pd.getName()) == null)
                result.add(pd.getName());

        return result.toArray(new String[0]);
    }
}
