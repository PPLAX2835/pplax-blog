package xyz.pplax.article.service;

import xyz.pplax.article.pojo.ArticlePojo;

import java.io.IOException;

public interface ParseArticleFile {
    // List<CategoryPojo> parseCategory(String frontmatterCategoryName, boolean folderAsCategoryName) throws IOException;
    // List<TagVO> parseTag(String frontmatterTagName) throws IOException;

    ArticlePojo parseArticle(boolean reservedFrontMatter,
                             String frontmatterCategoryName,
                             boolean folderAsCategoryName,
                             String frontmatterTagName,
                             boolean useFileNameAsTitle,
                             boolean useFirstArticlePictureAsCover) throws IOException;
}
