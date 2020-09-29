package com.example.todo.infrastructure.task;

import com.example.todo.domain.task.Task;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TaskMapper {

  @Select("select id, title, done, created_at from task where id = #{id}")
  Task findById(Long id);

  @Select("select id, title, done, created_at from task")
  List<Task> findAll();

  @Options(useGeneratedKeys = true)
  @Insert("insert into task (title, done, created_at) values (#{title}, #{done}, #{createdAt})")
  void insert(Task todo);

  @Update("update task set done = 1 where id = #{id}")
  void finish(Long id);

  @Delete("delete from task where id = #{id}")
  void deleteById(Long id);
}
