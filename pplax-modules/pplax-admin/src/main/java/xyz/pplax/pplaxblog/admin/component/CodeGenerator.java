package xyz.pplax.pplaxblog.admin.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import xyz.pplax.pplaxblog.admin.model.CodeGenerateParam;
import xyz.pplax.pplaxblog.commons.utils.JavaMySQLTypeConverter;
import xyz.pplax.pplaxblog.commons.utils.NamingUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Component
public class CodeGenerator {

    @Autowired
    private CodeGenerateParam codeGenerateParam;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    // map中会用到的key
    private final String CREATE_TIME = "createTime";        // 表相关
    private final String ENGINE = "ENGINE";
    private final String TABLE_COMMENT = "tableComment";
    private final String TABLE_NAME = "tableName";
    private final String DATA_TYPE = "dataType";            // 列相关
    private final String COLUM_COMMENT = "columnComment";
    private final String COLUM_KEY = "columnKey";
    private final String COLUM_NAME = "columnName";

    public ByteArrayOutputStream generate(Map<String, Object> table, List<Map<String, Object>> columns, String replacePrefix) throws IOException {
        // 获得代码字符串
        String entityStr = entityGenerate(table, columns, replacePrefix);
        String mapperStr = mapperGenerate(table, replacePrefix);
        String mapperXmlStr = mapperXmlGenerate(table, columns, replacePrefix);
        String serviceStr = serviceGenerate(table, replacePrefix);
        String serviceImplStr = serviceImplGenerate(table, replacePrefix);
        String sqlConstantsStr = sqlConstantsGenerate(table, columns, replacePrefix);

        // 获得className
        String tableName = ((String) table.get(TABLE_NAME)).replaceFirst(replacePrefix, "");
        String className = NamingUtils.getClassName(NamingUtils.snakeToCamel(tableName));

        // 文件目录
        String baseDir = "src/main/java/xyz/pplax/pplaxblog/xo/";

        String entityDir = baseDir + "entity/";
        String mapperDir = baseDir + "mapper/";
        String mapperXmlDir = "src/main/resources/mapper/";
        String serviceDir = baseDir + "service/";
        String serviceImplDir = baseDir + "service/impl/";
        String sqlConstantsDir = baseDir + "constants/sql/";

        // 创建内存中的字节数组输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // 使用 ZipOutputStream 包装字节数组输出流
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        // 创建目录 ZipEntry
        ZipEntry dirEntry = new ZipEntry(entityDir);              // entity
        zipOutputStream.putNextEntry(dirEntry);
        zipOutputStream.closeEntry();
        dirEntry = new ZipEntry(mapperDir);              // mapper
        zipOutputStream.putNextEntry(dirEntry);
        zipOutputStream.closeEntry();
        dirEntry = new ZipEntry(mapperXmlDir);        // mapperXml
        zipOutputStream.putNextEntry(dirEntry);
        zipOutputStream.closeEntry();
        dirEntry = new ZipEntry(serviceDir);            // service
        zipOutputStream.putNextEntry(dirEntry);
        zipOutputStream.closeEntry();
        dirEntry = new ZipEntry(serviceImplDir);    // serviceImpl
        zipOutputStream.putNextEntry(dirEntry);
        zipOutputStream.closeEntry();
        dirEntry = new ZipEntry(sqlConstantsDir);  // sqlConstants
        zipOutputStream.putNextEntry(dirEntry);
        zipOutputStream.closeEntry();

        // 创建文件的 ZipEntry（包含目录路径）,并将字符串内容写入到文件 ZipEntry 中
        ZipEntry fileEntry = new ZipEntry(entityDir + className + ".java");
        zipOutputStream.putNextEntry(fileEntry);
        zipOutputStream.write(entityStr.getBytes());

        fileEntry = new ZipEntry(mapperDir + className + "Mapper.java");
        zipOutputStream.putNextEntry(fileEntry);
        zipOutputStream.write(mapperStr.getBytes());

        fileEntry = new ZipEntry(mapperXmlDir + className + "Mapper.xml");
        zipOutputStream.putNextEntry(fileEntry);
        zipOutputStream.write(mapperXmlStr.getBytes());

        fileEntry = new ZipEntry(serviceDir + className + "Service.java");
        zipOutputStream.putNextEntry(fileEntry);
        zipOutputStream.write(serviceStr.getBytes());

        fileEntry = new ZipEntry(serviceImplDir + className + "ServiceImpl.java");
        zipOutputStream.putNextEntry(fileEntry);
        zipOutputStream.write(serviceImplStr.getBytes());

        fileEntry = new ZipEntry(sqlConstantsDir + className + "SQLConstants.java");
        zipOutputStream.putNextEntry(fileEntry);
        zipOutputStream.write(sqlConstantsStr.getBytes());

        zipOutputStream.closeEntry();

        return byteArrayOutputStream;
    }

