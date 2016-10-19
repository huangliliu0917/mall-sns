package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.entity.Article;
import com.huotu.huobanplus.sns.entity.Category;
import com.huotu.huobanplus.sns.model.AppWikiListModel;
import com.huotu.huobanplus.sns.model.AppWikiModel;
import com.huotu.huobanplus.sns.model.admin.AdminArticleEditModel;
import com.huotu.huobanplus.sns.model.admin.AdminArticleModel;
import com.huotu.huobanplus.sns.model.admin.AdminArticlePageModel;
import com.huotu.huobanplus.sns.model.admin.PagingModel;
import com.huotu.huobanplus.sns.model.common.ArticleType;
import com.huotu.huobanplus.sns.repository.ArticleRepository;
import com.huotu.huobanplus.sns.repository.CategoryRepository;
import com.huotu.huobanplus.sns.repository.CircleRepository;
import com.huotu.huobanplus.sns.repository.UserRepository;
import com.huotu.huobanplus.sns.service.ArticleService;
import com.huotu.huobanplus.sns.service.resource.StaticResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
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

    public AdminArticlePageModel getAdminArticleList(Integer articleType, String name, Integer pageNo, Integer pageSize) {
        AdminArticlePageModel adminArticlePageModel = new AdminArticlePageModel();

        Pageable pageable = new PageRequest(pageNo - 1, pageSize, new Sort(Sort.Direction.ASC, "id"));
        Page<Article> articles = null;
        if (!StringUtils.isEmpty(name)) {
            articles = articleRepository.findByArticleTypeAndNameLike(articleType.equals(1) ? ArticleType.Wiki : ArticleType.Normal, name, pageable);
        } else {
            articles = articleRepository.findByArticleType(articleType.equals(1) ? ArticleType.Wiki : ArticleType.Normal, pageable);
        }

        adminArticlePageModel.setPage(new PagingModel(pageNo, pageSize, articles.getTotalPages(), articles.getTotalElements()));
        adminArticlePageModel.setList(toAdminArticle(articles.getContent()));

        return adminArticlePageModel;
    }

    private List<AdminArticleModel> toAdminArticle(List<Article> articles) {
        List<AdminArticleModel> adminArticleModels = new ArrayList<>();
        articles.forEach(x -> {
            AdminArticleModel adminArticleModel = new AdminArticleModel();
            adminArticleModel.setId(x.getId());
            adminArticleModel.setName(x.getName());
            adminArticleModel.setDate(x.getDate());
            if (x.getPublisher() != null)
                adminArticleModel.setPublisher(x.getPublisher().getNickName());
            adminArticleModel.setClick(x.getClick());
            adminArticleModel.setView(x.getView());
            adminArticleModels.add(adminArticleModel);
        });
        return adminArticleModels;
    }

    @Autowired
    private StaticResourceService staticResourceService;

    public AdminArticleEditModel getAdminArticle(String type, Integer articleType, Long id) throws URISyntaxException {
        AdminArticleEditModel adminArticleEditModel = new AdminArticleEditModel();

        if (type != null && type.equals("edit") && id != null && id > 0) {
            Article article = articleRepository.findOne(id);
            if (article != null) {
                adminArticleEditModel.setType(type);
                adminArticleEditModel.setId(article.getId());
                adminArticleEditModel.setName(article.getName());
                adminArticleEditModel.setArticleType(article.getArticleType().getValue());
                adminArticleEditModel.setContent(article.getContent());
                if (article.getPictureUrl() != null) {
                    adminArticleEditModel.setPictureUrl(article.getPictureUrl());
                    adminArticleEditModel.setPictureFullUrl(staticResourceService.getResource(article.getPictureUrl()).toString());
                }
                adminArticleEditModel.setSummary(article.getSummary());
                adminArticleEditModel.setAdConent(article.getAdConent());
                if (article.getPublisher() != null) {
                    adminArticleEditModel.setUserId(article.getPublisher().getId());
                    adminArticleEditModel.setUserName(article.getPublisher().getNickName());
                }
                if (article.getCategory() != null) {
                    adminArticleEditModel.setCategoryId(article.getCategory().getId());
                    adminArticleEditModel.setCategoryName(article.getCategory().getName());
                }

                if (article.getCircle() != null) {
                    adminArticleEditModel.setCircleId(article.getCircle().getId());
                    adminArticleEditModel.setCircleName(article.getCircle().getName());
                }
                return adminArticleEditModel;
            }
        }

        //add缺省值
        adminArticleEditModel.setType(type);
        adminArticleEditModel.setId(0L);
        adminArticleEditModel.setName("");
        adminArticleEditModel.setArticleType(articleType);
        adminArticleEditModel.setContent("");
        adminArticleEditModel.setPictureUrl("");
        adminArticleEditModel.setSummary("");
        adminArticleEditModel.setAdConent("");
        adminArticleEditModel.setUserId(0L);
        adminArticleEditModel.setUserName("");
        adminArticleEditModel.setCategoryId(0);
        adminArticleEditModel.setCircleId(0L);
        return adminArticleEditModel;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CircleRepository circleRepository;

    public void save(Integer articleType, Long id
            , String name, Long userId, String pictureUrl, String content
            , String summary, Integer categoryId, Long circleId, String adConent) {

        Article article = null;
        if (id != null && id > 0) {
            article = articleRepository.findOne(id);
        }
        if (article == null) {
            article = new Article();
            article.setClick(0L);
            article.setView(0L);
            article.setComments(0L);
            article.setTop(false);
        }

        article.setArticleType(articleType.equals(1) ? ArticleType.Wiki : ArticleType.Normal);
        article.setName(name);
        article.setPublisher(userRepository.findOne(userId));
        article.setPictureUrl(pictureUrl);
        article.setContent(content);
        article.setSummary(summary);
        article.setDate(new Date());
        if (categoryId != null && categoryId > 0)
            article.setCategory(categoryRepository.findOne(categoryId));
        if (circleId != null && circleId > 0)
            article.setCircle(circleRepository.findOne(circleId));
        article.setAdConent(adConent);
        articleRepository.save(article);

    }
}