package com.example.demo.service;

import com.example.demo.bo.LoginBo;
import com.example.demo.entry.OldPerson;
import com.example.demo.entry.SysUser;
import com.example.demo.mapper.OldPersonMapper;
import com.example.demo.util.ResultReturn;
import com.example.demo.util.ResultReturnUtil;
import com.example.demo.util.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class OldPersonService {

    @Resource
    private OldPersonMapper oldPersonMapper;

    @Resource
    private RedisService redisService;

    public ResultReturn add(OldPerson oldPerson){

        if (oldPerson.getUsername()==null||oldPerson.getUsername().equals(""))
            return ResultReturnUtil.fail("名字不能为空");

        if (oldPerson.getPassword()==null||oldPerson.getPassword().equals(""))
            return ResultReturnUtil.fail("密码不能为空");

        OldPerson temp = oldPersonMapper.selectByUserName(oldPerson.getUsername());
        if (temp!=null){
            return ResultReturnUtil.fail("该位老人信息已存在");
        }

        oldPersonMapper.add(oldPerson);

        return ResultReturnUtil.success("添加成功");
    }

    public ResultReturn update(OldPerson oldPerson){
        if (oldPerson.getUsername()==null||oldPerson.getUsername().equals(""))
            return ResultReturnUtil.fail("名字不能为空");

        OldPerson temp = oldPersonMapper.selectByUserName(oldPerson.getUsername());
        if (temp==null){
            return ResultReturnUtil.fail("该位老人信息不存在");
        }
        oldPersonMapper.update(oldPerson);
        return ResultReturnUtil.success("修改成功");
    }

    public ResultReturn delete(int id){

        OldPerson temp = oldPersonMapper.selectByID(id);
        if (temp==null){
            return ResultReturnUtil.fail("该位老人不存在");
        }

        oldPersonMapper.delete(id);
        return ResultReturnUtil.success("删除成功");
    }

    public ResultReturn getAll(){

        List<OldPerson> lists = oldPersonMapper.getAll();
        return ResultReturnUtil.success("查询成功！",lists);
    }

    public ResultReturn login(LoginBo loginBo){
        OldPerson oldPerson = oldPersonMapper.selectByUserName(loginBo.getUsername());

        if (oldPerson == null)
            return ResultReturnUtil.fail("no such user!");

        if (oldPerson.getPassword().equals(loginBo.getPassword())){
            String token = TokenUtil.getToken();
//            System.out.println(token + " " + loginBo.toString());
            redisService.setString(token, loginBo.getUsername(),3600L);
            System.out.println(redisService.get(token));
            return ResultReturnUtil.success("older login success!", token);

        }
        else
            return ResultReturnUtil.fail("password error!");
    }

    public ResultReturn queryByUsername(String username){

        OldPerson oldPerson = oldPersonMapper.selectByUserName(username);
        if (oldPerson == null)
            return ResultReturnUtil.fail("没有符合的信息");

        return ResultReturnUtil.success("查询成功", oldPerson);
    }
}
