
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.lang.Thread;

public class Zadanie1 {
    static Connection conn = null;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println("Error: Couldn't load driver");
            return;
        }
        
        System.out.println("Driver loaded");
        for (int i = 0; i < 12; ++i) {
            try {
                conn = DriverManager.getConnection("jdbc:mysql://10.0.10.3:3306/zadanie1?" +
                                                    "user=jmitura&password=password");
                break;
            } catch (Exception ex) {
                System.out.println("Error: Couldn't connect to DB");
                if (i < 11)
                    System.out.println("Retry in 5 sec");
            }
            try	{
				Thread.sleep(5000);
			} catch(InterruptedException ex)
            {
				Thread.currentThread().interrupt();
			}
        }
        
        
        System.out.println("Connection established");
        if (!create())
            return;

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Write: insert | select | exit");
            String command = in.nextLine();
            if (command.contains("insert")) {
                System.out.println("Give me 3 integers");
                int kol1, kol2, kol3;
                kol1 = in.nextInt();
                kol2 = in.nextInt();
                kol3 = in.nextInt();
                insert(kol1, kol2, kol3);
            } else if (command.contains("select")) {
                select();
            } else if (command.contains("exit")) {
                break;
            }
        }
    }

    static void select() {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT kol1, kol2, kol3 FROM dane");


            System.out.println("kol1\tkol2\tkol3");
            while (rs.next()) {
                System.out.println(Integer.toString(rs.getInt("kol1"))+"\t"+Integer.toString(rs.getInt("kol2"))+"\t" + Integer.toString(rs.getInt("kol3")));
            }

        } catch (Exception ex) {
            System.out.println("Error: Couldn't load data");
        }
    }

    static boolean insert(int kol1, int kol2, int kol3) {
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO `dane` (`kol1`, `kol2`, `kol3`) VALUES ('" + Integer.toString(kol1) + "', '"+ Integer.toString(kol2) + "', '" + Integer.toString(kol3) + "');");

        } catch (Exception ex) {
            System.out.println("Error: Couldn't insert data");
            return false;
        }
        return true;
    }

    static boolean create() {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DROP TABLE IF EXISTS `dane`;");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `dane` ("+
                                "`kol1` int(11) NOT NULL,"+
                                "`kol2` int(11) NOT NULL,"+
                                "`kol3` int(11) NOT NULL"+
                                ") ENGINE=InnoDB DEFAULT CHARSET=latin1;");
        } catch (Exception ex) {
            System.out.println("Error: Couldn't create table");
            return false;
        }
        return true;        
    }

}