    /**
     * 生成entity代码字符串
     * @param table
     * @param columns
     * @param replacePrefix
     * @return
     */
    public String entityGenerate(Map<String, Object> table, List<Map<String, Object>> columns, String replacePrefix) {
        // 检查前缀
        if (replacePrefix == null) {
            replacePrefix = "";
        }

        // 获取数据
        String tableComment = (String) table.get(TABLE_COMMENT);
        String author = codeGenerateParam.getAuthor();
        String date = (new Date()).toString();
        String tableName = ((String) table.get(TABLE_NAME)).replaceFirst(replacePrefix, "");
        String className = NamingUtils.getClassName(NamingUtils.snakeToCamel(tableName));
        List<Map<String, String>> attributes = new ArrayList<>();

        // 循环封装attributes
        for (Map<String, Object> column : columns) {
            Map<String, String> attribute = new HashMap<>();

            String attributeName = NamingUtils.snakeToCamel((String) column.get(COLUM_NAME));
            if (
                "uid".equals(attributeName) ||
                "status".equals(attributeName) ||
                "createTime".equals(attributeName) ||
                "updateTime".equals(attributeName)
            ) {
                continue;
            }

            String attributeComment = (String) column.get(COLUM_COMMENT);
            String attributeType = JavaMySQLTypeConverter.mysqlToJava((String) column.get(DATA_TYPE));

            attribute.put("attributeComment", attributeComment);
            attribute.put("attributeType", attributeType);
            attribute.put("attributeName", attributeName);

            attributes.add(attribute);
        }

        // 填充模板
        Map<String, Object> context = new HashMap<>();
        context.put("tableComment", tableComment);
        context.put("author", author);
        context.put("date", date);
        context.put("tableName", tableName);
        context.put("className", className);
        context.put("attributes", attributes);

        return processTemplate("entity.java.ftl", context);
    }

    /**
     * 生成mapper代码字符串
     * @param table
     * @param replacePrefix
     * @return
     */
    public String mapperGenerate(Map<String, Object> table, String replacePrefix) {
        if (replacePrefix == null) {
            replacePrefix = "";
        }

        String author = codeGenerateParam.getAuthor();
        String date = (new Date()).toString();
        String tableName = ((String) table.get(TABLE_NAME)).replaceFirst(replacePrefix, "");
        String className = NamingUtils.getClassName(NamingUtils.snakeToCamel(tableName));

        Map<String, Object> context = new HashMap<>();
        context.put("author", author);
        context.put("date", date);
        context.put("tableName", tableName);
        context.put("className", className);

        return processTemplate("mapper.java.ftl", context);
    }

    /**
     * 生成mapper XML代码字符串
     * @param table
     * @param columns
     * @param replacePrefix
     * @return
     */
    public String mapperXmlGenerate(Map<String, Object> table, List<Map<String, Object>> columns, String replacePrefix) {
        if (replacePrefix == null) {
            replacePrefix = "";
        }

        String tableName = ((String) table.get(TABLE_NAME)).replaceFirst(replacePrefix, "");
        String className = NamingUtils.getClassName(NamingUtils.snakeToCamel(tableName));
        List<Map<String, String>> columnConverters = new ArrayList<>();
        StringBuilder columnsStr = new StringBuilder();

        boolean flag = false;
        for (Map<String, Object> column : columns) {
            String columName = (String) column.get(COLUM_NAME);
            if (!flag) {
                columnsStr.append("`").append(columName).append("`");
                flag = true;
            } else {
                columnsStr.append(", ").append("`").append(columName).append("`");
            }

            Map<String, String> columnConverter = new HashMap<>();
            if (!"uid".equals(columName)) {
                columnConverter.put("column", columName);
                columnConverter.put("property", NamingUtils.snakeToCamel(columName));
                columnConverters.add(columnConverter);
            }
        }

        Map<String, Object> context = new HashMap<>();
        context.put("tableName", tableName);
        context.put("className", className);
        context.put("columnsStr", columnsStr.toString());
        context.put("columnConverters", columnConverters);

        return processTemplate("mapper.xml.ftl", context);
    }

