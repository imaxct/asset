package me.maxct.asset.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import me.maxct.asset.domain.Department;
import me.maxct.asset.domain.Role;
import me.maxct.asset.domain.User;
import me.maxct.asset.dto.DepUserVO;
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

        Assert.isTrue(password.equals(user.getPassword()), "密码错误");

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
        vo.setDepId(user.getDepId());
        return Msg.ok(vo);
    }

    @Override
    public Msg listUser(int pageNo, int size) {
        return Msg.ok(userDao.findAll(PageRequest.of(pageNo, size)));
    }

    @Override
    public Msg saveUser(User user) {
        user.setGmtModified(LocalDateTime.now());
        userDao.saveAndFlush(user);
        return Msg.ok(null);
    }

    @Override
    public User getUser(Long id) {
        return userDao.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Msg getDepUser() {
        List<Department> departments = departmentDao.findAll();
        List<User> users = userDao.findAll();
        List<DepUserVO> result = new ArrayList<>();
        for (Department department : departments) {
            DepUserVO vo = new DepUserVO();
            vo.setName(department.getName());
            vo.setValue(String.format("g%d", department.getId()));
            vo.setParent("");
            result.add(vo);
        }
        for (User user : users) {
            DepUserVO vo = new DepUserVO();
            vo.setName(user.getName());
            vo.setValue(String.valueOf(user.getId()));
            vo.setParent(String.format("g%d", user.getDepId()));
            result.add(vo);
        }
        return Msg.ok(result);
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
