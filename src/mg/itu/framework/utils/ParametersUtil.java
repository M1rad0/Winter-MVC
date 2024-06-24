package mg.itu.framework.utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParametersUtil {
    /*Fonction permettant de convertir un String en un objet de la classe targetType */
    public static Object castString(String value, Class<?> targetType) {
        if (targetType == Integer.class || targetType == int.class) {
            return Integer.parseInt(value);
        } else if (targetType == Double.class || targetType == double.class) {
            return Double.parseDouble(value);
        } else if (targetType == Float.class || targetType == float.class) {
            return Float.parseFloat(value);
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (targetType == Long.class || targetType == long.class) {
            return Long.parseLong(value);
        } else if (targetType == Short.class || targetType == short.class) {
            return Short.parseShort(value);
        } else if (targetType == Byte.class || targetType == byte.class) {
            return Byte.parseByte(value);
        } else if (targetType == Character.class || targetType == char.class) {
            if (value.length() == 1) {
                return value.charAt(0);
            } else {
                throw new IllegalArgumentException("Cannot cast to char: the string length is not 1.");
            }
        } else if (targetType == String.class) {
            return value;
        } else if (targetType == Date.class) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.parse(value);
            } catch (ParseException e) {
                throw new IllegalArgumentException("Cannot cast to Date: " + e.getMessage(), e);
            }
        } else if (targetType == LocalDateTime.class) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            return LocalDateTime.parse(value, formatter);
        } else {
            throw new IllegalArgumentException("Si vous utlisez une classe personnalisée, veuillez marquer le paramètre avec l'annotation ParamWrapper");
        }
    }
}