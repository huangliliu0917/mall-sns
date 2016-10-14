package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.entity.Article;
import com.huotu.huobanplus.sns.entity.Category;
import com.huotu.huobanplus.sns.model.AppWikiListModel;
import com.huotu.huobanplus.sns.model.AppWikiModel;
import com.huotu.huobanplus.sns.model.common.ArticleType;
import com.huotu.huobanplus.sns.repository.ArticleRepository;
import com.huotu.huobanplus.sns.repository.CategoryRepository;
import com.huotu.huobanplus.sns.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleRepository articleRepository;


    public List<AppWikiListModel> getAppWikiList(Integer catalogId, Long lastId) {
        List<AppWikiListModel> appWikiListModels = new ArrayList<>();

        Pageable pageable = new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "id"));

        Category category = null;
        if (catalogId != null && catalogId > 0) {
            category = categoryRepository.findOne(catalogId);
        }

        if (category == null) {
            if (lastId == null || lastId <= 0) {
                Page<Article> articles = articleRepository.findByArticleType(ArticleType.Wiki, pageable);
                appWikiListModels = toAppWikiList(articles.getContent());
            } else {
                List<Article> articles = articleRepository.findByArticleType(ArticleType.Wiki, lastId);
                appWikiListModels = toAppWikiList(articles);
            }
        } else {
            if (lastId == null || lastId <= 0) {
                Page<Article> articles = articleRepository.findByArticleTypeAndCategory(ArticleType.Wiki, category, pageable);
                appWikiListModels = toAppWikiList(articles.getContent());
            } else {
                List<Article> articles = articleRepository.findByArticleTypeAndCategory(ArticleType.Wiki, category, lastId);
                appWikiListModels = toAppWikiList(articles);
            }
        }

        return appWikiListModels;
    }

    public AppWikiModel getAppWiki(Long id) {
        Article article = articleRepository.findOne(id);
        return toAppWiki(article);
    }

    public AppWikiModel toAppWiki(Article article) {
        AppWikiModel appWikiModel = new AppWikiModel();
        appWikiModel.setPid(article.getId());
        appWikiModel.setName(article.getName());
        appWikiModel.setContent(article.getContent());
        appWikiModel.setTime(article.getDate().getTime());
        appWikiModel.setAdConent(article.getAdConent());
        if (article.getPublisher() != null) {
            appWikiModel.setUserHeadUrl(article.getPublisher().getImgURL());
            appWikiModel.setUserName(article.getPublisher().getNickName());
        }
        return appWikiModel;
    }

    private List<AppWikiListModel> toAppWikiList(List<Article> articles) {
        List<AppWikiListModel> appWikiListModels = new ArrayList<>();
        articles.forEach(x -> {
            AppWikiListModel appWikiListModel = new AppWikiListModel();
            appWikiListModel.setPid(x.getId());
            appWikiListModel.setName(x.getName());
            appWikiListModel.setSummary(x.getSummary());
            appWikiListModel.setPictureUrl(x.getPictureUrl());
            appWikiListModel.setTime(x.getDate().getTime());
            appWikiListModel.setViewAmount(x.getView());
            if (x.getPublisher() != null) {
                appWikiListModel.setUserHeadUrl(x.getPublisher().getImgURL());
                appWikiListModel.setUserLevel(x.getPublisher().getLevel().getId());
                appWikiListModel.setUserName(x.getPublisher().getNickName());
//            appWikiListModel.setUrl();
            }
            appWikiListModels.add(appWikiListModel);
        });

        return appWikiListModels;
    }

}
