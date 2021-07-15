package com.example.demo.service;

import com.example.demo.bo.LoginBo;
import com.example.demo.entry.Employee;
import com.example.demo.entry.OldPerson;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.util.ResultReturn;
import com.example.demo.util.ResultReturnUtil;
import com.example.demo.util.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private RedisService redisService;

    public ResultReturn add(Employee employee){

        if (employee.getUsername() == null || employee.getUsername().equals("")){
            return ResultReturnUtil.fail("名字不能为空");
        }

        if (employee.getPassword() == null || employee.getPassword().equals("")){
            return ResultReturnUtil.fail("密码不能为空");
        }

        Employee temp = employeeMapper.selectByUserName(employee.getUsername());
        if (temp != null){
            return ResultReturnUtil.fail("该员工信息已存在");
        }

        employeeMapper.add(employee);
        return ResultReturnUtil.success("添加成功");
    }

    public ResultReturn update(Employee employee){
        if (employee.getUsername() == null || employee.getUsername().equals("")){
            return ResultReturnUtil.fail("名字不能为空");
        }

        Employee temp = employeeMapper.selectByUserName(employee.getUsername());
        if (temp == null){
            return ResultReturnUtil.fail("该员工不存在");
        }

        employeeMapper.update(employee);
        return ResultReturnUtil.success("修改成功");
    }

    public ResultReturn delete(int id){

        Employee employee = employeeMapper.selectById(id);
        if (employee == null)
            return ResultReturnUtil.fail("该员工不存在");

        employeeMapper.delete(id);
        return ResultReturnUtil.success("删除成功");
    };

    public ResultReturn getAll(){
        List<Employee> employees = employeeMapper.getAll();
        return ResultReturnUtil.success("查询成功", employees);
    }

    public ResultReturn login(LoginBo loginBo){
        Employee employee = employeeMapper.selectByUserName(loginBo.getUsername());

        if (employee == null)
            return ResultReturnUtil.fail("no such user!");

        if (employee.getPassword().equals(loginBo.getPassword())){
            String token = TokenUtil.getToken();
//            System.out.println(token + " " + loginBo.toString());
            redisService.setString(token, loginBo.getUsername(),3600L);
            System.out.println(redisService.get(token));
            return ResultReturnUtil.success("employee login success!", token);

        }
        else
            return ResultReturnUtil.fail("password error!");
    }

    public ResultReturn queryByUsername(String username){
        Employee employee = employeeMapper.selectByUserName(username);
        if (employee == null)
            return ResultReturnUtil.fail("没有符合的信息");

        return ResultReturnUtil.success("查询成功", employee);
    }

}
