package me.maxct.asset.service;

import me.maxct.asset.domain.Department;
import me.maxct.asset.dto.Msg;

/**
 * @author imaxct
 * 2019-04-01 16:57
 */
public interface DepartmentService {
    Msg createDep(Department department);

    Msg updateDep(Department department);

    Msg listDep();

    Msg deleteDep(Long id);
}
