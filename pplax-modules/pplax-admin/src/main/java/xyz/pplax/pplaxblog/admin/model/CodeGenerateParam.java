package xyz.pplax.pplaxblog.admin.model;

import lombok.Data;

@Data
public class CodeGenerateParam {

    /**
     * 输入目录
     */
    private String outputDir;
    /**
     * 作者
     */
    private String author;
    /**
     * 包名
     */
    private String packageName;
    /**
     * freemarker模块文件路径
     */
    private String templatePath;

}
