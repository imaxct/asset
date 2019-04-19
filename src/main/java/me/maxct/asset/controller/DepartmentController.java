package me.maxct.asset.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import me.maxct.asset.constant.AppConst;
import me.maxct.asset.domain.Department;
import me.maxct.asset.domain.User;
import me.maxct.asset.dto.DepDO;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.interceptor.AuthCheck;
import me.maxct.asset.service.DepartmentService;

/**
 * @author imaxct
 * 2019-04-03 15:57
 */
@RestController
@RequestMapping("/Dep")
public class DepartmentController {
    private final DepartmentService departmentService;

    @AuthCheck
    @GetMapping("/list")
    public Msg listDep(HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        return departmentService.listDep();
    }

    @AuthCheck
    @PostMapping("/add")
    public Msg addDep(@RequestBody DepDO depDO, HttpServletRequest request) {
        Assert.isTrue(!StringUtils.isEmpty(depDO.getName()), "部门名称为空");

        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");

        Department department = new Department();
        department.setName(depDO.getName());
        if (depDO.getParentDep() != null && depDO.getParentDep() != 0L) {
            department.setParentDep(depDO.getParentDep());
        }
        return departmentService.createDep(department);
    }

    @AuthCheck
    @PostMapping("/update")
    public Msg updateDep(@RequestBody DepDO depDO, HttpServletRequest request) {
        Assert.notNull(depDO.getId(), "部门id为空");
        Assert.isTrue(!StringUtils.isEmpty(depDO.getName()), "部门名称为空");

        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");

        Department department = new Department();
        if (depDO.getParentDep() != null) {
            department.setId(depDO.getParentDep() == 0L ? null : depDO.getParentDep());
        }
        department.setId(depDO.getId());
        department.setName(depDO.getName());
        return departmentService.updateDep(department);
    }

    @AuthCheck
    @PostMapping("/del")
    public Msg deleteDep(@RequestBody DepDO depDO, HttpServletRequest request) {
        Assert.notNull(depDO.getId(), "部门id为空");
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        return departmentService.deleteDep(depDO.getId());
    }

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
}
