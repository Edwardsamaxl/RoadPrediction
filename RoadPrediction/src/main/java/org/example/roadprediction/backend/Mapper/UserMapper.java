package org.example.roadprediction.backend.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.roadprediction.backend.Entity.User;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户记录
     *
     * @param username 用户名
     * @return 查询到的用户对象，如果不存在则返回 null
     */
    User selectByUsername(String username);

    /**
     * 根据用户名和密码查询用户记录
     *
     * @param username 用户名
     * @param password 密码（已加密）
     * @return 查询到的用户对象，如果用户名或密码不匹配则返回 null
     */
    User selectByUsernameAndPassword(String username, String password);

    /**
     * 插入新用户记录
     *
     * @param user 新用户对象
     * @return 插入操作影响的行数
     */
    int insert(User user);

    /**
     * 更新用户密码
     *
     * @param user 用户对象，包含新的密码
     * @return 更新操作影响的行数
     */
    int updatePassword(User user);

    /**
     * 根据用户ID查询用户记录
     *
     * @param id 用户ID
     * @return 查询到的用户对象，如果不存在则返回 null
     */
    User selectById(int id);

    /**
     * 删除用户记录
     *
     * @param id 用户ID
     * @return 删除操作影响的行数
     */
    int deleteById(int id);
}