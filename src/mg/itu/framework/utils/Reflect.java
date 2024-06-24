package mg.itu.framework.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflect {
    @SuppressWarnings("rawtypes")
    public static Object execMeth(Object obj,String name, Class[] paramTypes, Object[] args) throws IllegalAccessException ,IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
        Method meth=obj.getClass().getMethod(name, paramTypes);

        return meth.invoke(obj,args);
    }

    static String getMethodName(String attributeName)
    {
        String get="get";
        String firstLetter=attributeName.substring(0, 1).toUpperCase();
        String rest=attributeName.substring(1);
        String res=firstLetter.concat(rest);    
        String methodName=get.concat(res);
        return methodName;
    }

    static String setMethodName(String attributeName)
    {
        String set="set";
        String firstLetter=attributeName.substring(0, 1).toUpperCase();
        String rest=attributeName.substring(1);
        String res=firstLetter.concat(rest);    
        String methodName=set.concat(res);
        return methodName;
    }

    public static Object get(Object obj, String attributeName) throws Exception{
        String methName=getMethodName(attributeName);

        Method meth=obj.getClass().getDeclaredMethod(methName, (Class<?>[])null);
        return meth.invoke(obj, (Object[])null);
    }

    public static void set(Object obj, String attributeName, Object value, Class<?> type) throws Exception{
        String methName=setMethodName(attributeName);

        Method meth=obj.getClass().getDeclaredMethod(methName,type);
        meth.invoke(obj,value);
    }
}
