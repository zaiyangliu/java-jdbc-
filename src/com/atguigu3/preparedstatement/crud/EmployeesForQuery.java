package com.atguigu3.preparedstatement.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

import com.atguigu3.util.JDBCUtils;

/**
 * @针对于employees表的查询操作
 * @author tys10
 *
 */
public class EmployeesForQuery {
	@Test
	public void testQuery1() throws Exception {
		Connection conn = JDBCUtils.getConnection();
		String sql = "select employee_id, first_name, last_name, email, phone_number, job_id, salary, commission_pct, manager_id, hiredate from employees where employee_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		//执行并返回结果集
		ResultSet resultSet = ps.executeQuery();
		//处理结果集
		
		
	}
}
