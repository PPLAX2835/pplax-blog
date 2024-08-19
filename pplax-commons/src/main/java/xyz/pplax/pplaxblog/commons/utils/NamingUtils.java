package xyz.pplax.pplaxblog.commons.utils;

public class NamingUtils {

    // 驼峰转下划线
    public static String camelToSnake(String camelCase) {
        if (camelCase == null || camelCase.isEmpty()) {
            return camelCase;
        }
        StringBuilder snakeCase = new StringBuilder(camelCase);
        for (int i = 1; i < snakeCase.length(); i++) {
            if (Character.isUpperCase(snakeCase.charAt(i))) {
                snakeCase.insert(i, '_');
                i++;
            }
        }
        return snakeCase.toString().toLowerCase();
    }

    // 下划线转驼峰
    public static String snakeToCamel(String snakeCase) {
        if (snakeCase == null || snakeCase.isEmpty()) {
            return snakeCase;
        }
        StringBuilder camelCase = new StringBuilder(snakeCase);
        int index = camelCase.indexOf("_");
        while (index > -1) {
            if (index + 1 < camelCase.length()) {
                char nextChar = camelCase.charAt(index + 1);
                if (Character.isLetter(nextChar)) {
                    camelCase.setCharAt(index + 1, Character.toUpperCase(nextChar));
                }
                camelCase.delete(index, index + 1);
            }
            index = camelCase.indexOf("_");
        }
        return camelCase.toString();
    }

    public static String getClassName(String camelName) {
        if (camelName == null || camelName.isEmpty()) {
            return camelName;
        }
        return camelName.substring(0,1).toUpperCase()+camelName.substring(1);
    }
}
