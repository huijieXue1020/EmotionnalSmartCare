package com.example.demo.mapper;

import com.example.demo.entry.Employee;
import com.example.demo.entry.OldPerson;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    @Select("select * from employee_info where username=#{username}")
    Employee selectByUserName(String username);

    @Select("select * from employee_info where id=#{id}")
    Employee selectById(int id);

    @Insert("insert into employee_info(ORG_ID, CLIENT_ID, username, password, gender,phone,id_card,birthday,hire_date, resign_date, imgset_dir," +
            "profile_photo, DESCRIPTION, ISACTIVE, CREATED, CREATEBY, UPDATED, UPDATEBY, REMOVE) " +
            "values(1,1,#{username}, #{password}, #{gender},#{phone},#{id_card},#{birthday}, #{hire_date}, #{resign_date}, #{imgSetDir}," +
            " #{profilePhotoDir}, #{description}, #{isActive},#{createTime}, #{createBy}, #{updateTime}," +
            " #{updateBy},#{remove})")
    void add(Employee employee);

    @Update("update employee_info set username=#{username}, password=#{password}, gender=#{gender}, phone=#{phone}, id_card=#{id_card}," +
            "birthday=#{birthday}, hire_date=#{hire_date}, resign_date=#{resign_date}, imgset_dir=#{imgSetDir}, " +
            "profile_photo=#{profilePhotoDir}, DESCRIPTION=#{description}, ISACTIVE=#{isActive}," +
            "CREATED=#{createTime}, CREATEBY=#{createBy}, UPDATED=#{updateTime}, UPDATEBY=#{updateBy}, REMOVE=#{remove} " +
            "where username=#{username}")
    void update(Employee employee);

    @Delete("delete from employee_info where id=#{id}")
    void delete(int id);

    @Select("select id, username, gender,phone,id_card,birthday,hire_date, resign_date, imgset_dir," +
            "profile_photo, DESCRIPTION, ISACTIVE, CREATED, CREATEBY, UPDATED, UPDATEBY, REMOVE from employee_info")
    List<Employee> getAll();
}
