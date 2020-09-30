package com.example.todo.infrastructure.user

import com.example.todo.domain.user.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface UserMapper {

  @Select("select id, user_name, user_id, password, created_at from user where id = #{id}")
  fun findById(id: Long): User?

  @Select("select id, user_name, user_id, password, created_at from user where user_id = #{userId}")
  fun findByUserId(userId: String): User?
}
