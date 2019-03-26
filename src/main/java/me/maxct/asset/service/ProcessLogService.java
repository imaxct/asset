package me.maxct.asset.service;

import me.maxct.asset.domain.ProcessLog;
import me.maxct.asset.dto.Msg;

/**
 * @author imaxct
 * 2019-03-25 16:44
 */
public interface ProcessLogService {

    Msg processStep(ProcessLog processLog);
}
