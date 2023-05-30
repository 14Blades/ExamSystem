import java.util.Scanner;
import java.lang.*;
public class Main {
     public static String Account;
     public static String Password;
     public static String Name;
     public static int identity;
     public static int Loginsuccess=0;

    public static void main(String[] args)
    {
        Login.main(new String[]{});
        if (Loginsuccess == 0) {
            System.exit(0);
        }
        Menu.main(new String[]{});
    }


}