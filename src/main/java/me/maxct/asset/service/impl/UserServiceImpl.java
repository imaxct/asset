package me.maxct.asset.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import me.maxct.asset.domain.Department;
import me.maxct.asset.domain.Role;
import me.maxct.asset.domain.User;
import me.maxct.asset.dto.LoginVO;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.dto.RoleVO;
import me.maxct.asset.mapper.DepartmentDao;
import me.maxct.asset.mapper.RoleDao;
import me.maxct.asset.mapper.UserDao;
import me.maxct.asset.security.JwtUtil;
import me.maxct.asset.service.UserService;

/**
 * @author imaxct
 * 2019-03-27 21:40
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserDao       userDao;
    private final RoleDao       roleDao;
    private final DepartmentDao departmentDao;
    private final JwtUtil       jwtUtil;

    @Override
    public Msg<LoginVO> login(String username, String password) {
        Optional<User> userOptional = userDao.findByUsername(username);
        Assert.isTrue(userOptional.isPresent(), "用户不存在");
        User user = userOptional.get();

        Optional<Role> roleOptional = roleDao.findById(user.getRoleId());
        Assert.isTrue(roleOptional.isPresent(), "角色不存在");

        String token = jwtUtil.sign(user);
        Optional<Department> departmentOptional = departmentDao.findById(user.getDepId());
        Assert.isTrue(departmentOptional.isPresent(), "记录不存在");
        LoginVO vo = new LoginVO();
        vo.setId(user.getId());
        vo.setToken(token);
        vo.setDepName(departmentOptional.get().getName());
        vo.setName(user.getName());
        vo.setUsername(username);
        vo.setExpireSecond(jwtUtil.tokenExpireMinute * 60L);
        vo.setRole(new RoleVO(roleOptional.get()));
        return Msg.ok(vo);
    }

    @Autowired
    public UserServiceImpl(UserDao userDao, JwtUtil jwtUtil, RoleDao roleDao,
                           DepartmentDao departmentDao) {
        this.userDao = userDao;
        this.jwtUtil = jwtUtil;
        this.roleDao = roleDao;
        this.departmentDao = departmentDao;
    }
}