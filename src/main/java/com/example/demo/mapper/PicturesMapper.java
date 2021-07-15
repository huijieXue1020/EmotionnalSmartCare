package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface PicturesMapper {

    @Insert("insert into pic_dirs(event_id, dir) values(#{eventId}, #{dir})")
    void savePictures(String eventId, String dir);

    @Select("select dir from pic_dirs where dir = #{dir}")
    String getPictureByDir(String dir);
}
