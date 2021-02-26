package com.wp.weixin.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class ValidationUtil {
    private static String token="51hgo";
    
    /*
     * 验证签名
     * 
     * 
     * 开发者通过检验signature对请求进行校验（下面有校验方式）。若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败。加密/校验流程如下：
        1）将token、timestamp、nonce三个参数进行字典序排序
        2）将三个参数字符串拼接成一个字符串进行sha1加密
        3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     * 
     * 
     */
    public static boolean checkSignature(String signature ,String timestamp,String nonce) {
        //构建一个字符串数组
        String[] strArr=new String[]{token,timestamp,nonce}; 
        //1）将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(strArr);
        //定义一个StringBuffer
        StringBuilder sb= new StringBuilder();
        //遍历这个数组      
        for(int i=0;i<strArr.length;i++){
            //将三个参数字符串拼接成一个字符串
            sb.append(strArr[i]);
        }
        //进行sha1加密
        MessageDigest md=null;
        String result=null;
        try {
            md=MessageDigest.getInstance("SHA-1");
            byte [] mdArr= md.digest(sb.toString().getBytes());
            sb=null;//加密完成后将sb设为空
            result=bytesToHexString(mdArr).toLowerCase();
            System.out.println("result="+result);
            System.out.println("signature="+signature);
            if(result!=null&&result.equals(signature)){
                return true;
            }
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }      
        return false;  
    }
    
    //将字节数组转化我16进制字符串
    private static String arrToStr(byte[] arr){
        String str="";
        for(int i=0;i<arr.length;i++){
            str+=arrToStr1(arr[i]);
        }      
        return str;      
    }
    
    //将一个字节转化为16进制字符串
    
    private static String arrToStr1(byte bye){
        char[] ch={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] temp=new char[2];
        temp[0]=ch[bye>>>4 & 0X0F];
        temp[1]=ch[bye & 0X0F];
        String st=new String(temp);
        return st;      
    }
    
    
    
    /** 
     * 把字节数组转换成16进制字符串 
     *  
     * @param bArray 
     * @return 
     */  
    public static final String bytesToHexString(byte[] bArray) {  
        StringBuffer sb = new StringBuffer(bArray.length);  
        String sTemp;  
        for (int i = 0; i < bArray.length; i++) {  
            sTemp = Integer.toHexString(0xFF & bArray[i]);  
            if (sTemp.length() < 2)  
                sb.append(0);  
            sb.append(sTemp.toUpperCase());  
        }  
        return sb.toString();  
    }  
    
 /*   public static void main(String[] args) {
        byte [] b={'A','E','G'};
        System.out.println(arrToStr(b)+"-----------------------------------------");
        System.out.println(bytesToHexString(b)+"-----------------------------------------");  
        
        checkSignature(token, token, token);
        
    }*/

}
