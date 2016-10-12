package com.huotu.huobanplus.sns.service;

import com.huotu.huobanplus.sns.model.AppWikiListModel;
import com.huotu.huobanplus.sns.model.AppWikiModel;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
public interface ArticleService {
    List<AppWikiListModel> getAppWikiList(Integer catalogId, Long lastId);
    AppWikiModel getAppWiki(Long id);
}
