package com.huotu.huobanplus.sns.service;

import com.huotu.huobanplus.sns.model.AppWikiListModel;
import com.huotu.huobanplus.sns.model.AppWikiModel;
import com.huotu.huobanplus.sns.model.admin.AdminArticleEditModel;
import com.huotu.huobanplus.sns.model.admin.AdminArticlePageModel;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
public interface ArticleService {
    List<AppWikiListModel> getAppWikiList(Integer catalogId, Long lastId);

    AppWikiModel getAppWiki(Long id);

    AdminArticlePageModel getAdminArticleList(Integer articleType, String name, Integer pageNo, Integer pageSize);

    AdminArticleEditModel getAdminArticle(String type, Integer articleType, Long id);

    void  save(Integer articleType, String type, Long id
            , String name, Long userId, String pictureUrl, String content
            , String summary, Integer categoryId, Long circleId, String adConent);
}
