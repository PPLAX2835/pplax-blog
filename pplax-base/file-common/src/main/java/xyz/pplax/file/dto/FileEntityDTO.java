package xyz.pplax.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件实体
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FileEntityDTO {
    /**
     * 此文件的最终存储路径
     */
    private String storagePath;

    /** 文件的名称，包含扩展名 **/
    private String name;

    /** 文件的大小 byte **/
    private long size;

    /** 文件的创建时间 **/
    private String createTime;

    /** 返回文件的url地址 **/
    private String remoteUrl;

    /** 文件路径部分的uri **/
    private String filePathUri;

    /** 文件输入流 **/
    private InputStream inputStream;

    /** 文件输入流 **/
    private OutputStream outputStream;

    /** 文件的所有者 **/
    private String owner;

    /** 文件上传时的百分比，只返回小数，不带"%" **/
    private float uploadPercent;

    /** 文件下载时的百分比 **/
    private float downloadPercent;

    public FileEntityDTO(String name, InputStream inputStream) {
        this.name = name;
        this.inputStream = inputStream;
    }

    public FileEntityDTO(String storagePath, String name, long size) {
        this.storagePath = storagePath;
        this.name = name;
        this.size = size;
    }

    public FileEntityDTO(String storagePath, String name, long size, String remoteUrl) {
        this.storagePath = storagePath;
        this.name = name;
        this.size = size;
        this.remoteUrl = remoteUrl;
    }
}
