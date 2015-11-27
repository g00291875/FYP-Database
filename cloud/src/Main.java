import java.sql.*;

public class Main {
    //private PreparedStatement sqlInsertName;

    public static void main(String[] args) {
        Connection myConn = null;
        ResultSet myRs = null;
        int result;

        String url = "jdbc:sqlserver://zlxgbmw91b.database.windows.net:1433;"+
         "database=songDatabase;user=toor@zlxgbmw91b;" +
                "password=Zqlllx$8;";

        String con = "jdbc:sqlserver://zlxgbmw91b.database.windows.net:1433;" +
                "database=songDatabase;" +
                "user=toor@zlxgbmw91b;" +
                "password=Zqlllx$8;";

        PreparedStatement sqlInsertName = null;
        try {
            Connection connection = DriverManager.getConnection( url);

            sqlInsertName = connection.prepareStatement(
                    "INSERT INTO songTable ( id, song, artist ) " +
                            "VALUES ( ?,  ? , ? )" );

            sqlInsertName.setInt(1, 1);
            sqlInsertName.setString( 2, "song1" );
            sqlInsertName.setString( 3, "artist1" );
            result = sqlInsertName.executeUpdate();

            if ( result == 0 ) {
                connection.rollback(); // rollback insert
                System.out.println("roll back");
                //return false;          // insert unsuccessful
            }

            connection.commit();
            sqlInsertName.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }



                    //"encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            // 1. Get a connection to database
//        try {
//            myConn = DriverManager.getConnection(con);
//
//            // 2. Prepare statement
//            sqlInsertName = myConn.prepareStatement("select * from test" +
//                    "");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//       // ResultSet result = null;
//        try {
//            sqlInsertName.setString( 1, "song1" );
//            sqlInsertName.setString( 2, "artist1" );
//            int result = sqlInsertName.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}

