package com.wp.weixin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wp.weixin.utils.ValidationUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;


/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 获取请求参数
		String signature = request.getParameter("signature");// 签名
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");// 随机数
		String echostr = request.getParameter("echostr");// 随机字符串
		Boolean flag = ValidationUtil.checkSignature(signature, timestamp,
				nonce);
		PrintWriter out = response.getWriter();
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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
				System.out.println("通过校验！signature:" + signature + "  "
						+ "timestamp:" + timestamp + "  " + "nonce:" + nonce
						+ "  " + "echostr:" + echostr);
				// 如果验证通过，才可以进行下面的其他步骤
				InputStream inputStream = request.getInputStream();// 获取一个流（微信的消息内容都是用流来传输的）

				SAXReader reader = new SAXReader();
				Document document = reader.read(inputStream);
				result = new FlowController().tuLingFlow(document);
				// 发送结果
				if(result!=null){
					out.print(result);					
				}
			} else {
				System.out.println("未通过校验！signature:" + signature + "  "
						+ "timestamp:" + timestamp + "  " + "nonce:" + nonce
						+ "  " + "echostr:" + echostr);
			}
			out.close();
			out = null;

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * InputStreamReader isr = new InputStreamReader(is, "utf-8");
		 * BufferedReader br = new BufferedReader(isr);
		 * 
		 * String str = ""; StringBuffer sb = new StringBuffer(); while (null !=
		 * (str = br.readLine())) { sb.append(str); } String content =
		 * sb.toString(); System.out.println(content);
		 * 
		 * // 调用微信出来流程，获取处理结果 String result = new
		 * FlowController().tuLingFlow(content);
		 */
	}
}
