package me.maxct.asset.service;

import me.maxct.asset.domain.Process;
import me.maxct.asset.domain.Step;
import me.maxct.asset.domain.Ticket;
import me.maxct.asset.domain.User;
import me.maxct.asset.dto.Msg;

/**
 * @author imaxct
 * 2019-03-25 16:44
 */
public interface ProcessLogService {

    /**
     * 处理一个步骤
     * @param ticket
     * @param process
     * @param step
     * @param user
     * @return
     */
    Msg processStep(Ticket ticket, Process process, Step step, User user);
}
