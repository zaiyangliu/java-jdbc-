package com.atguigu1.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

public class ConnectionTest {
	
	//��ʽһ��
	@Test
	public void testConnection1() throws SQLException {
		//��ȡDriverʵ�������
		Driver driver = new com.mysql.jdbc.Driver();
		//jdbc:mysql:Э��
		//localhost:ip��ַ
		//3306:Ĭ��mysql�Ķ˿ں�
		//hutubill�Լ������ݿ⣬�����Լ����ݿ�������д
		String url = "jdbc:mysql://localhost:3306/hutubill";
		//���û����������װ��properties��
		Properties info = new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "admin");
		Connection conn = driver.connect(url , info );
		System.out.println(conn);
	}
	//��ʽ�����Է�ʽһ�ĵ����������µĳ����в����ֵ�����api��ʹ�ó�����и��õ���ֲ��
	@Test
	public void testConnection2() throws Exception {
		//��ȡDriverʵ�������ʹ�÷���
		Class clazz = Class.forName("com.mysql.jdbc.Driver");
		@SuppressWarnings("deprecation")
		Driver driver = (Driver) clazz.newInstance();
		//�ṩҪ���ӵ����ݿ�
		String url = "jdbc:mysql://localhost:3306/hutubill";
		//�ṩ������Ҫ���û��������루�Լ��ģ�
		Properties info = new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "admin");
		//��ȡ����
		Connection conn = driver.connect(url, info);
		System.out.println(conn);
	}
	//��ʽ����ʹ��DriverManager��������ࣩ���滻Driver
	@Test
	public void testConnection3() throws Exception {
		//1.��ȡDriverʵ����Ķ���
		Class clazz = Class.forName("com.mysql.jdbc.Driver");
		@SuppressWarnings("deprecation")
		Driver driver = (Driver) clazz.newInstance();
		//2.�ṩ�����������ӵĻ�����Ϣ
		String url = "jdbc:mysql://localhost:3306/hutubill";
		String user = "root";
		String password = "admin";
		//3.ע������
		DriverManager.registerDriver(driver);
		//��ȡ����
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println(conn);
	}
	//��ʽ�ģ��Է�ʽ�����Ż�
	@Test
	public void testConnection4() throws Exception {
		//1.�ṩ�����������ӵĻ�����Ϣ
		String url = "jdbc:mysql://localhost:3306/hutubill";
		String user = "root";
		String password = "admin";
		//2.����Driver
		Class.forName("com.mysql.jdbc.Driver");
		//����ڷ�ʽ��������ʡ�����²�����
//		@SuppressWarnings("deprecation")
//		Driver driver = (Driver) clazz.newInstance();
//		//ע������
//		DriverManager.registerDriver(driver);
		//Ϊʲô����ʡ�����������ģ�
		/**
		 * ��mysql��Driverʵ�����У����������µĲ�����
		 * static{
		 * 		try{
		 * 			java.sql.DriverManager.registerDriver(new Driver());
		 * 		}catch (SQLException E){
		 * 			throw new RuntimeException("Can't register drvier!");
		 * 	}
		 */
		//3.��ȡ����
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println(conn);
	}
	//��ʽ�壺���հ� �����ݿ�������Ҫ���ĸ�������Ϣ�����������ļ��У�ͨ����ȡ�����ļ��ķ�ʽ����ȡ����
	/**
	 * 1.ʵ�������������ķ��룬ʵ���˽��
	 * 2.�޸������ļ���Ϣ�����Ա���������´����
	 * @throws Exception
	 */
	@Test
	public void getConnection5() throws Exception {
		//1.��ȡ�����ļ���Ϣ
		InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
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
		System.out.println(conn);
	}
	
}
