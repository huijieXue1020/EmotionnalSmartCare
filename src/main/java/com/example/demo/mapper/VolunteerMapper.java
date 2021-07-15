package com.example.demo.mapper;

import com.example.demo.entry.Employee;
import com.example.demo.entry.Volunteer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VolunteerMapper {

    @Select("select * from volunteer_info where name=#{username}")
    Volunteer selectByUserName(String username);

    @Select("select * from volunteer_info where id=#{id}")
    Volunteer selectById(int id);

    @Insert("insert into volunteer_info(ORG_ID, CLIENT_ID, name, password, gender,phone,id_card,birthday,checkin_date, checkout_date, imgset_dir," +
            "profile_photo, DESCRIPTION, ISACTIVE, CREATED, CREATEBY, UPDATED, UPDATEBY, REMOVE) " +
            "values(1,1,#{name}, #{password}, #{gender},#{phone},#{id_card},#{birthday}, #{checkin_date}, #{checkout_date}, #{imgSetDir}," +
            " #{profilePhotoDir}, #{description}, #{isActive},#{createTime}, #{createBy}, #{updateTime}," +
            " #{updateBy},#{remove})")
    void add(Volunteer volunteer);

    @Update("update volunteer_info set name=#{name}, password=#{password},gender=#{gender}, phone=#{phone}, id_card=#{id_card}," +
            "birthday=#{birthday}, checkin_date=#{checkin_date}, checkout_date=#{checkout_date}, imgset_dir=#{imgSetDir}, " +
            "profile_photo=#{profilePhotoDir}, DESCRIPTION=#{description}, ISACTIVE=#{isActive}," +
            "CREATED=#{createTime}, CREATEBY=#{createBy}, UPDATED=#{updateTime}, UPDATEBY=#{updateBy}, REMOVE=#{remove} " +
            "where name=#{name}")
    void update(Volunteer volunteer);

    @Delete("delete from volunteer_info where ID=#{id}")
    void delete(int id);

    @Select("select id, name,gender,phone,id_card,birthday,checkin_date, checkout_date, imgset_dir," +
            "profile_photo, DESCRIPTION, ISACTIVE, CREATED, CREATEBY, UPDATED, UPDATEBY, REMOVE from volunteer_info")
    List<Volunteer> getAll();
}
