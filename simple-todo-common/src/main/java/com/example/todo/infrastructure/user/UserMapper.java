package com.example.todo.infrastructure.user;

import com.example.todo.domain.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Results({
    @Result(id = true, column = "id", property = "id"),
    @Result(column = "user_name", property = "userName"),
    @Result(column = "user_id", property = "userId"),
    @Result(column = "password", property = "hashedPassword"),
    @Result(column = "created_at", property = "createdAt")
  })
  @Select("select id, user_name, user_id, password, created_at from user where id = #{id}")
  User findById(Long id);

  @Results({
    @Result(id = true, column = "id", property = "id"),
    @Result(column = "user_name", property = "userName"),
    @Result(column = "user_id", property = "userId"),
    @Result(column = "password", property = "hashedPassword"),
    @Result(column = "created_at", property = "createdAt")
  })
  @Select("select id, user_name, user_id, password, created_at from user where user_id = #{userId}")
  User findByUserId(String userId);
}
