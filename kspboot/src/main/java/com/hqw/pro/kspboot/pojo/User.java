package com.hqw.pro.kspboot.pojo;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.*;


@Entity
public class User implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键.
     */
    @Id
    @GeneratedValue
    private String id;

    /**
     * 用户名.
     */
    private String userName = "";

    /**
     * 手机号码.
     */
    private String mobileNo = "";

    /**
     * 邮箱.
     */
    private String email = "";

    /**
     * 密码.
     */
    private String password = "";

    /**
     * 用户类型.
     */
    private Integer userType = 0;

    /**
     * 注册时间.
     */
    private Date registerTime = new Date();

    /**
     * 所在区域.
     */
    private String region = "";

    /**
     * 是否有效 0 有效 1 无效.
     */
    private Integer validity = 0;

    /**
     * 头像.
     */
    private String headPortrait = "";
}