    /**
     * 生成service 接口代码字符串
     * @param table
     * @param replacePrefix
     * @return
     */
    public String serviceGenerate(Map<String, Object> table, String replacePrefix) {
        if (replacePrefix == null) {
            replacePrefix = "";
        }

        String author = codeGenerateParam.getAuthor();
        String date = (new Date()).toString();
        String tableName = ((String) table.get(TABLE_NAME)).replaceFirst(replacePrefix, "");
        String className = NamingUtils.getClassName(NamingUtils.snakeToCamel(tableName));

        Map<String, Object> context = new HashMap<>();
        context.put("author", author);
        context.put("date", date);
        context.put("tableName", tableName);
        context.put("className", className);

        return processTemplate("service.java.ftl", context);
    }

    /**
     * 生成serviceImpl 代码字符串
     * @param table
     * @param replacePrefix
     * @return
     */
    public String serviceImplGenerate(Map<String, Object> table, String replacePrefix) {
        if (replacePrefix == null) {
            replacePrefix = "";
        }

        String author = codeGenerateParam.getAuthor();
        String date = (new Date()).toString();
        String tableName = ((String) table.get(TABLE_NAME)).replaceFirst(replacePrefix, "");
        String className = NamingUtils.getClassName(NamingUtils.snakeToCamel(tableName));

        Map<String, Object> context = new HashMap<>();
        context.put("author", author);
        context.put("date", date);
        context.put("tableName", tableName);
        context.put("className", className);

        return processTemplate("serviceImpl.java.ftl", context);
    }


    /**
     * 生成SQLConstants常量 代码字符串
     * @param table
     * @param replacePrefix
     * @return
     */
    public String sqlConstantsGenerate(Map<String, Object> table, List<Map<String, Object>> columns, String replacePrefix) {
        if (replacePrefix == null) {
            replacePrefix = "";
        }

        // 获取数据
        String tableName = ((String) table.get(TABLE_NAME)).replaceFirst(replacePrefix, "");
        String author = codeGenerateParam.getAuthor();
        String date = (new Date()).toString();
        String className = NamingUtils.getClassName(NamingUtils.snakeToCamel(tableName));
        List<Map<String, String>> attributes = new ArrayList<>();

        // 循环封装attributes
        for (Map<String, Object> column : columns) {
            Map<String, String> attribute = new HashMap<>();

            String attributeComment = (String) column.get(COLUM_COMMENT);
            String columnName = (String) column.get(COLUM_NAME);
            String columnVariableName = columnName.toUpperCase();

            attribute.put("attributeComment", attributeComment);
            attribute.put("columnName", columnName);
            attribute.put("columnVariableName", columnVariableName);

            attributes.add(attribute);
        }

        // 填充模板
        Map<String, Object> context = new HashMap<>();
        context.put("author", author);
        context.put("date", date);
        context.put("tableName", tableName);
        context.put("className", className);
        context.put("attributes", attributes);

        return processTemplate("SQLConstants.java.ftl", context);
    }

    /**
     * 处理模板并返回生成的字符串
     * @param templateName 模板文件名
     * @param context 数据模型
     * @return 填充后的字符串
     */
    private String processTemplate(String templateName, Map<String, Object> context) {
        templateName = String.format(codeGenerateParam.getTemplatePath(), templateName);
        try {
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
            StringWriter stringWriter = new StringWriter();
            template.process(context, stringWriter);
            return stringWriter.toString();
        } catch (IOException | TemplateException e) {
            log.error("Error processing template: {}", templateName, e);
            throw new RuntimeException("Error processing template", e);
        }
    }
}
