
import java.sql.*;

public class Connect {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://10.16.39.167:1433;databaseName=ExamSystem;integratedSecurity=false;encrypt=false;";
        Connection conn;
        try {
            conn = DriverManager.getConnection(url,"sa","mark4567");
            Statement stat = conn.createStatement();//创建一个 Statement 对象来将 SQL 语句发送到数据库。
            ResultSet resultSet=stat.executeQuery("select * from Admin");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }// 连接数据库cpp

    }
}
