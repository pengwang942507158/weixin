package com.wp.weixin.controller;

import com.google.gson.Gson;
import com.wp.weixin.model.*;
import com.wp.weixin.service.WxService;
import com.wp.weixin.utils.HttpRequest;
import com.wp.weixin.utils.Util;
import com.wp.weixin.utils.ValidationUtil;
import com.wp.weixin.utils.XmlUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @program: weixin
 * @description:
 * @author: Mr.Wang
 * @create: 2021-02-26 10:23
 **/
@RestController
public class WxController {

    @Autowired
    private WxService wxService;


    @GetMapping("/wx")
    public void wxSignature(HttpServletRequest request, HttpServletResponse response){
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

    @PostMapping("/wx")
    public void wxOperation(HttpServletRequest request,HttpServletResponse response)  {
        try {
            // 设置编码
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("test/html;charset=UTF-8");
            // 获取请求参数
            String signature = request.getParameter("signature");// 签名
            String timestamp = request.getParameter("timestamp");// 时间戳
            String nonce = request.getParameter("nonce");// 随机数
            String echostr = request.getParameter("echostr");// 随机字符串
            Boolean flag = ValidationUtil.checkSignature(signature, timestamp,
                    nonce);
            PrintWriter out = response.getWriter();
            String result = "";
            if (flag) {
                out.print(echostr);
                System.out.println("通过校验！signature:" + signature + "  "+ "timestamp:" + timestamp + "  " + "nonce:" + nonce + "  " + "echostr:" + echostr);
                // 如果验证通过，才可以进行下面的其他步骤

                InputStream inputStream = request.getInputStream();// 获取一个流（微信的消息内容都是用流来传输的）
                Map<String, String> requestMap = XmlUtil.parseRequest(inputStream);
                System.out.println(requestMap);
                result = wxService.getRespose(requestMap);
                System.out.println(result);

                // 发送结果
                if(result!=null){
                    out.print(result);
                }
            } else {
                System.out.println("未通过校验！signature:" + signature + "  "
                        + "timestamp:" + timestamp + "  " + "nonce:" + nonce
                        + "  " + "echostr:" + echostr);
            }
            out.flush();
            out.close();
            out = null;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @GetMapping("/createMenu")
    public  String createMenu(){
        //菜单对象
        Button btn = new Button();

        //创建第一个一级菜单
        btn.getButton().add(new ViewButton("预约按摩", "http://www.baidu.com"));


        //创建第二个一级菜单
        SubButton sb2 = new SubButton("健康养生");
        //为第二个一级菜单增加子菜单
        sb2.getSub_button().add(new PhotoOrAlbumButton("往期知识分享", "31"));
        sb2.getSub_button().add(new ClickButton("食物禁忌查询", "32"));
        sb2.getSub_button().add(new ViewButton("按摩视频教程", "http://news.163.com"));
        //加入第二个一级菜单
        btn.getButton().add(sb2);


        //创建第三个一级菜单
        SubButton sb3 = new SubButton("我的");
        //为第三个一级菜单增加子菜单
        sb3.getSub_button().add(new PhotoOrAlbumButton("我的预约", "31"));
        sb3.getSub_button().add(new ClickButton("我的评价", "32"));
        sb3.getSub_button().add(new ViewButton("我的账户", "http://news.163.com"));
        //加入第三个一级菜单
        btn.getButton().add(sb3);

        //准备url
        String url = Constant.CREATE_MENU_URL.replace("ACCESS_TOKEN", wxService.getAccessToken());
        //发送请求
        String result = Util.post(url, new Gson().toJson(btn));
        System.out.println(result);
        return result;
    }

    @GetMapping("/setIndustry")
    public  String setIndustry(@RequestParam Map<String,String> map){
        String url=Constant.SET_INDUSTRY_URL.replace("ACCESS_TOKEN",wxService.getAccessToken());
        map.put("industry_id1","1");
        map.put("industry_id2","4");
        String result =HttpRequest.post(url,new Gson().toJson(map));
        System.out.println(result);
        return  result;
    }

    @GetMapping("/getIndustry")
    public  String getIndustry(){
        String url=Constant.GET_INDUSTRY_URL.replace("ACCESS_TOKEN",wxService.getAccessToken());
        String result =HttpRequest.get(url);
        System.out.println(result);
        return  result;
    }



    /**
     * 发送模板消息
     * by 罗召勇 Q群193557337
     */
    @GetMapping("/sendTemplateMessage")
    public String sendTemplateMessage() {
        String result = wxService.sendTemplateMessage();
        System.out.println(result);
        return  result;
    }




}
