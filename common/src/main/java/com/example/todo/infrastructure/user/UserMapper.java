package com.example.todo.infrastructure.user;

import com.example.todo.domain.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Select("select id, user_name, user_id, password, created_at from user where id = #{id}")
  User findById(Long id);

  @Select("select id, user_name, user_id, password, created_at from user where user_id = #{userId}")
  User findByUserId(String userId);
}
