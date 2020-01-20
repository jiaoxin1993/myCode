package jdbcCode;

import java.sql.*;
public class Parameter {
	public static void main(String[] args) {
		if(args.length!=3){

		}
		String uri = "jdbc:mysql://127.0.0.1:3306/jx";
		String name = "root";
		String password = "123456";
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement  pstme = null;
		ResultSet rs = null;
		//加载数据库驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");//实例化时自动向 DriverManager注册，不需要显式调用DirverManager.registerDriver方法
			conn = DriverManager.getConnection(uri, name, password);

//			stmt = conn.createStatement();
//			//批处理
//			stmt.addBatch("sql");
//			stmt.addBatch("sql2");
//			stmt.addBatch("sql3");
			stmt.executeBatch();//批处理执行

			//占位符
			pstme = conn.prepareStatement("sql 其中内容用 ? 作为占位符");
			pstme.setInt(1, 55);// 给参数 参数下标1 内容
			pstme.addBatch();
			//两条批处理
			pstme.setInt(1, 33);// 给参数 参数下标1 内容
			pstme.addBatch();

			pstme.executeBatch();//批处理执行
			rs = pstme.executeQuery();
			//System.out.println(((JDBC4PreparedStatement)pstme).asSql());   输出占位符填充后的sql
			//调用存储过程
			CallableStatement cstmt = conn.prepareCall("(call 存贮名(?,?，?,? )");
			cstmt.registerOutParameter(3, Types.INTEGER);//设置存储参数类型
			cstmt.registerOutParameter(4, Types.INTEGER);//设置存储参数类型
			//给存储设置参数值
			cstmt.setInt(1, 11);
			cstmt.setInt(2, 33);
			cstmt.setInt(4, 22);
			cstmt.execute();//执行存储
			cstmt.getInt(3);//获取执行后的参数值 下标为3

			//遍历结果集
			while(rs.next()){
				rs.getString("字段名");
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
