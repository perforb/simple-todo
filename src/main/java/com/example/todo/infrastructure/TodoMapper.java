package com.example.todo.infrastructure;

import com.example.todo.domain.Todo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TodoMapper {

  @Results({
    @Result(id = true, column = "id", property = "id"),
    @Result(column = "title", property = "title"),
    @Result(column = "done", property = "done"),
    @Result(column = "created_at", property = "createdAt")
  })
  @Select("select id, title, done, created_at from todo where id = #{id}")
  Todo findById(int id);

  @Results({
    @Result(id = true, column = "id", property = "id"),
    @Result(column = "title", property = "title"),
    @Result(column = "done", property = "done"),
    @Result(column = "created_at", property = "createdAt")
  })
  @Select("select id, title, done, created_at from todo")
  List<Todo> findAll();

  @Options(useGeneratedKeys = true)
  @Insert("insert into todo (title, done, created_at) values (#{title}, #{done}, #{createdAt})")
  void insert(Todo todo);

  @Update("update todo set done = 1 where id = #{id}")
  void finish(int id);

  @Delete("delete from todo where id = #{id}")
  void deleteById(int id);
}
