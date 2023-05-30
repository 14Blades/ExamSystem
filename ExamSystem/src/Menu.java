import java.util.Scanner;
import java.lang.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Menu {

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        Scanner scan = new Scanner(System.in);
        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        GetExam getExam = new GetExam();
        Exam exam = new Exam();
        // 格式化输出
        String formattedDateTime = now.format(formatter);
        if (Main.identity == 1) {
            while (Main.identity == 1) {
                String url = "jdbc:sqlserver://10.16.39.167:1433;databaseName=ExamSystem;integratedSecurity=false;encrypt=false;";
                Connection conn;

                System.out.println("尊敬的" + Main.Name + "管理员您好！现在是北京时间" + formattedDateTime);
                System.out.println("1.创建考试  2.查看考试 3.退出系统");
                int q = scan.nextInt();
                if (q == 1) {
                    System.out.println("请按   考试ID 考试名称 教师ID 试卷ID 注意事项  考试时间 输入：");
                    System.out.println("如：10001 Java考试 20001 99999（默认试卷ID） 请提前15分钟进入考场 2023-05-26 09:30:00");
                    int id = scan.nextInt();
                    String name = scan.next();
                    int teacherId = scan.nextInt();
                    int questionId = scan.nextInt();
                    String tips = scan.next();
                    String time = scan.nextLine();

                    try {
                        conn = DriverManager.getConnection(url, "sa", "mark4567");
                        Statement stat = conn.createStatement();//创建一个 Statement 对象来将 SQL 语句发送到数据库。
                        String sql = "INSERT INTO Exam VALUES (" + id + ", '" + name + "', '" + time + "', " + teacherId + ", " + questionId + ", '" + tips + "')";
                        stat.executeUpdate(sql);
                    } catch (SQLException e) {
                        //TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }//输入考试信息

                if (q == 2)//输出考试信息
                {
                    getExam.main();
                }
                if (q == 3) {
                    break;
                }
            }

        }
        if (Main.identity == 2) {
            while (Main.identity == 2) {
                System.out.println("尊敬的" + Main.Name + "老师您好！现在是北京时间" + formattedDateTime);
                System.out.println("1.编辑试题    2.编辑答案    3.编辑考试      4.查看考试   \n 5.查看学生名单    6.查看成绩    7.退出系统");
                int q = scan.nextInt();
                String url = "jdbc:sqlserver://10.16.39.167:1433;databaseName=ExamSystem;integratedSecurity=false;encrypt=false;";
                Connection conn;
                try {
                    conn = DriverManager.getConnection(url, "sa", "mark4567");
                    Statement stat = conn.createStatement();
                    if (q == 1) //编辑试题
                    {
                        try {
                            System.out.println("请输入试题编号 教师ID 然后输入试题，输入两个回车结束");
                            System.out.println("如：10001 20001  （1）1+1=？ (2）世界上最高的山是什么?");
                            int id = scan.nextInt();
                            int TeacherID = scan.nextInt();
                            StringBuilder text = new StringBuilder();
                            while (scan.hasNextLine()) {
                                String line = scan.nextLine();
                                if (line.equals("")) {  //
                                    break;
                                }
                                text.append(line).append("\n");
                            }

                            String sql = "INSERT INTO Question VALUES (" + id + ", '" + TeacherID + "', '" + text + "')";
                            stat.executeUpdate(sql);
                        } catch (SQLException e) {
                            //TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (q == 2) //编辑答案
                    {
                        try {
                            System.out.println("请输入答案编号 试题ID 然后输入答案，输入两个回车结束");
                            System.out.println("如：10001 20001  （1）2 (2）珠穆朗玛峰");
                            int id = scan.nextInt();
                            int QuestionID = scan.nextInt();
                            StringBuilder text = new StringBuilder();
                            while (scan.hasNextLine()) {
                                String line = scan.nextLine();
                                if (line.equals("")) {  //
                                    break;
                                }
                                text.append(line).append("\n");
                            }

                            String sql = "INSERT INTO Answer VALUES (" + id + ", '" + QuestionID + "', '" + text + "')";
                            stat.executeUpdate(sql);
                        } catch (SQLException e) {
                            //TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    if (q == 3) //编辑考试
                    {
                        System.out.println("请输入您要编辑的考试ID：");
                        int id = scan.nextInt();
                        ResultSet resultSet = stat.executeQuery("SELECT * FROM exam WHERE id = " + id);

                        while (resultSet.next()) {
                            int examId = resultSet.getInt("ID");
                            String examName = resultSet.getString("Name");
                            String examTime = resultSet.getString("Time");
                            int examTeacherId = resultSet.getInt("TeacherID");
                            int examQuestionId = resultSet.getInt("QuestionID");
                            String examTips = resultSet.getString("Tips");
                            System.out.println("考试ID：" + examId + "      考试名称：" + examName + "      考试时间：" + examTime + "      教师ID：" + examTeacherId + "      试题ID：" + examQuestionId + "      考试提醒：" + examTips);
                        }
                        System.out.println("请输入您要设置的试卷ID：");
                        int quesid = scan.nextInt();
                        String sql = "UPDATE Exam SET QuestionID = ? WHERE id = ?";
                        PreparedStatement statement = conn.prepareStatement(sql);
                        statement.setInt(1, quesid);
                        statement.setInt(2, id);
                        statement.executeUpdate();


                    }
                    if (q == 4) {
                        getExam.main();
                    }
                    if (q == 5) {
                        ResultSet resultSet = stat.executeQuery("SELECT * FROM Student");
                        while (resultSet.next()) {
                            int studentID = resultSet.getInt("ID");
                            String account = resultSet.getString("Account");
                            String name = resultSet.getString("Name");
                            String studentClass = resultSet.getString("Class");

                            String studentInfo = String.format("学生ID：%d, 账号：%s, 姓名：%s, 班级：%s",
                                    studentID, account, name, studentClass);
                            System.out.println(studentInfo);
                        }

                    }
                    if (q == 6) {
                        String sql = "SELECT * FROM Sheet";
                        Statement statement = conn.createStatement();
                        ResultSet resultSet = statement.executeQuery(sql);

                        while (resultSet.next()) {
                            int sheetID = resultSet.getInt("ID");
                            int examID = resultSet.getInt("ExamID");
                            int studentID = resultSet.getInt("StudentID");
                            String studentName = resultSet.getString("StudentName");
                            float studentGrade = resultSet.getFloat("StudentGrade");

                            System.out.print("成绩ID: " + sheetID);
                            System.out.print("  考试ID: " + examID);
                            System.out.print("  学生ID: " + studentID);
                            System.out.print("  学生姓名: " + studentName);
                            System.out.println("  学生成绩: " + studentGrade);

                        }
                    }
                    if (q == 7) {
                        break;
                    }

                } catch (SQLException e) {
                    //TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        if (Main.identity == 3) {
            System.out.println(Main.Name + "同学你好！现在是北京时间" + formattedDateTime);
            System.out.println("1.查看考试      2.参加考试     3.查看成绩");
            int q = scan.nextInt();
            String url = "jdbc:sqlserver://10.16.39.167:1433;databaseName=ExamSystem;integratedSecurity=false;encrypt=false;";
            Connection conn;
            try {
                conn = DriverManager.getConnection(url, "sa", "mark4567");
                Statement stat = conn.createStatement();
                if (q == 1) {
                    getExam.main();
                }
                if (q == 2) {
                    exam.main();
                }
                if (q == 3) {
                    int studentID = Integer.parseInt(Main.Account); // 假设给定的学生ID为12345

                    String sql = "SELECT * FROM Sheet WHERE StudentID = ?";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setInt(1, studentID);

                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        int sheetID = resultSet.getInt("ID");
                        int examID = resultSet.getInt("ExamID");
                        String studentName = resultSet.getString("StudentName");
                        float studentGrade = resultSet.getFloat("StudentGrade");


                        System.out.print("SheetID: " + sheetID);
                        System.out.print("   ExamID: " + examID);
                        System.out.print("   StudentName: " + studentName);
                        System.out.print("   StudentGrade: " + studentGrade);
                    }
                }
            } catch (SQLException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}

