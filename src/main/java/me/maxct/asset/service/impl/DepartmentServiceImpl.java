package me.maxct.asset.service.impl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import me.maxct.asset.domain.Department;
import me.maxct.asset.dto.DepListVO;
import me.maxct.asset.dto.DepVO;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.mapper.DepartmentDao;
import me.maxct.asset.service.DepartmentService;
import me.maxct.asset.util.StringUtil;

/**
 * @author imaxct
 * 2019-04-03 15:59
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentDao departmentDao;

    @Override
    public Msg createDep(Department department) {
        department.setGmtModified(LocalDateTime.now());
        if (department.getParentDep() != null && department.getParentDep() != 0L) {
            Optional<Department> parentDepOpt = departmentDao.findById(department.getParentDep());
            Assert.isTrue(parentDepOpt.isPresent(), "父部门不存在");
            Department parentDep = parentDepOpt.get();
            String newDepId = parentDep.getDepId() + StringUtil.random4BitStr().substring(0, 2);
            department.setDepId(newDepId);
        } else {
            department.setDepId(StringUtil.random4BitStr());
        }
        department.setGmtCreate(LocalDateTime.now());
        department.setGmtModified(LocalDateTime.now());
        return Msg.ok(departmentDao.saveAndFlush(department));
    }

    @Override
    public Msg updateDep(Department department) {
        Optional<Department> dbDepOpt = departmentDao.findById(department.getId());
        Assert.isTrue(dbDepOpt.isPresent(), "记录不存在");
        Department dbDep = dbDepOpt.get();

        department.setGmtModified(LocalDateTime.now());
        if (department.getParentDep() != null && department.getParentDep() != 0L
            && !Objects.equals(department.getParentDep(), dbDep.getParentDep())) {

            Optional<Department> parentDepOpt = departmentDao.findById(department.getParentDep());
            Assert.isTrue(parentDepOpt.isPresent(), "父部门不存在.");
            Department parentDep = parentDepOpt.get();

            String newDepId = parentDep.getDepId() + StringUtil.random4BitStr().substring(0, 2);
            dbDep.setDepId(newDepId);
        }
        dbDep.setGmtModified(LocalDateTime.now());
        dbDep.setName(department.getName());
        return Msg.ok(departmentDao.saveAndFlush(dbDep));
    }

    @Override
    public Msg listDep() {
        List<Department> list = departmentDao.findAll();

        List<DepVO> depVOList = list.stream().map(DepVO::parse).collect(Collectors.toList());

        Map<Long, DepVO> depMap = depVOList.stream()
            .collect(Collectors.toMap(DepVO::getId, Function.identity(), (o, n) -> n));

        for (Department department : list) {
            if (department.getParentDep() != null) {
                DepVO vo = depMap.get(department.getParentDep());
                if (vo.getChildren() == null) {
                    vo.setChildren(new ArrayList<>());
                }
                vo.getChildren().add(depMap.get(department.getId()));
            }
        }
        for (Department department : list) {
            if (department.getParentDep() != null) {
                depMap.remove(department.getId());
            }
        }

        DepVO depVO = new DepVO();
        depVO.setId(0L);
        depVO.setLabel("总公司");
        depVO.setChildren(new ArrayList<>(depMap.values()));

        DepListVO listVO = new DepListVO();
        listVO.setList(list.stream().map(DepVO::parse).collect(Collectors.toList()));
        listVO.setTree(depVO);

        return Msg.ok(listVO);
    }

    @Override
    @Transactional
    public Msg deleteDep(Long id) {
        Department dep = new Department();
        dep.setParentDep(id);

        if (departmentDao.exists(Example.of(dep))) {
            return Msg.err("当前部门下存在子部门, 无法删除");
        }
        departmentDao.deleteById(id);
        return Msg.ok(null);
    }

    public DepartmentServiceImpl(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }
}
