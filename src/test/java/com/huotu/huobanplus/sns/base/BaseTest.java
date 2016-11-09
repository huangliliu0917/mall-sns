package com.huotu.huobanplus.sns.base;


import com.huotu.huobanplus.sns.entity.Article;
import com.huotu.huobanplus.sns.entity.Category;
import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.model.common.ArticleType;
import com.huotu.huobanplus.sns.model.common.CategoryType;
import com.huotu.huobanplus.sns.repository.*;
import com.huotu.huobanplus.sns.service.ArticleService;
import com.huotu.huobanplus.sns.service.UserService;
import com.huotu.huobanplus.sns.utils.StringHelper;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by lgh on 2016/1/12.
 */

public class BaseTest {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected Long customerId = 7788L;
    @Autowired
    private CategoryRepository categoryRepository;


    @Before
    public void createMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
//
//    /**
//     * 生成一个没有绑定手机的用户
//     *
//     * @param password
//     * @param userRepository
//     * @return
//     * @throws UnsupportedEncodingException
//     */
//    public User generateUserWithoutMobile(@NotNull String password, UserRepository userRepository) throws UnsupportedEncodingException {
//        String userName = generateInexistentMobile(userRepository);
//        User user = new User();
//        user.setEnabled(true);
//        user.setRegTime(new Date(System.currentTimeMillis() + new Random().nextInt(360 * 30 * 24 * 60 * 60)));
//        user.setUsername(userName);
//        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes("UTF-8")).toLowerCase());
//        user.setUserHead(staticResourceService.USER_HEAD_PATH + "defaultH.jpg");
//        return userRepository.saveAndFlush(user);
//    }
//
//    /**
//     * 生成一个班定了手机 但是没有token的用户
//     *
//     * @param password
//     * @param userRepository
//     * @return
//     * @throws UnsupportedEncodingException
//     */
//    public User generateUserWithMobileWithoutToken(String password, UserRepository userRepository) throws UnsupportedEncodingException {
//        User user = generateUserWithoutMobile(password, userRepository);
//        user.setMobileBinded(true);
//        user.setRegTime(new Date(System.currentTimeMillis() + new Random().nextInt(360 * 30 * 24 * 60 * 60)));
//        user.setMobile(user.getUsername());
//        user.setUserHead(staticResourceService.USER_HEAD_PATH + "defaultH.jpg");
//        user.setBuyAndTurnType(CommonEnum.BuyAndTurnType.buy);
//        return userRepository.saveAndFlush(user);
//    }
//
//    /**
//     * 生成一个绑定了手机而且拥有登录token的用户
//     *
//     * @param password
//     * @param userRepository
//     * @return
//     * @throws UnsupportedEncodingException
//     */
//    public User generateUserWithMobileWithToken(String password, UserRepository userRepository) throws UnsupportedEncodingException {
//        User user = generateUserWithMobileWithoutToken(password, userRepository);
//        user.setToken(DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes("UTF-8")));
//        user.setUserHead(staticResourceService.USER_HEAD_PATH + "defaultH.jpg");
//        user.setUserFromType(CommonEnum.UserFromType.register);
//        user.setBuyAndTurnType(CommonEnum.BuyAndTurnType.buy);
//        user.setMoney(new BigDecimal("1000000"));
//        return userRepository.saveAndFlush(user);
//    }
//

    /**
     * 返回一个不存在的手机号码
     *
     * @return 手机号码
     */
    public String generateMobile() {
        Random random = new Random();
        return StringHelper.RandomNum(random, 11);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private UserService userService;

    public User createUser(Long customerId
            , Long userId
            , String mobile
            , String openId
            , String nickName
            , String imageUrl) {
        return userService.register(customerId, userId, mobile, openId, nickName, imageUrl);
    }


    public Category createCategory() {
        return createCategory(CategoryType.Normal, null);
    }


    public Category createCategory(CategoryType categoryType, Category parent) {
        Category category = new Category();
        category.setCustomerId(customerId);
        category.setName(UUID.randomUUID().toString().substring(0, 9));
        category.setSort(1);
        category.setCategoryType(categoryType);
        category.setParent(parent);
        category = categoryRepository.saveAndFlush(category);
        return category;
    }


    @Autowired
    private ArticleService articleService;

    public Article createArticle(Integer articleType, Long id
            , String name, Long userId, String pictureUrl, String content
            , String summary, Integer categoryId, Long circleId, String adConent, String tags) {
        return articleService.save(customerId, articleType, id, name, userId, pictureUrl, content, summary, categoryId, circleId, adConent, tags);
    }

    public Article createArticle(ArticleType articleType, Long userId, Integer categoryId, Long circleId) {
        Long id = 0L;
        String name = UUID.randomUUID().toString();
        String pictureUrl = "";
        String content = "";
        String summary = "";
        String adConent = "";
        String tags = "";
        return articleService.save(customerId, articleType.getValue(), id, name, userId, pictureUrl, content, summary, categoryId, circleId, adConent, tags);
    }

    @Autowired
    private CircleRepository circleRepository;

    @Autowired
    private ArticleRepository articleRepository;


    public Article save(Long customerId, Integer articleType, Long id
            , String name, Long userId, String pictureUrl, String content
            , String summary, Integer categoryId, Long circleId, String adConent, String tags) {

        Article article = new Article();
        article.setClick(0L);
        article.setView(0L);
        article.setComments(0L);
        article.setTop(false);

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
//        if (!StringUtils.isEmpty(tags)) {
//            Set<Tag> tags1 = new HashSet<>();
//            for (String item : tags.split(",")) {
//                tags1.add(tagRespository.findOne(Integer.parseInt(item)));
//            }
//            article.setTags(tags1);
//        }
        article = articleRepository.saveAndFlush(article);
        return article;

    }


}
