package me.maxct.asset.service;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import me.maxct.asset.domain.Step;
import me.maxct.asset.enumerate.PropertyStatus;
import me.maxct.asset.mapper.StepDao;

/**
 * @author imaxct
 * 2019-03-26 22:22
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProcessServiceTest {
    @Autowired
    private StepDao stepDao;

    @Test
    public void testListInsert() {
        List<Step> steps = Lists.newArrayList();
        for (int i = 0; i < 4; ++i) {
            Step step = new Step();
            step.setProcessId(1L);
            step.setRoleRequired("null");
            step.setStatusRequired(PropertyStatus.IDLE);
            step.setName(String.format("step-%d", i));
            step.setGmtCreate(LocalDateTime.now());
            step.setGmtModified(LocalDateTime.now());
            steps.add(step);

        }
        stepDao.saveAll(steps);
        for (Step step : steps) {
            System.out.println(" " + step.getId() + " " + step.getName());
        }
    }
}
