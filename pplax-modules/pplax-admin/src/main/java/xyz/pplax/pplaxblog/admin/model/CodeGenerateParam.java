package xyz.pplax.pplaxblog.admin.model;

import lombok.Data;

@Data
public class CodeGenerateParam {

    /**
     * 作者
     */
    private String author;
    /**
     * freemarker模块文件路径
     */
    private String templatePath;
    /**
     * 默认包路径
     */
    private String defaultPackage;

}
