package me.maxct.asset.service;

import me.maxct.asset.domain.Step;
import me.maxct.asset.dto.Msg;

/**
 * @author imaxct
 * 2019-05-14 18:44
 */
public interface StepService {
    Msg updateStep(Step step);
}
