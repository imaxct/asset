package me.maxct.asset.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.maxct.asset.domain.Process;
import me.maxct.asset.domain.Step;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.dto.ProcessVO;
import me.maxct.asset.mapper.ProcessDao;
import me.maxct.asset.mapper.StepDao;
import me.maxct.asset.service.ProcessService;

/**
 * @author imaxct
 * 2019-03-25 21:39
 */
@Service
public class ProcessServiceImpl implements ProcessService {
    private final ProcessDao processDao;
    private final StepDao    stepDao;

    @Override
    @Transactional
    public Msg addProcess(Process process, List<Step> steps) {
        process = processDao.saveAndFlush(process);
        for (Step step : steps) {
            step.setProcessId(process.getId());
            step.setGmtCreate(LocalDateTime.now());
            step.setGmtModified(LocalDateTime.now());
        }
        stepDao.saveAll(steps);
        Step curStep;
        Step nextStep;
        for (int i = 0; i < steps.size(); ++i) {
            curStep = steps.get(i);
            if (i < steps.size() - 1) {
                nextStep = steps.get(i + 1);
                curStep.setNextStepId(nextStep.getId());
            }
        }
        stepDao.saveAll(steps);
        process.setFirstStepId(steps.get(0).getId());
        processDao.saveAndFlush(process);
        return Msg.ok(null);
    }

    @Override
    public Msg getAllProcess() {
        List<Process> processList = processDao.findAll();
        List<Step> stepList = stepDao.findAll();
        Map<Long, Process> processMap = processList.stream()
            .collect(Collectors.toMap(Process::getId, Function.identity(), (o, n) -> n));
        Map<Long, List<Step>> stepMap = stepList.stream()
            .collect(Collectors.groupingBy(Step::getProcessId));
        List<ProcessVO> result = new ArrayList<>();
        ProcessVO vo;
        for (Map.Entry<Long, Process> entry : processMap.entrySet()) {
            vo = new ProcessVO();
            vo.setProcess(entry.getValue());
            vo.setSteps(stepMap.get(entry.getKey()));
            result.add(vo);
        }
        return Msg.ok(result);
    }

    @Autowired
    public ProcessServiceImpl(ProcessDao processDao, StepDao stepDao) {
        this.processDao = processDao;
        this.stepDao = stepDao;
    }
}
