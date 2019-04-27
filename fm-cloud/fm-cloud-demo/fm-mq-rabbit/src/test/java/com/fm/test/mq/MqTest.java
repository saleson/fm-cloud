package com.fm.test.mq;

import com.fm.mq.MqRabbitApplication;
import com.fm.mq.component.Sender;
import com.fm.mq.domain.SubMsg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles({"dev"})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MqRabbitApplication.class)
public class MqTest {

    @Autowired
    private Sender sender;

    @Test
    public void hello() {
        SubMsg msg = new SubMsg("sub", "d", "t");
        sender.send(msg);
    }

    @Test
    public void output() {
        SubMsg msg = new SubMsg("sub", "d", "t");
//        sender.sendOutput(msg);
    }
}
