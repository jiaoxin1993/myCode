
package jdbcCode.db;

import java.sql.*;

public class Test {
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		String uri = "jdbc:mysql://154.3.0.12:3306/system_platform";
		String name = "root";
		String password = "withub";
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstme = null;
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
			rs = stmt.executeQuery("");


			//遍历结果集
			while(rs.next()){
				//System.out.println(rs.getString("MAILBOX_ID"));
				//rs.getString("MAILBOX_ID").split(",")[0]
				sb.append(","+rs.getString("MAILBOX_ID"));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
