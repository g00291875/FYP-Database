package mySQL2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.*;

/**
 * Created by user on 29/01/2016.
 */
class DB {
    Blob blob;
    int blobLength;
    byte[] blobAsBytes;

    public DB() {

    }

    public Connection dbConnect(String db_connect_string,
                                String db_userid, String db_password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    db_connect_string, db_userid, db_password);

            System.out.println("connected");
            return conn;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertImage(Connection conn, String img) {
        int len;
        String query;
        PreparedStatement pstmt;

        try {
            File file = new File(img);
            FileInputStream fis = new FileInputStream(file);
            len = (int) file.length();

            query = ("insert into localsongdatabase VALUES(?,?,?,?)");
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, 2);
            pstmt.setString(2, "song3");
            pstmt.setString(3, "artist3");

            // Method used to insert a stream of bytes
            pstmt.setBinaryStream(4, fis, len);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getImageData(Connection conn) {

        byte[] fileBytes;
        String query;
        try {
            query = "select fileData from localsongdatabase";

            //query = "select fileData from localsongdatabase where songName = 'song3'";
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(query);
            int cnt = 0;
            while(rs.next()) {
                fileBytes = rs.getBytes(1);
                OutputStream targetFile =
                        new FileOutputStream(
                                "C:\\test\\fromDB" + Integer.toString(cnt)+ ".mp3");

                targetFile.write(fileBytes);
                targetFile.close();
                fileBytes = null;
                cnt++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte [] getDBData(Connection conn) {

        byte[] fileBytes = null;
        String query;
        try {
            query = "select fileData from localsongdatabase where songName = 'song3'";
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(query);
            if (rs.next()) {
                fileBytes = rs.getBytes(1);


                /**8
                OutputStream targetFile =
                        new FileOutputStream(
                                "C:\\test\\fromDB.mp3");

                targetFile.write(fileBytes);
                targetFile.close();***/
            }

            Blob blob = rs.getBlob("SomeDatabaseField");

            int blobLength = (int) blob.length();
            byte[] blobAsBytes = blob.getBytes(1, blobLength);

//release the blob and free up memory. (since JDBC 4.0)
            blob.free();

        } catch (Exception e) {
            e.printStackTrace();
        }



        return fileBytes;
    }
}