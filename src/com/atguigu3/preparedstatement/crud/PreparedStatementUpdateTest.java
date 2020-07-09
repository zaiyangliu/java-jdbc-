package com.atguigu3.preparedstatement.crud;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

import com.atguigu3.util.JDBCUtils;

/**
 * ʹ��PreparedStatement���滻Statement,ʵ�ֶ����ݱ����ɾ�Ĳ���
 * 1.��ɾ�ģ�2.��
 * @author tys10
 *
 */
public class PreparedStatementUpdateTest {
	@Test
	public void testupdate() {
//		String sql = "delete from employees where employee_id = ?";
//		update(sql,209);
		String sql = "update employees set first_name = ? where employee_id = ?";
		update(sql, "heihei", 208);
	}
	//ͨ�õ���ɾ�Ĳ���
	public void update(String sql, Object ...args) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			//1.��ȡ���ݿ������
			conn = JDBCUtils.getConnection();
			//2.Ԥ����sql��䣬����PreparedStatement��ʵ��
			ps = conn.prepareStatement(sql);
			//3.���ռλ��
			for(int i = 0; i < args.length; i++) {
				ps.setObject(i+1, args[i]);
			}
			//4.ִ��
			ps.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			//5.�ر���Դ
			JDBCUtils.closeResource(conn, ps);
		}
	}
	//�޸�employees���һ����¼
	@Test
	public void testUpdate() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			//1.��ȡ���ݿ�����
			conn = JDBCUtils.getConnection();
			//2.Ԥ����sql��䣬����PreparedStatement��ʵ��
			String sql = "update employees set first_name = ? where employee_id = ?";
			ps = conn.prepareStatement(sql);
			//3.���ռλ��
			ps.setObject(1, "haha");
			ps.setObject(2, 208);
			//4.ִ��
			ps.execute();
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//5.��Դ�ر�
			JDBCUtils.closeResource(conn, ps);
		}
	}
	//��employees�������һ����¼
	@Test
	public void testInsert() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
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
			conn = DriverManager.getConnection(url, user, password);
			//System.out.println(conn);


			//4.Ԥ����sql��䣬����preparedStatement��ʵ��
			String sql = "insert into employees(first_name, last_name, email)values(?,?,?)"; //ռλ��
			ps =  conn.prepareStatement(sql);
			//5.���ռλ��
			ps.setString(1, "lu");
			ps.setString(2, "ang");
			ps.setString(3, "ang@afs.com");
			//6.ִ�в���
			ps.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//7.��Դ�ر�
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
	
}
