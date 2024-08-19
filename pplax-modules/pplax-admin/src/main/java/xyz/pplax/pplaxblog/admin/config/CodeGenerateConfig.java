package xyz.pplax.pplaxblog.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.pplax.pplaxblog.admin.model.CodeGenerateParam;

@Configuration
public class CodeGenerateConfig {

    @Bean
    public CodeGenerateParam codeGenerateParam() {
        CodeGenerateParam codeGenerateParam = new CodeGenerateParam();

        // 这里先写死，跑通了再整理
        codeGenerateParam.setOutputDir("G:\\tmp\\mybatis");
        codeGenerateParam.setAuthor("pplax");
        codeGenerateParam.setPackageName("xyz.pplax.pplaxblog");
        codeGenerateParam.setTemplatePath("codegen/controller");


        return codeGenerateParam;
    }

}
