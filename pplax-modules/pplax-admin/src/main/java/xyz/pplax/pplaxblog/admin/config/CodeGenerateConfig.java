package xyz.pplax.pplaxblog.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.pplax.pplaxblog.admin.model.CodeGenerateParam;

@Configuration
public class CodeGenerateConfig {

    @Bean
    public CodeGenerateParam codeGenerateParam() {
        CodeGenerateParam codeGenerateParam = new CodeGenerateParam();

        codeGenerateParam.setAuthor("PPLAX");
        codeGenerateParam.setTemplatePath("codegen/%s");
        codeGenerateParam.setBasePackage("xyz.pplax.pplaxblog");

        return codeGenerateParam;
    }

}
