package mg.itu.framework.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflect {
    @SuppressWarnings("rawtypes")
    public static Object execMeth(Object obj,String name, Class[] paramTypes, Object[] args) throws IllegalAccessException ,IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
        Method meth=obj.getClass().getMethod(name, paramTypes);

        return meth.invoke(obj,args);
    }
}
