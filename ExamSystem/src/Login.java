import java.util.Scanner;
import java.lang.*;
import java.sql.*;

public class Login {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("#############################");
        System.out.println("#  请输入你的身份 账号 密码:     #");
        System.out.println("#  管理员-1 教师-2 学生-3       #");
        System.out.println("#  如   3  30001 30001       #");
        System.out.println("#############################");
        Main.identity = scan.nextInt();
        Main.Account = scan.next();
        Main.Password = scan.next();
        String url = "jdbc:sqlserver://10.16.39.167:1433;databaseName=ExamSystem;integratedSecurity=false;encrypt=false;";
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, "sa", "mark4567");
            Statement stat = conn.createStatement();//创建一个 Statement 对象来将 SQL 语句发送到数据库。
            if (Main.identity == 1) {
                ResultSet resultSet = stat.executeQuery("select * from Admin");
                while (resultSet.next()) {
                    if (Main.Account.equals(resultSet.getString("Account"))) {
                        resultSet.getString("Account");
                        if (Main.Password.equals(resultSet.getString("Password"))) {
                            System.out.println("登录成功！");
                            Main.Name = resultSet.getString("Name");
                            Main.Loginsuccess = 1;
                            break;
                        }
                        while (!Main.Password.equals(resultSet.getString("Password"))) {
                            System.out.println("密码错误！请重新输入：");
                            Main.Password = scan.next();
                            if (Main.Password.equals(resultSet.getString("Password"))) {
                                System.out.println("登录成功！");
                                Main.Name = resultSet.getString("Name");
                                Main.Loginsuccess = 1;
                                break;
                            }
                        }
                    }

                }
            }
            if (Main.identity == 2) {
                ResultSet resultSet = stat.executeQuery("select * from Teacher");
                while (resultSet.next()) {
                    if (Main.Account.equals(resultSet.getString("Account"))) {
                        resultSet.getString("Account");
                        if (Main.Password.equals(resultSet.getString("Password"))) {
                            System.out.println("登录成功！");
                            Main.Name = resultSet.getString("Name");
                            Main.Loginsuccess = 1;
                            break;
                        }
                        while (!Main.Password.equals(resultSet.getString("Password"))) {
                            System.out.println("密码错误！请重新输入：");
                            Main.Password = scan.next();
                            if (Main.Password.equals(resultSet.getString("Password"))) {
                                System.out.println("登录成功！");
                                Main.Name = resultSet.getString("Name");
                                Main.Loginsuccess = 1;
                                break;
                            }
                        }
                    }

                }
            }
            if (Main.identity == 3) {
                ResultSet resultSet = stat.executeQuery("select * from Student");
                while (resultSet.next()) {
                    if (Main.Account.equals(resultSet.getString("Account"))) {
                        resultSet.getString("Account");
                        if (Main.Password.equals(resultSet.getString("Password"))) {
                            System.out.println("登录成功！");
                            Main.Name = resultSet.getString("Name");
                            Main.Loginsuccess = 1;
                            break;
                        }
                        while (!Main.Password.equals(resultSet.getString("Password"))) {
                            System.out.println("密码错误！请重新输入：");
                            Main.Password = scan.next();
                            if (Main.Password.equals(resultSet.getString("Password"))) {
                                System.out.println("登录成功！");
                                Main.Name = resultSet.getString("Name");
                                Main.Loginsuccess = 1;
                                break;
                            }
                        }
                    }

                }
            }

        } catch (SQLException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }// 连接数据库cpp
    }


}
