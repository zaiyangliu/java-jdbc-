package com.atguigu3.preparedstatement.crud;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.junit.Test;

import com.atguigu3.bean.Employees;
import com.atguigu3.bean.Jobs;
import com.atguigu3.util.JDBCUtils;

/**
 * ʹ��PreparedStatementʵ������ڲ�ͬ���ͨ�õĲ�ѯ����
 * @author tys10
 *
 */
public class PreparedStatementQueryTest {
	@Test
	public void testGetInstance() {
		String sql = "SELECT job_id  jobId, job_title jobTitle, min_salary minSalary, max_salary maxSalary FROM jobs WHERE job_id = ?";
		Jobs jobs = getInstance(Jobs.class,sql, "AC_ACCOUNT");
		System.out.println(jobs);
		String sql1 = "select employee_id from employees where employee_id = ?";
		Employees employees = getInstance(Employees.class, sql1, 101);
		System.out.println(employees);
	}
	public <T> T getInstance(Class<T> clazz, String sql, Object... args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			ps = conn.prepareStatement(sql);
			for(int i = 0; i < args.length; i++) {
				ps.setObject(i+1, args[i]);
			}
			//ִ�У���ȡ�����
			rs = ps.executeQuery();
			//��ȡ�������Ԫ����
			ResultSetMetaData rsmd = rs.getMetaData();
			//��ȡ����
			int columnCount = rsmd.getColumnCount();
			if(rs.next()) {
				T t = clazz.newInstance();
				for(int i = 0; i < columnCount; i++) {
					
					//��ȡÿ���е�ֵ,ͨ��resultSet
					Object columnValue = rs.getObject(i+1);
					//��ȡÿ���е�������ͨ��resultsetmetadata
					//��ȡ�е�������getColumnName()
					//��ȡ�еı�����getColumnLabel()
					String columnLabel = rsmd.getColumnLabel(i+1);
					//ͨ�����䣬������ָ����ΪcolumnName�����Ը�ֵΪָ��ֵcoulumnValue
					Field field = clazz.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(t, columnValue);
				}
				return t;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.closeResource(conn, ps, rs);
			
		}
		return null;
	}
}
