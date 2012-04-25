package chatonline.access;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public  class DBMSTool {
	private static String sqlUser="root";
	private static String sqlPsd="root";
//	private static String sqlDriver="oracle.jdbc.driver.OracleDriver";
	//private static String sqlDriver="org.postgresql.Driver";
	private static String sqlDriver="com.mysql.jdbc.Driver";
//	private static String sqlServer="jdbc:oracle:thin:@localhost:1521:XE";
	//private static String sqlServer="jdbc:postgresql://localhost:5432/test";
	private static String sqlServer="jdbc:mysql://localhost/test";
	private static PrintStream debugLog=System.out;
	public static Connection getConnection(){
		Connection con=null;
		try {
			Class.forName(sqlDriver);
			con = DriverManager.getConnection(sqlServer, sqlUser, sqlPsd);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(debugLog);
			throw new RuntimeException("注册驱动失败！！");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(debugLog);
			throw new RuntimeException("获取连接失败！！");
		}
		return con;
	}
	public static void  closeResultSet(ResultSet aSet){
		if(aSet!=null){
			try {
				aSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(debugLog);
				throw new RuntimeException("closeResult failed!!");
			}
		}
	}
	public static void closeConnection(Connection aCon){
		if(aCon!=null){
			try {
				aCon.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(debugLog);
				throw new RuntimeException("closeConnection failed!!");
			}
		}
	}
	public static void closeStatement(Statement aStmt){
		if(aStmt!=null){
			try {
				aStmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(debugLog);
				throw new RuntimeException("closeStatement failed!!");
			}
		}
	}
}
