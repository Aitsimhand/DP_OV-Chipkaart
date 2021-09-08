import java.sql.*;

public class Main {
    public static void main(String[] args){
        try {
            Connection myConn = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip?user=postgres&password=rJFQu34u");
            Statement myStmnt = myConn.createStatement();
            ResultSet myRs = myStmnt.executeQuery("SELECT * from reiziger");
            System.out.println("Alle reizigers:");

            while(myRs.next()){

                System.out.println("#" + myRs.getString(1) + ": "
                        + myRs.getString(2) + ". " + myRs.getString(4)
                        + " " + "(" + myRs.getString(5) + ")");
            }

        }

        catch (Exception e){
            e.printStackTrace();
        }

    }

}
