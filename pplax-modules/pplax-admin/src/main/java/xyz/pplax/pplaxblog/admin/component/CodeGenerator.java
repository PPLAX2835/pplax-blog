package xyz.pplax.pplaxblog.admin.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import xyz.pplax.pplaxblog.admin.model.CodeGenerateParam;
import xyz.pplax.pplaxblog.commons.utils.JavaMySQLTypeConverter;
import xyz.pplax.pplaxblog.commons.utils.NamingUtils;

import java.util.*;

@Slf4j
@Component
public class CodeGenerator {

    @Autowired
    private CodeGenerateParam codeGenerateParam;

    @Autowired
    private TemplateEngine templateEngine;

    // map中会用到的key
    private final String CREATE_TIME = "createTime";        // 表相关
    private final String ENGINE = "ENGINE";
    private final String TABLE_COMMENT = "tableComment";
    private final String TABLE_NAME = "tableName";
    private final String DATA_TYPE = "dataType";            // 列相关
    private final String COLUM_COMMENT = "columnComment";
    private final String COLUM_KEY = "columnKey";
    private final String COLUM_NAME = "columnName";

    public void generate(Map<String, Object> table, List<Map<String, Object>> columns) {
        String entityStr = entityGenerate(table, columns, "t_");

        System.out.println(entityStr);
    }

    public String entityGenerate(
            Map<String, Object> table,
            List<Map<String, Object>> columns,
            String replacePrefix
    ) {
        if (replacePrefix == null) {
            replacePrefix = "";
        }

        String tableComment = (String) table.get(TABLE_COMMENT);
        String author = codeGenerateParam.getAuthor();
        String date = (new Date()).toString();
        String tableName = ((String) table.get(TABLE_NAME)).replaceFirst(replacePrefix, "");
        String className = NamingUtils.getClassName(NamingUtils.snakeToCamel(tableName));

        StringBuilder attributes = new StringBuilder();
        for (Map<String, Object> column : columns) {
            String attributeComment = (String) column.get(COLUM_COMMENT);
            String attributeType = JavaMySQLTypeConverter.mysqlToJava((String) column.get(DATA_TYPE));
            String attributeName = NamingUtils.snakeToCamel((String) column.get(COLUM_NAME));

            if (
                "uid".equals(attributeName) ||
                "status".equals(attributeName) ||
                "createTime".equals(attributeName) ||
                "updateTime".equals(attributeName)
            ) {
                continue;
            }

            // <#list>标签不能正常解析，现在使用嵌套填充的方式
            String attribute =
                    "\n" +
                    "    /**\n" +
                    "     * %s\n" +
                    "     */\n" +
                    "    private %s %s;\n";
            attributes.append(String.format(attribute, attributeComment, attributeType, attributeName));
        }

        Context context = new Context();
        context.setVariable("tableComment", tableComment);
        context.setVariable("author", author);
        context.setVariable("date", date);
        context.setVariable("tableName", tableName);
        context.setVariable("className", className);
        context.setVariable("attributes", attributes.toString());

        return templateEngine.process(String.format(codeGenerateParam.getTemplatePath(), "entity.java"), context);
    }
}
