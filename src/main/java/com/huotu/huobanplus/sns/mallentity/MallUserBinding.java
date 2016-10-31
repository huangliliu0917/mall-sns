package com.huotu.huobanplus.sns.mallentity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.Description;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/10/31.
 */
@Entity
@Cacheable(value = false)
@Getter
@Setter
@Table(name = "Hot_UserOAuthBinding")
@Description("用户微信关联信息")
public class MallUserBinding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UOB_ID")
    private Long id;
    @OneToOne
    @JoinColumn(name = "UOB_UB_UserID")
    @Description("关联用户信息")
    private MallUser userInfo;
    @Column(name = "UOB_Identification", length = 50)
    private String openId;
    @Column(name = "UOB_UnionId", length = 50)
    private String unionId;
}
