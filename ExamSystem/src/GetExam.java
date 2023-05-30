import java.sql.*;

public class GetExam {
    public void main() {
        String url = "jdbc:sqlserver://10.16.39.167:1433;databaseName=ExamSystem;integratedSecurity=false;encrypt=false;";
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, "sa", "mark4567");
            Statement stat = conn.createStatement();//创建一个 Statement 对象来将 SQL 语句发送到数据库。
            ResultSet resultSet = stat.executeQuery("SELECT * FROM Exam");

            while (resultSet.next()) {
                int examId = resultSet.getInt("ID");
                String examName = resultSet.getString("Name");
                String examTime = resultSet.getString("Time");
                int examTeacherId = resultSet.getInt("TeacherID");
                int examQuestionId = resultSet.getInt("QuestionID");
                String examTips = resultSet.getString("Tips");
                System.out.println("考试ID：" + examId + "      考试名称：" + examName + "      考试时间：" + examTime + "      教师ID：" + examTeacherId + "      试题ID：" + examQuestionId + "      考试提醒：" + examTips);
            }
        } catch (SQLException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
