package jdbcCode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class Transaction {
	public static void main(String[] args) {

		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:SXT", "scott", "tiger");

			conn.setAutoCommit(false);//修改为不自动提交
			stmt = conn.createStatement();
			stmt.addBatch("insert into dept2 values (51, '500', 'haha')");
			stmt.addBatch("insert into dept2 values (52, '500', 'haha')");
			stmt.addBatch("insert into dept2 values (53, '500', 'haha')");
			stmt.executeBatch();//批量执行
			conn.commit();//提交
			conn.setAutoCommit(true);//事务完成后改回自动提交
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {

			e.printStackTrace();

			try {
				if(conn != null)
				{
					//其中有sql执行异常 直接回滚
					conn.rollback();
					conn.setAutoCommit(true);//改回自动提交
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				if(stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	}
}
