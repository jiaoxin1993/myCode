package jdbcCode.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Conn {
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		String sql ="select group_concat(MAILBOX_ID) MAILBOX_ID from james_mailbox  GROUP BY USER_NAME,MAILBOX_NAME HAVING COUNT(MAILBOX_NAME) >1";
		String sql2 ="SELECT MAILBOX_ID FROM `james_mail` GROUP BY MAILBOX_ID";//存在
		List<String> list = query(sql);
		List<String> list2 = query(sql2);
		//System.out.println(list2.size());
		//System.out.println(list.size());
		list.removeAll(list2);
		for (String str : list) {
			if(!"".equals(str)){
				sb.append(","+str);
			}
		}
		//System.out.println(list.size());
		System.out.println(sb.toString());//1944
	}

	public static List<String> query2(String sql){
		StringBuffer sb = new StringBuffer();
		String uri = "jdbc:mysql://219.145.168.171:3306/mail_sx";
		String name = "root";
		String password = "withub";
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement  pstme = null;
		ResultSet rs = null;
		//加载数据库驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");//实例化时自动向 DriverManager注册，不需要显式调用DirverManager.registerDriver方法
			conn = DriverManager.getConnection(uri, name, password);

			stmt = conn.createStatement();
//			//批处理
//			stmt.addBatch("sql");
//			stmt.addBatch("sql2");
//			stmt.addBatch("sql3");

			rs = stmt.executeQuery(sql);


			//遍历结果集
			while(rs.next()){
				sb.append(","+rs.getString("MAILBOX_ID"));
			}
			List<String> list = new ArrayList<String>(Arrays.asList(sb.toString().substring(1).split(",")));
			return list;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			//关闭连接
			try {
				if(rs != null){
					rs.close();
				}

				if(stmt != null){
					stmt.close();
				}

				if(conn != null){
					conn.close();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static List<String> query(String sql){
		StringBuffer sb = new StringBuffer();
		String uri = "jdbc:mysql://219.145.168.171:3306/mail_sx";
		String name = "root";
		String password = "withub";
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement  pstme = null;
		ResultSet rs = null;
		//加载数据库驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");//实例化时自动向 DriverManager注册，不需要显式调用DirverManager.registerDriver方法
			conn = DriverManager.getConnection(uri, name, password);

			stmt = conn.createStatement();
//			//批处理
//			stmt.addBatch("sql");
//			stmt.addBatch("sql2");
//			stmt.addBatch("sql3");

			rs = stmt.executeQuery(sql);


			//遍历结果集
			while(rs.next()){
				//System.out.println(rs.getString("MAILBOX_ID"));
				//rs.getString("MAILBOX_ID").split(",")[0]
				sb.append(","+rs.getString("MAILBOX_ID"));
			}
			List<String> list = new ArrayList<String>(Arrays.asList(sb.toString().substring(1).split(",")));
			return list;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			//关闭连接
			try {
				if(rs != null){
					rs.close();
				}

				if(stmt != null){
					stmt.close();
				}

				if(conn != null){
					conn.close();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}

