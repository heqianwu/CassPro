package com.hqw.pro.kspboot.repos;

import com.hqw.pro.kspboot.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepos extends JpaRepository<User, String> {

    /**
     * 通过用户名相等查询
     *
     * @param userName 用户名
     * @return
     */
    List<User> findByUserName(String userName);

    /**
     * 通过名字like查询
     *
     * @param userName 用户名
     * @return
     */
    List<User> findByUserNameLike(String userName);

    /**
     * 通过用户名和手机号码查询
     *
     * @param userName 用户名
     * @param mobileNo 手机号码
     * @return
     */
    User findByUserNameAndMobileNo(String userName, String mobileNo);

    /**
     * 根据用户类型，分页查询
     *
     * @param userType 用户类型
     * @param pageable
     * @return
     */
    Page<User> findByUserType(Integer userType, Pageable pageable);

    /**
     * 根据用户名，排序查询
     *
     * @param userName 用户名
     * @param sort
     * @return
     */
    List<User> findByUserName(String userName, Sort sort);

}
