package com.atguigu3.preparedstatement.crud;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.junit.Test;

import com.atguigu3.bean.Jobs;
import com.atguigu3.util.JDBCUtils;

/**针对jobs表的通用查询
 * 针对表的字段名与类的属性名不相同的情况
 * 1.必须声明sql时，使用类的属性名来命名字段的别名
 * 2.使用resultSetMetaData时，需要使用getColumnLabel()来替换getColumnName(),获取列的别名
 * 补充说明：如果sql中没有给字段取别名，getColumnLabel()获取的就是列名
 * @author tys10
 *
 */
public class JobsForQuery {
	
	@Test 
	public void testjobsForQuery() {
		String sql = "SELECT job_id  jobId, job_title jobTitle, min_salary minSalary, max_salary maxSalary FROM jobs WHERE job_id = ?";
		Jobs jobs = jobsForQuery(sql,"AC_ACCOUNT");
		System.out.println(jobs);
	}
	public Jobs jobsForQuery(String sql, Object ...args){
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
				Jobs jobs = new Jobs();
				for(int i = 0; i < columnCount; i++) {
					
					//获取每个列的值,通过resultSet
					Object columnValue = rs.getObject(i+1);
					//获取每个列的列名，通过resultsetmetadata
					//获取列的列名：getColumnName()
					//获取列的别名：getColumnLabel()
					String columnLabel = rsmd.getColumnLabel(i+1);
					//通过反射，将对象指定名为columnName的属性赋值为指定值coulumnValue
					Field field = Jobs.class.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(jobs, columnValue);
				}
				return jobs;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.closeResource(conn, ps, rs);
			
		}
		return null;
	}
	@Test
	public void testQuery1() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select job_id, job_title, min_salary, max_salary from jobs where job_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setObject(1, "AC_ACCOUNT");
			rs = ps.executeQuery();
			if(rs.next()) {
				String job_id = (String) rs.getObject(1);
				String job_title = (String) rs.getObject(2);
				int min_salary = (int) rs.getObject(3);
				int max_salary = (int) rs.getObject(4);
				Jobs jobs = new Jobs(job_id, job_title, min_salary, max_salary);
				System.out.println(jobs);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			JDBCUtils.closeResource(conn, ps, rs);
		}
	}
}
