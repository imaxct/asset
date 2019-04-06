package me.maxct.asset.service;

import java.util.List;

import me.maxct.asset.domain.Process;
import me.maxct.asset.domain.Step;
import me.maxct.asset.dto.Msg;

/**
 * @author imaxct
 * 2019-03-24 21:17
 */
public interface ProcessService {

    /**
     * 添加一种业务流程
     * @param process
     * @param steps
     * @return
     */
    Msg addProcess(Process process, List<Step> steps);


    /**
     * 获取所有业务流程
     * @return
     */
    Msg getAllProcess();

    Msg getById(Long id);
}
