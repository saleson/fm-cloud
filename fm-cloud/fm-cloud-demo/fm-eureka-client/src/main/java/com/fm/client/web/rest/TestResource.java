package com.fm.client.web.rest;

import com.google.common.collect.ImmutableMap;
import com.tls.sigcheck.tls_sigcheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by saleson on 2017/10/18.
 */
@RestController
@RequestMapping("/api/test")
public class TestResource {
    @Autowired
    Environment env;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> testGet(@RequestParam(value = "version", required = false) String version) {
        return ImmutableMap.of("test", "success.", "serverPort", env.getProperty("server.port"));
    }


    @RequestMapping(value = "/genUsersig", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> genUsersig(@RequestParam("identifier") String identifier) {
        long start = System.currentTimeMillis();
        tls_sigcheck demo = new tls_sigcheck();
        // 使用前请修改动态库的加载路径
        demo.loadJniLib("/home/tls/tls_sig_api-src/jnisigcheck_mt_x64.so");
//        demo.loadJniLib("/Users/saleson/Downloads/tencent_im/jnisigcheck_mt_x64.so");
        long end = System.currentTimeMillis();
        System.out.println("load times:" + (end - start));

        String priKey = "-----BEGIN PRIVATE KEY-----\n" +
                "MIGEAgEAMBAGByqGSM49AgEGBSuBBAAKBG0wawIBAQQguTKiOCQrAIa1ooUom0T+\n" +
                "ARYWqTBfdZKIbI/V+4+1bLOhRANCAATwgfbv3z0VYp8bkPy0EnzpOybUDjVUXG8O\n" +
                "Zof5lmoBGEIdphIj9l7TBwTcbjeU9fxiCAz0J3TPRa3ki+daTG2N\n" +
                "-----END PRIVATE KEY-----";

        start = System.currentTimeMillis();
        int ret = demo.tls_gen_signature_ex2("1400046172", "testadmin", priKey);
        end = System.currentTimeMillis();
        System.out.println("gen usersig times:" + (end - start));
        if (ret != 0) {
            return ImmutableMap.of("ret", String.valueOf(ret), "err", demo.getErrMsg());
        }
        return ImmutableMap.of("usersig", demo.getSig());
    }


}
