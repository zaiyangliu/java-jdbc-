package com.atguigu3.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

/**
 * �������ݿ�Ĺ�����
 * @author tys10
 *
 */
public class JDBCUtils {
	/**
	 * ��ȡ���ݿ������
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		//1.��ȡ�����ļ���Ϣ
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
		Properties pros = new Properties();
		pros.load(is);
		String user = pros.getProperty("user");
		String password = pros.getProperty("password");
		String url = pros.getProperty("url");
		String driverClass = pros.getProperty("driverClass");
		//2.��������
		Class.forName(driverClass);
		//3.��ȡ����
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;			
	}
	/**
	 * �ر���Դ����
	 * @param conn
	 * @param ps
	 */
	public static void closeResource(Connection conn, Statement ps) {
		try {
			if(ps != null)
				ps.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(conn != null)
				conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
