package me.maxct.asset.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.maxct.asset.domain.Department;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.mapper.DepartmentDao;
import me.maxct.asset.service.DepartmentService;

/**
 * @author imaxct
 * 2019-04-03 15:59
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentDao departmentDao;

    @Override
    public Msg saveDep(Department department) {
        department.setGmtModified(LocalDateTime.now());
        return Msg.ok(departmentDao.saveAndFlush(department));
    }

    @Override
    public Msg listDep() {
        return Msg.ok(departmentDao.findAll());
    }

    @Override
    @Transactional
    public Msg deleteDep(Long id) {
        departmentDao.deleteById(id);
        return Msg.ok(null);
    }

    public DepartmentServiceImpl(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }
}
