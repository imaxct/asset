package me.maxct.asset.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import me.maxct.asset.domain.Step;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.mapper.StepDao;
import me.maxct.asset.service.StepService;

/**
 * @author imaxct
 * 2019-05-14 18:44
 */
@Service
public class StepServiceImpl implements StepService {
    private final StepDao stepDao;

    @Override
    public Msg updateStep(Step step) {
        Optional<Step> stepOptional = stepDao.findById(step.getId());
        Assert.isTrue(stepOptional.isPresent(), "参数错误");
        Step dbStep = stepOptional.get();
        if (!StringUtils.isEmpty(step.getRoleRequired())) {
            dbStep.setRoleRequired(step.getRoleRequired());
        }
        if (step.getStatusRequired() != null) {
            dbStep.setStatusRequired(step.getStatusRequired());
        }
        dbStep.setGmtModified(LocalDateTime.now());
        return Msg.ok(stepDao.saveAndFlush(dbStep));
    }

    public StepServiceImpl(StepDao stepDao) {
        this.stepDao = stepDao;
    }
}
