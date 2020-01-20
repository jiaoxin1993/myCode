
package jdbcCode.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Test1 {
	public static void main(String[] args) throws Exception{
		Map<String,Integer> map = new HashMap();
		map.put("su",0);
		map.put("err",0);
		Integer su =0;
		Integer err =0;
		String fileName ="C:/Users/Administrator/Desktop/sql111.txt";
		String sqls = getString(fileName);
		String[] split = sqls.split(";");
		for (String sql:split){
			ex(sql,map);
		}
		System.out.println("成功"+map.get("su"));
		System.out.println("异常"+map.get("err"));
	}
	private  static String getString(String fielPath){
		File file = new File(fielPath);
		BufferedReader reader = null;
		StringBuffer sbf = new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempStr;
			while ((tempStr = reader.readLine()) != null) {
				sbf.append(tempStr);
			}
			reader.close();
			return sbf.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return sbf.toString();
	}
	private static void ex(String sql,Map<String,Integer> map){
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
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt.addBatch(sql);
			stmt.executeBatch();//批量执行
			conn.commit();//提交
			System.out.println("正常sql--" + sql);
			map.put("su",(Integer)map.get("su")+1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("代码异常-----------");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("异常sql--"+sql);
			map.put("err",(Integer)map.get("err")+1);
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
