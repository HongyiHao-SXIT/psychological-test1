package com.macro.mall.tiny;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MallTinyApplication {

    public static void main(String[] args) {
//        ScheduleUtils.schedule(TriggerUtils.createTriggerAtNowWithCronTab("1", "2", new TestTask(), "0/1 * * * * ? "));
        SpringApplication.run(MallTinyApplication.class, args);
    }

}
