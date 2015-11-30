package work1;


import java.sql.*;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;


public class Main {
    //private PreparedStatement sqlInsertName;

    public static void main(String[] args) {
        // Define the connection-string with your values
         final String storageConnectionString =
                "DefaultEndpointsProtocol=http;" +
                        "AccountName=muzikhost;" +
                        "AccountKey=LgxjWwFSki3yYz5InTSpBvEyW7T59rqng/1yop7AZLEDkKj1k+Ke+nI7u27bLg1jEdup2LG5VwOeDGEVLAAzGg==";

        Connection myConn = null;
        ResultSet myRs = null;
        int result;

//        DefaultEndpointsProtocol=https;
//        AccountName=storagesample;
//        AccountKey=<account-key>

//        String storageConnectionString =
//                "jdbc:sqlserver://zlxgbmw91b.database.windows.net:1433;" +
//                        "database=songDatabase;" +
//                        "user=toor@zlxgbmw91b;" +
//                        "password=Zqlllx$8";

        String con = "jdbc:sqlserver://zlxgbmw91b.database.windows.net:1433;" +
                "database=songDatabase;" +
                "user=toor@zlxgbmw91b;" +
                "password=Zqlllx$8;";

        PreparedStatement sqlInsertName = null;
//        try {
//            Connection connection = DriverManager.getConnection(storageConnectionString );
//
//            sqlInsertName = connection.prepareStatement(
//                    "INSERT INTO songTable ( id, song, artist ) " +
//                            "VALUES ( ?,  ? , ? )" );
//
//            sqlInsertName.setInt(1, 1);
//            sqlInsertName.setString( 2, "song2" );
//            sqlInsertName.setString( 3, "artist2" );
//            result = sqlInsertName.executeUpdate();
//
//            if ( result == 0 ) {
//                connection.rollback(); // rollback insert
//                System.out.println("roll back");
//                //return false;          // insert unsuccessful
//            }
//
//            connection.commit();
//            sqlInsertName.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        try
//        {
//            // Retrieve storage account from connection-string.
//            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
//
//            // Create the blob client.
//            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
//
//            // Get a reference to a container.
//            // The container name must be lower case
//            CloudBlobContainer container = blobClient.getContainerReference("thesongs");
//
//            // Create the container if it does not exist.
//            container.createIfNotExists();
//        }
//        catch (Exception e)
//        {
//            // Output the stack trace.
//            e.printStackTrace();
//        }

        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            // Create the table client.
            CloudTableClient tableClient = storageAccount.createCloudTableClient();

            // Create the table if it doesn't exist.
            String tableName = "SongName";
            CloudTable cloudTable = new CloudTable(tableName,tableClient);
            cloudTable.createIfNotExists();

            String artistTable = "Artist";
            cloudTable = new CloudTable(artistTable,tableClient);
            cloudTable.createIfNotExists();
            String blobTable = "songBlob";
             cloudTable = new CloudTable(blobTable,tableClient);
            cloudTable.createIfNotExists();
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }

        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            // Create the table client.
            CloudTableClient tableClient = storageAccount.createCloudTableClient();

            // Loop through the collection of table names.
            for (String table : tableClient.listTables())
            {
                // Output each table name.
                System.out.println(table);
            }
        }
        catch (Exception e)
        {
            // Output the stack trace.
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

