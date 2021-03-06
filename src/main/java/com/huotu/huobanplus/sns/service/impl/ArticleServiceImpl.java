/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huotu.huobanplus.sns.entity.*;
import com.huotu.huobanplus.sns.exception.ClickException;
import com.huotu.huobanplus.sns.model.*;
import com.huotu.huobanplus.sns.model.admin.*;
import com.huotu.huobanplus.sns.model.common.AppCode;
import com.huotu.huobanplus.sns.model.common.ArticleType;
import com.huotu.huobanplus.sns.model.common.CommentStatus;
import com.huotu.huobanplus.sns.repository.*;
import com.huotu.huobanplus.sns.service.ArticleService;
import com.huotu.huobanplus.sns.service.RedisService;
import com.huotu.huobanplus.sns.service.resource.StaticResourceService;
import com.huotu.huobanplus.sns.utils.ContractHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 文章服务实现
 * Created by Administrator on 2016/10/12.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private static Log log = LogFactory.getLog(ArticleServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ConcernRepository concernRepository;

    @Autowired
    private UserArticleRepository userArticleRepository;
    @Autowired
    private StaticResourceService staticResourceService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CircleRepository circleRepository;
    @Autowired
    private ArticleCommentRepository articleCommentRepository;
    //    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisTemplate<String, AppArticleCommentModel> articleCommentRedisTemplate;
    @Autowired
    private RedisService redisService;

    //    @Autowired
//    private RedisTemplate<String, AppArticleCommentModel> articleReplyCommentRedisTemplate;
    @Autowired
    private TagRespository tagRespository;
    @Autowired
    private ArticleClickRepository articleClickRepository;
    @Autowired
    private EntityManager entityManager;
    private ObjectMapper objectMapper = new ObjectMapper();

    public List<AppWikiListModel> getAppWikiList(Long customerId, Integer catalogId, Long lastId) {
        List<AppWikiListModel> appWikiListModels;

        Pageable pageable = new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "id"));

        Category category = null;
        if (catalogId != null && catalogId > 0) {
            category = categoryRepository.findOne(catalogId);
        }

        if (category == null) {
            if (lastId == null || lastId <= 0) {
                Page<Article> articles = articleRepository.findByCustomerIdAndArticleType(customerId, ArticleType.Wiki, pageable);
                appWikiListModels = toAppWikiList(articles.getContent());
            } else {
                List<Article> articles = articleRepository.findByCustomerIdAndArticleType(ArticleType.Wiki, lastId);
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
                if (x.getPublisher().getLevel() != null)
                    appWikiListModel.setUserLevel(x.getPublisher().getLevel().getId());
                appWikiListModel.setUserName(x.getPublisher().getNickName());
                appWikiListModel.setUserId(x.getPublisher().getId());
            }
            //todo
            appWikiListModel.setConcerned(false);
            appWikiListModels.add(appWikiListModel);
        });

        return appWikiListModels;
    }

    public AdminArticlePageModel getAdminArticleList(Long customerId, Integer articleType, String name, Integer pageNo, Integer pageSize) {
        AdminArticlePageModel adminArticlePageModel = new AdminArticlePageModel();

        Pageable pageable = new PageRequest(pageNo - 1, pageSize, new Sort(Sort.Direction.DESC, "id"));
        Page<Article> articles;
        if (!StringUtils.isEmpty(name)) {
            articles = articleRepository.findByCustomerIdAndArticleTypeAndNameLike(customerId, articleType.equals(1) ? ArticleType.Wiki : ArticleType.Normal, "%" + name + "%", pageable);
        } else {
            articles = articleRepository.findByCustomerIdAndArticleType(customerId, articleType.equals(1) ? ArticleType.Wiki : ArticleType.Normal, pageable);
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
            adminArticleModel.setEnabled(x.getEnabled());
            if (x.getPublisher() != null)
                adminArticleModel.setPublisher(x.getPublisher().getNickName());
            adminArticleModel.setClick(x.getClick());
            adminArticleModel.setView(x.getView());
            adminArticleModel.setTop(x.getTop() == null ? false : x.getTop());
            adminArticleModels.add(adminArticleModel);
        });
        return adminArticleModels;
    }

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

                Set<Tag> set = article.getTags();
                List<AdminTagsModel> adminTagsModels = set.stream()
                        .map(tag -> new AdminTagsModel(tag.getId(), tag.getName())).collect(Collectors.toList());

                adminArticleEditModel.setTags(adminTagsModels);
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

    public Article save(Long customerId, Integer articleType, Long id
            , String name, Long userId, String pictureUrl, String content
            , String summary, Integer categoryId, Long circleId, String adConent, String tags) {

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

        article.setCustomerId(customerId);
        article.setArticleType(articleType.equals(1) ? ArticleType.Wiki : ArticleType.Normal);
        article.setName(name);
        article.setPublisher(userId == null ? null : userRepository.findOne(userId));
        article.setPictureUrl(pictureUrl);
        article.setContent(content);
        article.setSummary(summary);
        article.setDate(new Date());
        if (categoryId != null && categoryId > 0)
            article.setCategory(categoryRepository.findOne(categoryId));
        if (circleId != null && circleId > 0)
            article.setCircle(circleRepository.findOne(circleId));
        article.setAdConent(adConent);
        if (!StringUtils.isEmpty(tags)) {
            Set<Tag> tags1 = new HashSet<>();
            for (String item : tags.split(",")) {
                tags1.add(tagRespository.findOne(Integer.parseInt(item)));
            }
            article.setTags(tags1);
        }
        article = articleRepository.saveAndFlush(article);
        return article;

    }

    @Transactional
    @Override
    public Long addArticleResult(Integer articleType, String name, User user, String pictureUrl,
                                 String content, Long circleId) throws IOException, InterruptedException {
        //文章内容截取成为简介,暂定为80个字
        String summary;
        if (content.length() < 80) {
            summary = content;
        } else {
            summary = content.substring(0, 80) + "...";
        }
        Article article = save(user.getCustomerId(), ArticleType.Normal.getValue(), null, name, user.getId(),
                pictureUrl, content, summary, null, circleId, null, null);

        addUserArticle(articleType, article.getId(), name, user, pictureUrl, summary);
        //用户的文章数增加
        userRepository.addArticleAmount(user.getId());
//        BoundHashOperations<String, String, Long> userOperations = redisTemplate
//                .boundHashOps(ContractHelper.userFlag + user.getId());
//        userOperations.putIfAbsent("articleAmount", 0L);
//        synchronized (userOperations.get("articleAmount")) {
//            Long articleAmount = userOperations.get("articleAmount");
//            userOperations.put("articleAmount", articleAmount + 1L);
//        }
        //圈子的文章数增加
        circleRepository.addArticleAmount(circleId);
//        BoundHashOperations<String, String, Long> circleOperations = redisTemplate
//                .boundHashOps(ContractHelper.circleFlag + id);
//        circleOperations.putIfAbsent("articleAmount", 0L);
//        synchronized (circleOperations.get("articleAmount")) {
//            Long articleAmount = circleOperations.get("articleAmount");
//            circleOperations.put("articleAmount", articleAmount + 1L);
//        }
        return article.getId();
    }

    @Override
    public ArticleComment commentArticle(Long id, String content, User user) throws IOException {
        Article article = articleRepository.getOne(id);
        ArticleComment articleComment = new ArticleComment();
        articleComment.setUser(user);
        articleComment.setCustomerId(user.getCustomerId());
        articleComment.setContent(content);
        articleComment.setDate(new Date());
        articleComment.setCommentStatus(CommentStatus.Normal);
        articleComment.setArticle(article);
        Optional<Long> maxFloor = articleCommentRepository.getMaxFloorByArticleId(id);
        articleComment.setFloor(maxFloor.orElse(0L) + 1L);
        articleCommentRepository.saveAndFlush(articleComment);
//        articleRepository.addComments(id);
//        BoundHashOperations<String, String, Long> articleOperations = redisTemplate
//                .boundHashOps(ContractHelper.articleFlag + id);
//        Long comments = articleOperations.get("comments");
//        if (Objects.isNull(comments))
//            articleOperations.put("comments", 1L);
//        else
//            articleOperations.put("comments", comments + 1L);
        redisService.addArticleCommentNum(id);

//        BoundListOperations<String, AppArticleCommentModel> articleCommentBoundListOperations =
//                articleCommentRedisTemplate.boundListOps(ContractHelper.articleCommentFlag + id);
//        articleCommentBoundListOperations.leftPush(changeModel(articleComment));
        redisService.addArticleComment(id, articleComment);
        return articleComment;
    }

    /**
     * 保存用户和文章的关联表
     *
     * @param articleType
     * @param id
     * @param name
     * @param user  用户
     * @param pictureUrl
     * @param summary
     * @throws IOException
     * @throws InterruptedException
     */
    private void addUserArticle(Integer articleType, Long id, String name, User user, String pictureUrl,
                                String summary) throws IOException, InterruptedException {
        List<Concern> list = concernRepository.findByToUser(user);
        int size = list.size();
        Date date = new Date();
        saveUserArticle(articleType, id, name, user, pictureUrl, summary, date, user.getId());
        for (int i = 0; i < size; i++) {
            saveUserArticle(articleType, id, name, user, pictureUrl, summary, date, list.get(i).getToUser().getId());
            if (i > 0 && i % 10 == 0)
                Thread.sleep(500);
        }
    }

    private void saveUserArticle(Integer articleType, Long id, String name, User user, String pictureUrl,
                                 String summary, Date date, Long ownerId) throws IOException {
        UserArticle userArticle = new UserArticle();
        userArticle.setCustomerId(user.getCustomerId());
        userArticle.setArticleType(articleType.equals(1) ? ArticleType.Wiki : ArticleType.Normal);
        userArticle.setName(name);
        userArticle.setPictureUrl(pictureUrl);
        userArticle.setPublisherId(user.getId());
        userArticle.setPublisherNickname(user.getNickName());
        userArticle.setPublisherHeaderImageUrl(user.getImgURL());
        if (Objects.nonNull(user.getLevel()))
            userArticle.setPublisherLevelId(user.getLevel().getId());
        userArticle.setPublisherAuthenticationId(user.getAuthenticationType());
        userArticle.setSummary(summary);
        userArticle.setDate(date);
        userArticle.setOwnerId(ownerId);
        userArticle.setArticleId(id);
        userArticleRepository.save(userArticle);
    }

    public List<AppCircleArticleModel> getUserArticleList(Long userId, Long lastId) {
        List<AppCircleArticleModel> appCircleArticleModels = new ArrayList<>();

        StringBuilder hql = new StringBuilder();
        hql.append("select article from Article article where article.publisher.id=:userId");//
        if (lastId != null && lastId > 0) {
            hql.append(" and article.id<:lastId");
        }

        Query query = entityManager.createQuery(hql.toString());
        query.setParameter("userId", userId);
        if (lastId != null && lastId > 0) {
            query.setParameter("lastId", lastId);
        }
        query.setMaxResults(10);
        List list = query.getResultList();

//        log.info("database amount:" + articleRepository.count());
//
//        StringBuilder hql = new StringBuilder();
//        hql.append("select article from Article article");// where article.publisher.id=:userId
////        if (lastId != null && lastId > 0) {
////            hql.append(" and article.id<:lastId");
////        }
//
//        Query query = entityManager.createQuery(hql.toString());
////        query.setParameter("userId", userId);
////        if (lastId != null && lastId > 0) {
////            query.setParameter("lastId", lastId);
////        }
//        query.setMaxResults(100);
//        List list = query.getResultList();
//        log.info("query amount:" +list.size());

        list.forEach(object -> {
            Article article = (Article) object;
            AppCircleArticleModel appCircleArticleModel = new AppCircleArticleModel();
            appCircleArticleModel.setName(article.getName());
            appCircleArticleModel.setTime(article.getDate().getTime());
            appCircleArticleModel.setCommentsAmount(article.getComments());
            appCircleArticleModel.setViewAmount(article.getView());
            appCircleArticleModel.setPid(article.getId());
            appCircleArticleModel.setPictureUrl(article.getPictureUrl());
//            appCircleArticleModel.setUrl();
            if (article.getPublisher() != null) {
                appCircleArticleModel.setUserHeadUrl(article.getPublisher().getImgURL());
                appCircleArticleModel.setUserName(article.getPublisher().getNickName());
                appCircleArticleModel.setUserLevel(article.getPublisher().getLevel().getId());
            }
            appCircleArticleModels.add(appCircleArticleModel);
        });
        return appCircleArticleModels;
    }

    @Transactional
    @Override
    public void articleClick(Article article, User user) throws IOException, ClickException {
        ArticleClick click = articleClickRepository.findByArticleAndUser(article, user);
        if (Objects.nonNull(click)) {
            throw new ClickException(AppCode.ERROR_CLICK_ALREADY.getValue(), AppCode.ERROR_CLICK_ALREADY.getName());
        }
        click = new ArticleClick();
        click.setCustomerId(user.getCustomerId());
        click.setArticle(article);
        click.setDate(new Date());
        click.setUser(user);
        articleClickRepository.save(click);
        //文章点赞数加一
        articleRepository.addClick(article.getId());

    }

    @Transactional
    @Override
    public ArticleComment replyComment(ArticleComment articleComment, User user, String content) throws IOException {
        ArticleComment replyArticleComment = new ArticleComment();
        replyArticleComment.setUser(user);
        replyArticleComment.setCustomerId(user.getCustomerId());
        replyArticleComment.setContent(content);
        replyArticleComment.setDate(new Date());
        replyArticleComment.setCommentStatus(CommentStatus.Normal);
        replyArticleComment.setArticle(articleComment.getArticle());
        replyArticleComment.setArticleComment(articleComment);
        Long articleId = articleComment.getArticle().getId();
        Optional<Long> maxFloor = articleCommentRepository.getMaxFloorByArticleId(articleId);
        replyArticleComment.setFloor(maxFloor.orElse(0L) + 1L);

//        articleCommentRedisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        ListOperations<String, AppArticleCommentModel> listOperations = articleCommentRedisTemplate.opsForList();
        //取出上一条评论的冗余列表
        List<AppArticleCommentModel> models = listOperations
                .range(ContractHelper.articleReplyCommentFlag + articleComment.getId(), 0L, -1);
        //转化model
        AppArticleCommentModel model = redisService.changeModel(articleComment);

        models.add(model);
        Collections.sort(models, (o1, o2) -> o1.getDate().intValue() - o2.getDate().intValue());
        replyArticleComment.setExtend(objectMapper.writeValueAsString(models));
        if (null == articleComment.getPath()) {
            replyArticleComment.setPath("," + articleComment.getId() + ",");
        } else {
            replyArticleComment.setPath(articleComment.getPath() + articleComment.getId() + ",");
        }
        replyArticleComment = articleCommentRepository.saveAndFlush(replyArticleComment);
        //评论列表+1
//        BoundListOperations<String, AppArticleCommentModel> articleCommentBoundListOperations =
//                articleCommentRedisTemplate.boundListOps(ContractHelper.articleCommentFlag + articleId);
//        articleCommentBoundListOperations.leftPush(changeModel(replyArticleComment));
        redisService.addArticleComment(articleId, replyArticleComment);
        //本次评论的redis缓存
//        BoundListOperations<String, AppArticleCommentModel> ArticleReplyCommentBoundListOperations =
//                articleCommentRedisTemplate.boundListOps(ContractHelper.articleReplyCommentFlag + replyArticleComment.getId());
//        for (int i = 0; i < models.size(); i++) {
//            ArticleReplyCommentBoundListOperations.rightPush(models.get(i));
//        }
        redisService.addArticleCommentByCommentId(replyArticleComment.getId(), models);

        //评论数量+1
        redisService.addArticleCommentNum(articleId);

//        BoundHashOperations<String, String, Long> articleOperations = redisTemplate
//                .boundHashOps(ContractHelper.articleFlag + articleId);
//        Long comments = articleOperations.get("comments");
//        articleOperations.put("comments", comments + 1L);
        return replyArticleComment;
        //        articleRepository.addComments(id);
    }

    public List<AppCircleIndexArticleListModel> search(Long customerId, String key, Long lastId) {
        StringBuilder hql = new StringBuilder();
        hql.append("select article from Article article where article.customerId=:customerId and article.name like :name");
        if (lastId != null && lastId > 0) {
            hql.append(" and article.id<:lastId");
        }

        Query query = entityManager.createQuery(hql.toString());
        query.setParameter("customerId", customerId);
        query.setParameter("name", "%" + key + "%");
        if (lastId != null && lastId > 0) {
            query.setParameter("lastId", lastId);
        }
        query.setMaxResults(10);
        List list = query.getResultList();

        List<AppCircleIndexArticleListModel> appCircleIndexArticleListModels = new ArrayList<>();
        list.forEach(object -> {
            Article article = (Article) object;
            AppCircleIndexArticleListModel appCircleIndexArticleListModel = new AppCircleIndexArticleListModel();
            appCircleIndexArticleListModel.setArticleId(article.getId());
            appCircleIndexArticleListModel.setName(article.getName());
            appCircleIndexArticleListModel.setCommentsAmount(article.getComments());
            appCircleIndexArticleListModel.setViewAmount(article.getView());
            if (article.getPublisher() != null) {
                appCircleIndexArticleListModel.setUserName(article.getPublisher().getNickName());
            }
            appCircleIndexArticleListModels.add(appCircleIndexArticleListModel);
        });
        return appCircleIndexArticleListModels;
    }

    @Override
    public AppCircleArticleModel[] getTopArticleModels(Long userId,Long customerId,Long circleId) throws IOException {
        List<Article> articles=articleRepository.findByTopAndCircle_IdAndEnabledOrderByIdDesc(true,circleId,true);
        AppCircleArticleModel[] models=new AppCircleArticleModel[articles.size()];
        if(articles.isEmpty()){
            return models;
        }

        Set<Long> toUserIds=null;
        //获取当前用户对每一篇发布人的关注信息
        if(userId!=null){
            toUserIds=findAttentionUsersByArticles(articles,userId,customerId);
        }

        for(int i=0;i<articles.size();i++){
            models[i]=getAppCircleArticleModel(articles.get(i),toUserIds);
        }
        return models;
    }

    @Override
    public AppCircleArticleModel[] getArticleListModels(Long customerId, Long userId, Long lastId, Long circleId, Integer type) throws IOException {
        List<Article> articles;
        if(type==0){
            if(lastId==null){
                articles=articleRepository.findTop20ByCircle_IdOrderByIdDesc(circleId,new PageRequest(0,20));

            }else {
                articles=articleRepository.findTop20ByCircle_IdAndIdLessThanOrderByIdDesc(circleId,lastId,new PageRequest(0,20));
            }

        }else {
            if(lastId==null){
                articles=articleRepository.findTop20ByCircle_IdOrderByViewDesc(circleId,new PageRequest(0,20));
            }else {
                Article article=articleRepository.findOne(lastId);
                articles=articleRepository.findTop20ByCircle_IdAndViewLessThanOrderByViewDesc(circleId,article.getView(),new PageRequest(0,20));

            }
        }

        AppCircleArticleModel[] models=new AppCircleArticleModel[articles.size()];
        if(articles.isEmpty()){
            return models;
        }

        Set<Long> toUserIds=null;
        //获取当前用户对每一篇发布人的关注信息
        if(userId!=null){
            toUserIds=findAttentionUsersByArticles(articles,userId,customerId);
        }

        for(int i=0;i<articles.size();i++){
            models[i]=getAppCircleArticleModel(articles.get(i),toUserIds);
        }
        return models;
    }

    @Override
    public AppCircleArticleModel getAppCircleArticleModel(Article article, Set<Long> toUserIds) {
        AppCircleArticleModel model = new AppCircleArticleModel();
        model.setPid(article.getId());
        model.setName(article.getName());
        model.setPictureUrl(article.getPictureUrl());
        model.setUserId(article.getPublisher().getId());
        model.setUserName(article.getPublisher().getNickName());
        model.setUserHeadUrl(article.getPublisher().getImgURL());
        model.setUserLevel(article.getPublisher().getLevel().getId());
        model.setTime(article.getDate().getTime());
        model.setViewAmount(article.getView());
        model.setCommentsAmount(article.getComments());
        Long toUserId=article.getPublisher().getId();
        //有用户列表，并且当前文章的发布人包含在用户列表中，才说明我是已经关注该文章的发布人
        model.setConcerned(toUserIds!=null&&toUserIds.contains(toUserId));
        return model;
    }

    @Override
    public Set<Long> getToUserIdsByArticles(List<Article> articles) {
        Set<Long> toUserIds = new HashSet<>();

        //获取发布文章的用户列表
        articles.forEach(a -> {
            toUserIds.add(a.getPublisher().getId());
        });
        return toUserIds;
    }

    @Override
    public Set<Long> getToUserIdsByConcerns(List<Concern> concerns) {
        Set<Long> toUserIds = new HashSet<>();

        concerns.forEach(concern -> {
            toUserIds.add(concern.getToUser().getId());
        });
        return toUserIds;
    }

    @Override
    public AppCircleArticleDetailModel getArticleDetailModel(Article article) {
        AppCircleArticleDetailModel model=new AppCircleArticleDetailModel();
        model.setPid(article.getId());
        model.setName(article.getName());
        model.setContent(article.getContent());
        model.setPictureUrl(article.getPictureUrl());
        model.setUserName(article.getPublisher().getNickName());
        model.setUserHeadUrl(article.getPublisher().getImgURL());
        model.setClickAmount(article.getClick());
        model.setTime(article.getDate().getTime());
        return model;
    }

    @Override
    public Set<Long> findAttentionUsersByArticles(List<Article> articles,Long userId,Long customerId) throws IOException {
        //获取给定文章列表里面的发布用户列表
        Set<Long> articleUserIds=getToUserIdsByArticles(articles);

        //根据发布用户列表获取和我关注的信息
        List<Concern> concerns=concernRepository.findByUserAndToUsers(userId,customerId,articleUserIds);

        //获取我实际关注的发布用户列表
        return getToUserIdsByConcerns(concerns);
    }
}