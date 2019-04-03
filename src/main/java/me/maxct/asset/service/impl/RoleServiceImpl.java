package me.maxct.asset.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import me.maxct.asset.domain.Role;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.mapper.RoleDao;
import me.maxct.asset.service.RoleService;

/**
 * @author imaxct
 * 2019-04-02 12:02
 */
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    @Override
    public Msg listRole() {
        return Msg.ok(roleDao.findAll());
    }

    @Override
    @Transactional
    public Msg deleteRole(Long id) {
        roleDao.deleteById(id);
        return Msg.ok(null);
    }

    @Override
    public Msg saveRole(Role role) {
        Assert.notNull(role, "参数不能为空");
        Assert.isTrue(!StringUtils.isEmpty(role.getName()), "角色名为空");
        Assert.isTrue(!StringUtils.isEmpty(role.getRoleName()), "roleName不能为空");
        Assert.isTrue(!StringUtils.isEmpty(role.getAuthorizedMapping()), "url不能为空");
        role.setGmtCreate(LocalDateTime.now());
        role.setGmtModified(LocalDateTime.now());
        return Msg.ok(roleDao.saveAndFlush(role));
    }

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
}
