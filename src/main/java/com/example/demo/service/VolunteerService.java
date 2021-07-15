package com.example.demo.service;

import com.example.demo.bo.LoginBo;
import com.example.demo.entry.Employee;
import com.example.demo.entry.Volunteer;
import com.example.demo.mapper.VolunteerMapper;
import com.example.demo.util.ResultReturn;
import com.example.demo.util.ResultReturnUtil;
import com.example.demo.util.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VolunteerService {

    @Resource
    private VolunteerMapper volunteerMapper;

    @Resource
    private RedisService redisService;

    public ResultReturn login(LoginBo loginBo){
        Volunteer volunteer = volunteerMapper.selectByUserName(loginBo.getUsername());

        if (volunteer == null)
            return ResultReturnUtil.fail("no such user!");

        if (volunteer.getPassword().equals(loginBo.getPassword())){
            String token = TokenUtil.getToken();
//            System.out.println(token + " " + loginBo.toString());
            redisService.setString(token, loginBo.getUsername(),3600L);
            System.out.println(redisService.get(token));
            return ResultReturnUtil.success("volunteer login success!", token);

        }
        else
            return ResultReturnUtil.fail("password error!");
    }

    public ResultReturn add(Volunteer volunteer){
        if (volunteer.getName() == null || "".equals(volunteer.getName())){
            return ResultReturnUtil.fail("名字不能为空");
        }

        if (volunteer.getPassword() == null || "".equals(volunteer.getPassword())){
            return ResultReturnUtil.fail("密码不能为空");
        }

        Volunteer temp = volunteerMapper.selectByUserName(volunteer.getName());
        if (temp != null)
            return ResultReturnUtil.fail("该义工信息已存在");

        volunteerMapper.add(volunteer);
        return ResultReturnUtil.success("添加成功");
    }

    public ResultReturn update(Volunteer volunteer){
        if (volunteer.getName() == null || "".equals(volunteer.getName())){
            return ResultReturnUtil.fail("名字不能为空");
        }

        Volunteer temp = volunteerMapper.selectByUserName(volunteer.getName());
        if (temp == null)
            return ResultReturnUtil.fail("该义工信息不存在");

        volunteerMapper.update(volunteer);
        return ResultReturnUtil.success("修改成功");
    }

    public ResultReturn delete(int id){

        Volunteer volunteer = volunteerMapper.selectById(id);
        if (volunteer == null){
            return ResultReturnUtil.fail("该义工信息不存在");
        }

        volunteerMapper.delete(id);

        return ResultReturnUtil.success("删除成功");
    }

    public ResultReturn getAll(){

        List<Volunteer> volunteers = volunteerMapper.getAll();
        return ResultReturnUtil.success("查询成功", volunteers);
    }

    public ResultReturn queryByUsername(String username){

        Volunteer volunteer = volunteerMapper.selectByUserName(username);
        if (volunteer == null)
            return ResultReturnUtil.fail("没有符合的信息");

        return ResultReturnUtil.success("查询成功", volunteer);
    }
}
