package xyz.pplax.pplaxblog.commons.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 可以使
 */
public class JavaMySQLTypeConverter {

    private static final Map<String, String> mysqlToJavaMapping = new HashMap<>();
    private static final Map<String, String> javaToMysqlMapping = new HashMap<>();

    static {
        // MySQL to Java mappings
        mysqlToJavaMapping.put("tinyint", "Byte");
        mysqlToJavaMapping.put("smallint", "Short");
        mysqlToJavaMapping.put("mediumint", "Integer");
        mysqlToJavaMapping.put("int", "Integer");
        mysqlToJavaMapping.put("integer", "Integer");
        mysqlToJavaMapping.put("bigint", "Long");

        mysqlToJavaMapping.put("float", "Float");
        mysqlToJavaMapping.put("double", "Double");
        mysqlToJavaMapping.put("decimal", "BigDecimal");
        mysqlToJavaMapping.put("numeric", "BigDecimal");

        mysqlToJavaMapping.put("char", "String");
        mysqlToJavaMapping.put("varchar", "String");
        mysqlToJavaMapping.put("text", "String");
        mysqlToJavaMapping.put("tinytext", "String");
        mysqlToJavaMapping.put("mediumtext", "String");
        mysqlToJavaMapping.put("longtext", "String");

        mysqlToJavaMapping.put("date", "Date");
        mysqlToJavaMapping.put("datetime", "Timestamp");
        mysqlToJavaMapping.put("timestamp", "Timestamp");
        mysqlToJavaMapping.put("time", "Time");
        mysqlToJavaMapping.put("year", "Integer");

        mysqlToJavaMapping.put("binary", "Byte[]");
        mysqlToJavaMapping.put("varbinary", "Byte[]");
        mysqlToJavaMapping.put("blob", "Byte[]");
        mysqlToJavaMapping.put("tinyblob", "Byte[]");
        mysqlToJavaMapping.put("mediumblob", "Byte[]");
        mysqlToJavaMapping.put("longblob", "Byte[]");

        mysqlToJavaMapping.put("bit", "Boolean");

        mysqlToJavaMapping.put("enum", "String");
        mysqlToJavaMapping.put("set", "String");

        // Java to MySQL mappings
        javaToMysqlMapping.put("Byte", "tinyint");
        javaToMysqlMapping.put("Short", "smallint");
        javaToMysqlMapping.put("Integer", "int");
        javaToMysqlMapping.put("Long", "bigint");

        javaToMysqlMapping.put("Float", "float");
        javaToMysqlMapping.put("Double", "double");
        javaToMysqlMapping.put("BigDecimal", "decimal");

        javaToMysqlMapping.put("String", "varchar(255)"); // Default size for String
        javaToMysqlMapping.put("Date", "date");
        javaToMysqlMapping.put("Timestamp", "datetime");
        javaToMysqlMapping.put("Time", "time");

        javaToMysqlMapping.put("Byte[]", "blob"); // Default for binary data
        javaToMysqlMapping.put("Boolean", "bit");

        // Note: enum and set do not have direct Java equivalents in all cases
    }

    public static String mysqlToJava(String mysqlType) {
        if (mysqlType == null || mysqlType.isEmpty()) {
            throw new IllegalArgumentException("MySQL type cannot be null or empty");
        }

        mysqlType = mysqlType.toLowerCase();

        // Handling potential type lengths (e.g., varchar(255))
        if (mysqlType.contains("(")) {
            mysqlType = mysqlType.substring(0, mysqlType.indexOf("("));
        }

        return mysqlToJavaMapping.getOrDefault(mysqlType, "Object");
    }

    public static String javaToMysql(String javaType) {
        if (javaType == null || javaType.isEmpty()) {
            throw new IllegalArgumentException("Java type cannot be null or empty");
        }

        return javaToMysqlMapping.getOrDefault(javaType, "text"); // Default for unknown types
    }

}
