import java.sql.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.lang.*;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Exam {
    public void main() {
        String url = "jdbc:sqlserver://10.16.39.167:1433;databaseName=ExamSystem;integratedSecurity=false;encrypt=false;";
        Connection conn;
        Scanner scan = new Scanner(System.in);


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
            int examid = 9999;
            while (true) {
                System.out.println("请输入你要参加的考试ID");
                examid = scan.nextInt();
                ResultSet time = stat.executeQuery("SELECT Time FROM Exam WHERE id = " + examid);
                LocalDateTime currentTime = LocalDateTime.now();
                LocalDateTime targetTime = null;

                while (time.next()) {
                    String targetTimeStr = time.getString("Time");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                    targetTime = LocalDateTime.parse(targetTimeStr, formatter);
                }
                if (currentTime.isBefore(targetTime)) {
                    System.out.println("未到考试时间！请重试！");
                } else {
                    break;
                }
            }

            ResultSet qid = stat.executeQuery("SELECT QuestionID FROM Exam WHERE ID = " + examid);
            int queid = 99999;
            while (qid.next()) {
                queid = qid.getInt("QuestionID");

            }
            ResultSet textResult;
            PreparedStatement stmt = conn.prepareStatement("SELECT Text FROM Question WHERE ID = ?");
            stmt.setInt(1, queid); // 假设 qid 是一个整数变量

            textResult = stmt.executeQuery();
            while (textResult.next()) {
                String exam = textResult.getString("Text");
                System.out.println("试题如下：\n" + exam);
            }
            System.out.println("请输入答案，连续按下两次回车后结束：");


            StringBuilder ANSWER = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals("")) {
                        break;
                    }
                    ANSWER.append(line).append("\n");
                }
            } catch (IOException e) {
                System.out.println("发生了IO异常：" + e.getMessage());
            }

            String sql = "INSERT INTO Response (StudentID, ExamID, Text) VALUES (?, ?, ?)";//插入答案
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(Main.Account));
            statement.setInt(2, examid);
            statement.setString(3, ANSWER.toString());
            statement.executeUpdate();

            String sqll = "SELECT Text FROM Answer WHERE QuestionID = ?";//获取正确答案
            PreparedStatement statementt = conn.prepareStatement(sqll);
            statementt.setInt(1, queid);
            ResultSet answerSet = statementt.executeQuery();
            String rightAnswer = null;
            if (answerSet.next()) {
                rightAnswer = answerSet.getString("Text");
            }

            float score = getSimilarityRatio(ANSWER.toString().trim(), rightAnswer.trim());
            System.out.println("你的分数是" + score);


            String sqlll = "INSERT INTO Sheet ( ExamID, StudentID, StudentName, StudentGrade) VALUES (?, ?, ?, ? )";
            PreparedStatement statementtt = conn.prepareStatement(sqlll);

            statementtt.setInt(1, examid);
            statementtt.setInt(2, Integer.parseInt(Main.Account));
            statementtt.setString(3, Main.Name);
            statementtt.setFloat(4, score);
            statementtt.executeUpdate();

            System.out.println("再接再厉，下次取得更好成绩！！！！");



        } catch (SQLException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static float getSimilarityRatio(String str, String target) {
        int d[][]; // 矩阵
        int n = str.length();
        int m = target.length();
        int i; // 遍历str的
        int j; // 遍历target的
        char ch1; // str的
        char ch2; // target的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0 || m == 0) {
            return 0;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) { // 初始化第一列
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) { // 初始化第一行
            d[0][j] = j;
        }

        for (i = 1; i <= n; i++) { // 遍历str
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1), d[i - 1][j - 1] + temp);
            }
        }

        return (1 - (float) d[n][m] / Math.min(str.length(), target.length())) * 100F;
    }
}
