package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.entity.Tag;
import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.model.admin.AdminTagsModel;
import com.huotu.huobanplus.sns.model.admin.AdminTagsPageModel;
import com.huotu.huobanplus.sns.model.admin.PagingModel;
import com.huotu.huobanplus.sns.repository.TagRespository;
import com.huotu.huobanplus.sns.repository.UserRepository;
import com.huotu.huobanplus.sns.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 标签
 * Created by Administrator on 2016/10/14.
 */
@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagRespository tagRespository;

    public AdminTagsPageModel getAdminTagsList(Long customerId, String name, Integer pageNo, Integer pageSize) {
        AdminTagsPageModel adminTagsPageModel = new AdminTagsPageModel();

        Pageable pageable = new PageRequest(pageNo - 1, pageSize, new Sort(Sort.Direction.ASC, "id"));

        Page<Tag> tags = null;
        if (!StringUtils.isEmpty(name)) {
            tags = tagRespository.findByCustomerIdAndNameLike(customerId, "%" + name + "%", pageable);
        } else {
            tags = tagRespository.findByCustomerId(customerId, pageable);
        }

        adminTagsPageModel.setPage(new PagingModel(pageNo, pageSize, tags.getTotalPages(), tags.getTotalElements()));
        adminTagsPageModel.setList(toAdminTags(tags.getContent()));
        return adminTagsPageModel;
    }

    private List<AdminTagsModel> toAdminTags(List<Tag> tags) {
        List<AdminTagsModel> adminTagsModels = new ArrayList<>();
        tags.forEach(x -> {
            adminTagsModels.add(new AdminTagsModel(x.getId(), x.getName()));
        });
        return adminTagsModels;
    }

    private List<AdminTagsModel> toAdminTags(Set<Tag> tags) {
        List<AdminTagsModel> adminTagsModels = new ArrayList<>();
        Iterator iterator = tags.iterator();
        while (iterator.hasNext()) {
            Tag tag = (Tag) iterator.next();
            adminTagsModels.add(new AdminTagsModel(tag.getId(), tag.getName()));
        }

        return adminTagsModels;
    }

    public AdminTagsModel getAdminTags(Integer id) {
        Tag tag = tagRespository.findOne(id);
        if (tag != null) {
            AdminTagsModel adminTagsModel = new AdminTagsModel();
            adminTagsModel.setId(tag.getId());
            adminTagsModel.setName(tag.getName());
            return adminTagsModel;
        }
        return null;
    }

    public void save(Long customerId, Integer id, String name) {
        Tag tag;
        if (id != null && id > 0) {
            tag = tagRespository.findOne(id);
        } else {
            tag = new Tag();
        }
        tag.setName(name);
        tag.setCustomerId(customerId);
        tagRespository.save(tag);
    }

    public List<AdminTagsModel> addTags(Long customerId, Integer tagsType, Long id, String tagsIds) {
        List<AdminTagsModel> result = null;
        switch (tagsType) {
            case 0:
                result = addUserTags(id, tagsIds);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
        return result;
    }

    @Autowired
    private UserRepository userRepository;

    private List<AdminTagsModel> addUserTags(Long id, String tagsIds) {
        Set<Tag> tags = null;
        User user = userRepository.findOne(id);
        if (user != null && !StringUtils.isEmpty(tagsIds)) {
            tags = user.getTags();
            for (String item : tagsIds.split(",")) {
                Integer itemId = Integer.parseInt(item);
                if (!haveTags(tags, itemId)) {
                    tags.add(tagRespository.findOne(itemId));
                }
            }

            user.setTags(tags);
            userRepository.save(user);
        }
        return toAdminTags(tags);
    }

    private boolean haveTags(Set<Tag> tags, Integer id) {
        boolean result = false;
        Iterator iterator = tags.iterator();
        while (iterator.hasNext()) {
            Tag tag = (Tag) iterator.next();
            if (tag.getId().equals(id)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void deleteRags(Integer tagsType, Long id, Integer tagsId) {
        switch (tagsType) {
            case 0:
                User user = userRepository.findOne(id);
                if (user != null && !StringUtils.isEmpty(tagsId) && user.getTags() != null) {
                    Iterator iterator = user.getTags().iterator();
                    while (iterator.hasNext()) {
                        Tag tag = (Tag) iterator.next();
                        if (tag.getId().equals(tagsId)) iterator.remove();
                    }
                    userRepository.save(user);
                }
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }
}
