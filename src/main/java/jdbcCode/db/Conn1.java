package jdbcCode.db;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2019/12/26.
 */
public class Conn1 {
    public static void main(String[] args) {
        String fydm="RB2";
        String ip ="154.138.2.10";
        String pwd ="vomxpQknGyGY8PK@";
        String sql="SELECT xtpt_t_role_id,COUNT(xtpt_t_user_id) num FROM `xtpt_t_user_has_xtpt_t_role` WHERE xtpt_t_role_id IN (100441, 100442, 100443, 100444, 100445, 100446, 100447, 100448, 100449, 100450, 100451, 100452, 100453, 100454, 100455, 100456, 100457, 100458, 100459, 100460, 100461, 100462, 100463, 100464, 100465, 100466, 100467, 100468, 100469, 100470, 100471, 100472,100474, 100475, 100476, 100477, 100478, 100479, 100480, 100481, 100482, 100483, 100484, 100485, 100486, 100488, 100489, 100491, 100500, 100501, 100502, 100503, 100504, 100505, 101478, 101479, 101480, 120006, 120008, 120009, 120010, 120013, 120014, 120015, 120021, 120022, 120023, 120024, 120025, 120026, 120028, 120029,140001,140003) AND court_code = '"+fydm+"' GROUP BY xtpt_t_role_id";
        System.out.println(sql);
        Map<String,String> map1 = query1(sql);
        Map<String,String> map2 = query2(ip, pwd, sql);
        StringBuffer sb = new StringBuffer();
        Set s = map2.keySet();
        Iterator iterator1 = s.iterator();
        while (iterator1.hasNext()){
            String ss = (String)iterator1.next();
            if(map2.get(ss).equals(map1.get(ss))){
                System.out.println(ss+"信息一致");
            }else{
                if(map1.get(ss) == null){
                    System.out.println(ss+"信息缺失"+map2.get(ss));
                }else {
                    System.out.println(ss+"信息不一致地方:"+map2.get(ss)+"---集中:"+map1.get(ss));
                }
                sb.append(ss+",");
            }
        }
        System.out.println(sb.toString());
    }

    //各地
    public static Map<String,String> query2(String ip,String pwd,String sql){
        StringBuffer sb = new StringBuffer();
        String uri = "jdbc:mysql://"+ip+":3306/system_platform";
        String name = "root";
        String password = pwd;
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstme = null;
        ResultSet rs = null;
        Map<String,String> map = new HashMap<String, String>();
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
                map.put(rs.getString("xtpt_t_role_id"), rs.getString("num"));
            }
            return map;
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
    //集中
    public static Map<String,String> query1(String sql){
        StringBuffer sb = new StringBuffer();
        String uri = "jdbc:mysql://154.3.0.12:3306/system_platform";
        String name = "root";
        String password = "withub";
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstme = null;
        ResultSet rs = null;
        Map<String,String> map = new HashMap<String, String>();
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
                map.put(rs.getString("xtpt_t_role_id"), rs.getString("num"));
            }
            return map;
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
