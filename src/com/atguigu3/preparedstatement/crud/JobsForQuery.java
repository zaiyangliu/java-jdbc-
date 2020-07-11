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

/**���jobs���ͨ�ò�ѯ
 * ��Ա���ֶ������������������ͬ�����
 * 1.��������sqlʱ��ʹ������������������ֶεı���
 * 2.ʹ��resultSetMetaDataʱ����Ҫʹ��getColumnLabel()���滻getColumnName(),��ȡ�еı���
 * ����˵�������sql��û�и��ֶ�ȡ������getColumnLabel()��ȡ�ľ�������
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
			//ִ�У���ȡ�����
			rs = ps.executeQuery();
			//��ȡ�������Ԫ����
			ResultSetMetaData rsmd = rs.getMetaData();
			//��ȡ����
			int columnCount = rsmd.getColumnCount();
			if(rs.next()) {
				Jobs jobs = new Jobs();
				for(int i = 0; i < columnCount; i++) {
					
					//��ȡÿ���е�ֵ,ͨ��resultSet
					Object columnValue = rs.getObject(i+1);
					//��ȡÿ���е�������ͨ��resultsetmetadata
					//��ȡ�е�������getColumnName()
					//��ȡ�еı�����getColumnLabel()
					String columnLabel = rsmd.getColumnLabel(i+1);
					//ͨ�����䣬������ָ����ΪcolumnName�����Ը�ֵΪָ��ֵcoulumnValue
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
