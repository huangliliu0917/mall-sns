package com.huotu.huobanplus.sns.service;

import com.huotu.huobanplus.sns.model.AppWikiListModel;
import com.huotu.huobanplus.sns.model.AppWikiModel;
import com.huotu.huobanplus.sns.model.admin.AdminArticleEditModel;
import com.huotu.huobanplus.sns.model.admin.AdminArticlePageModel;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
public interface ArticleService {
    List<AppWikiListModel> getAppWikiList(Integer catalogId, Long lastId);

    AppWikiModel getAppWiki(Long id);

    AdminArticlePageModel getAdminArticleList(Integer articleType, String name, Integer pageNo, Integer pageSize);

    AdminArticleEditModel getAdminArticle(String type, Integer articleType, Long id) throws URISyntaxException;


    /**
     *
     * @param articleType
     * @param id
     * @param name
     * @param userId
     * @param pictureUrl
     * @param content
     * @param summary
     * @param categoryId
     * @param circleId
     * @param adConent
     */
    void  save(Integer articleType, Long id
            , String name, Long userId, String pictureUrl, String content
            , String summary, Integer categoryId, Long circleId, String adConent);
}
