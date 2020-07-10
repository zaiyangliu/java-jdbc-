package com.atguigu3.preparedstatement.crud;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

import com.atguigu3.bean.Employees;
import com.atguigu3.util.JDBCUtils;

/**
 * @针对于employees表的查询操作
 * @author tys10
 *
 */
public class EmployeesForQuery {

	@Test
	public void testQuery1(){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT employee_id, first_name, last_name, email, phone_number, job_id, salary, commission_pct, manager_id, department_id, hiredate FROM employees WHERE employee_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setObject(1, 101);
			//执行并返回结果集
			resultSet = ps.executeQuery();
			//处理结果集
			if(resultSet.next()) {//判断结果集的下一条是否有数据，如果有数据返回true，并且指针下移，如果为false，指针不下移
				//获取当前这一条数据的各个字段值
				int  employee_id = resultSet.getInt(1);
				String first_name = resultSet.getString(2);
				String last_name = resultSet.getString(3);
				String email = resultSet.getString(4);
				String phone_number = resultSet.getString(5);
				String job_id = resultSet.getString(6);
				double salary = resultSet.getDouble(7);
				double commission_pct = resultSet.getDouble(8);
				int manager_id = resultSet.getInt(9);
				int department_id = resultSet.getInt(10);
				Date hiredate = resultSet.getDate(11);
				
				//方式一：
				System.out.println(employee_id);
				//方式二：
				Object[] data = new Object[] {employee_id, first_name};
				for(Object a : data) {
					System.out.println(a);
				}
				
				//方式三：将数据封装为一个对象（推荐方式）
				Employees employees = new Employees(employee_id, first_name, last_name, email, phone_number, job_id, salary, commission_pct, manager_id, department_id, hiredate);
				System.out.println(employees);
			}  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			//关闭资源
			JDBCUtils.closeResource(conn, ps, resultSet);
		}
	}
}
