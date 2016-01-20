package mySQL1;


import java.sql.*;
import java.io.*;

public class SaveImage
{
    private Connection connection;
    public static void main(String[] args)
    {
        DB db = new DB();
        Connection conn=db.dbConnect(
                "jdbc:jtds:sqlserver://localhost:3306/localsong","root","root");


        db.insertImage(conn,"C://theset//test.mp3");
      //  db.getImageData(conn);
    }

    private void connect() throws Exception
    {
        // Cloudscape database driver class name
        String driver = "com.mysql.jdbc.Driver";

        // URL to connect to addressbook database
        String url = "jdbc:mysql://localhost:3306/addressbook";

        // load database driver class
        Class.forName( driver );

        // connect to database
        connection = DriverManager.getConnection( url ,"root","root");

        // Require manual commit for transactions. This enables
        // the program to rollback transactions that do not
        // complete and commit transactions that complete properly.
        connection.setAutoCommit( false );
    }

}

class DB
{
    public DB() {}

    public Connection dbConnect(String db_connect_string,
                                String db_userid, String db_password)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    db_connect_string, db_userid, db_password);

            System.out.println("connected");
            return conn;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void insertImage(Connection conn,String img)
    {
        int len;
        String query;
        PreparedStatement pstmt;

        try
        {
            File file = new File(img);
            FileInputStream fis = new FileInputStream(file);
            len = (int)file.length();

            query = ("insert into localsongdatabase VALUES(?,?,?,?)");
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, 1);
            pstmt.setString(2,file.getName());
            pstmt.setInt(3, len);

            // Method used to insert a stream of bytes
            pstmt.setBinaryStream(3, fis, len);
            pstmt.executeUpdate();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void getImageData(Connection conn)
    {

        byte[] fileBytes;
        String query;
        try
        {
            query = "select data from tableimage";
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(query);
            if (rs.next())
            {
                fileBytes = rs.getBytes(1);
                OutputStream targetFile=
                        new FileOutputStream(
                                "d://filepath//new.JPG");

                targetFile.write(fileBytes);
                targetFile.close();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
};

