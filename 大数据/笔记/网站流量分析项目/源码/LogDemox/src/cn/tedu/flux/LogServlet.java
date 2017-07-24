package cn.tedu.flux;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class LogServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(LogServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��ȡ���в���
		String str = request.getQueryString();
		//����url����
		str = URLDecoder.decode(str,"utf-8");
		//��str���д����Ϊ��ֵ��|�ָ�����ʽ
		StringBuffer buffer = new StringBuffer();
		String [] kvs = str.split("&");
		for(String kv : kvs){
			String value = kv.split("=").length >=2 ? kv.split("=")[1] : "";
			buffer.append(value+"|");
		}
		//ƴ��ip
		String ip = request.getRemoteAddr();
		buffer.append(ip);
		
		str = buffer.toString();
		
		logger.info(str);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
