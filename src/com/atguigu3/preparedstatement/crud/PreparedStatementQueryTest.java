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
 * 使用PreparedStatement实现针对于不同表的通用的查询操作
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
			//执行，获取结果集
			rs = ps.executeQuery();
			//获取结果集的元数据
			ResultSetMetaData rsmd = rs.getMetaData();
			//获取列数
			int columnCount = rsmd.getColumnCount();
			if(rs.next()) {
				T t = clazz.newInstance();
				for(int i = 0; i < columnCount; i++) {
					
					//获取每个列的值,通过resultSet
					Object columnValue = rs.getObject(i+1);
					//获取每个列的列名，通过resultsetmetadata
					//获取列的列名：getColumnName()
					//获取列的别名：getColumnLabel()
					String columnLabel = rsmd.getColumnLabel(i+1);
					//通过反射，将对象指定名为columnName的属性赋值为指定值coulumnValue
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
