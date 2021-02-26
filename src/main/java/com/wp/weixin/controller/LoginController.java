package com.wp.weixin.controller;

import com.wp.weixin.utils.ValidationUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @program: weixin
 * @description:
 * @author: Mr.Wang
 * @create: 2021-02-26 10:23
 **/
@RestController
@RequestMapping("/wx")
public class LoginController {

    @GetMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response){
        // 获取请求参数
        String signature = request.getParameter("signature");// 签名
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");// 随机数
        String echostr = request.getParameter("echostr");// 随机字符串
        Boolean flag = ValidationUtil.checkSignature(signature, timestamp,
                nonce);
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (flag) {
            out.print(echostr);
            System.out.println("通过校验！signature:" + signature + "  "
                    + "timestamp:" + timestamp + "  " + "nonce:" + nonce + "  "
                    + "echostr:" + echostr);
        } else {
            System.out.println("未通过校验！signature:" + signature + "  "
                    + "timestamp:" + timestamp + "  " + "nonce:" + nonce + "  "
                    + "echostr:" + echostr);
        }
        out.close();
        out = null;

    }
}
