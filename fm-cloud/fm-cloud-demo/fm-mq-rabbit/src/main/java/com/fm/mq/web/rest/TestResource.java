package com.fm.mq.web.rest;

import com.fm.mq.component.Sender;
import com.fm.mq.domain.Msg;
import com.fm.mq.domain.SubMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by saleson on 2017/10/18.
 */
@RestController
@RequestMapping("/api/test")
public class TestResource {
    @Autowired
    private Sender sender;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    @ResponseBody
    public String testGet(@RequestParam(value = "version", required = false) String version) {
        sender.send(new Msg("s1", "t1"));
        return "success";
    }

    @RequestMapping(value = "/sendOutput", method = RequestMethod.GET)
    @ResponseBody
    public String sendOutput(@RequestParam(value = "version", required = false) String version) {
        sender.sendOutput(new SubMsg(version, "s1", "t1"));
        return "success";
    }

}
