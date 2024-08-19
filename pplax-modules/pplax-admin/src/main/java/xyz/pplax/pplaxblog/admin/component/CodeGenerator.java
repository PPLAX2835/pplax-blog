package xyz.pplax.pplaxblog.admin.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import xyz.pplax.pplaxblog.admin.model.CodeGenerateParam;

import java.util.List;
import java.util.Map;

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
        System.out.println(table);
        System.out.println(columns);

        Context context = new Context();
        context.setVariable("className", "className");
        context.setVariable("fieldType", "fieldType");
        context.setVariable("fieldName", "fieldName");

        String process1 = templateEngine.process("codegen/controller.java", context);

        System.out.println(process1);
    }
}
