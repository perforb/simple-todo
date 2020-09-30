package com.example.todo.infrastructure.task

import com.example.todo.domain.task.Task
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface TaskMapper {

  @Select("select id, title, done, created_at from task where id = #{id}")
  fun findById(id: Long): Task?

  @Select("select id, title, done, created_at from task")
  fun findAll(): List<Task>

  @Options(useGeneratedKeys = true)
  @Insert("insert into task (title, done, created_at) values (#{title}, #{done}, #{createdAt})")
  fun insert(todo: Task)

  @Update("update task set done = 1 where id = #{id}")
  fun finish(id: Long)

  @Delete("delete from task where id = #{id}")
  fun deleteById(id: Long)
}
