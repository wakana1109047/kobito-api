package com.example.kobitoapi.mapper;

import com.example.kobitoapi.dto.Kobito;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KobitoMapper {

    @Select("SELECT id,name,description,image_url,created_at FROM kobito ORDER BY created_at DESC")
    List<Kobito> findAll();

    @Insert("INSERT INTO kobito (name,description,image_url) VALUES (#{name},#{description},#{imageUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Kobito kobito);

    @Select("SELECT id,name,description,image_url,created_at FROM kobito WHERE id = #{id}")
    Kobito findById(Long id);

    @Delete("DELETE FROM kobito WHERE id = #{id}")
    int delete(long id);

    @Update("UPDATE kobito SET name = #{name}, description = #{description}, image_url = #{imageUrl} WHERE id = #{id}")
    int update(Kobito kobito);

    @Select("SELECT id,name,description,image_url,created_at FROM kobito WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Kobito> findByName(String name);
}
