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
	
	//方式一：
	@Test
	public void testConnection1() throws SQLException {
		//获取Driver实现类对象
		Driver driver = new com.mysql.jdbc.Driver();
		//jdbc:mysql:协议
		//localhost:ip地址
		//3306:默认mysql的端口号
		//hutubill自己的数据库，根据自己数据库名字填写
		String url = "jdbc:mysql://localhost:3306/hutubill";
		//将用户名和密码封装在properties中
		Properties info = new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "admin");
		Connection conn = driver.connect(url , info );
		System.out.println(conn);
	}
	//方式二：对方式一的迭代：在如下的程序中不出现第三方api，使得程序具有更好的移植性
	@Test
	public void testConnection2() throws Exception {
		//获取Driver实现类对象，使用反射
		Class clazz = Class.forName("com.mysql.jdbc.Driver");
		@SuppressWarnings("deprecation")
		Driver driver = (Driver) clazz.newInstance();
		//提供要连接的数据库
		String url = "jdbc:mysql://localhost:3306/hutubill";
		//提供链接需要的用户名和密码（自己的）
		Properties info = new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "admin");
		//获取连接
		Connection conn = driver.connect(url, info);
		System.out.println(conn);
	}
	//方式三：使用DriverManager（具体的类）来替换Driver
	@Test
	public void testConnection3() throws Exception {
		//1.获取Driver实现类的对象
		Class clazz = Class.forName("com.mysql.jdbc.Driver");
		@SuppressWarnings("deprecation")
		Driver driver = (Driver) clazz.newInstance();
		//2.提供另外三个连接的基本信息
		String url = "jdbc:mysql://localhost:3306/hutubill";
		String user = "root";
		String password = "admin";
		//3.注册驱动
		DriverManager.registerDriver(driver);
		//获取连接
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println(conn);
	}
	//方式四：对方式三的优化
	@Test
	public void testConnection4() throws Exception {
		//1.提供另外三个连接的基本信息
		String url = "jdbc:mysql://localhost:3306/hutubill";
		String user = "root";
		String password = "admin";
		//2.加载Driver
		Class.forName("com.mysql.jdbc.Driver");
		//相较于方式三，可以省略如下操作：
//		@SuppressWarnings("deprecation")
//		Driver driver = (Driver) clazz.newInstance();
//		//注册驱动
//		DriverManager.registerDriver(driver);
		//为什么可以省略上述操作的？
		/**
		 * 在mysql的Driver实现类中，声明了如下的操作：
		 * static{
		 * 		try{
		 * 			java.sql.DriverManager.registerDriver(new Driver());
		 * 		}catch (SQLException E){
		 * 			throw new RuntimeException("Can't register drvier!");
		 * 	}
		 */
		//3.获取连接
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println(conn);
	}
	//方式五：最终版 将数据库连接需要的四个基本信息声明在配置文件中，通过读取配置文件的方式，获取连接
	/**
	 * 1.实现了数据与代码的分离，实现了解耦。
	 * 2.修改配置文件信息，可以避免程序重新打包。
	 * @throws Exception
	 */
	@Test
	public void getConnection5() throws Exception {
		//1.读取配置文件信息
		InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties pros = new Properties();
		pros.load(is);
		String user = pros.getProperty("user");
		String password = pros.getProperty("password");
		String url = pros.getProperty("url");
		String driverClass = pros.getProperty("driverClass");
		//2.加载驱动
		Class.forName(driverClass);
		//3.获取连接
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println(conn);
	}
	
}
