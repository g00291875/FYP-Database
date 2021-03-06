package work4;


import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URI;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


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
        byte[] array;


        String con = "jdbc:sqlserver://zlxgbmw91b.database.windows.net:1433;" +
                "database=songDatabase;" +
                "user=toor@zlxgbmw91b;" +
                "password=Zqlllx$8;";

        PreparedStatement sqlInsertName = null;

        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Get a reference to a container.
            // The container name must be lower case
            CloudBlobContainer container = blobClient.getContainerReference("muzikhost");

            // Create the container if it does not exist.
            container.createIfNotExists();
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }

//        try
//        {
//            // Retrieve storage account from connection-string.
//            CloudStorageAccount storageAccount =
//                    CloudStorageAccount.parse(storageConnectionString);
//
//            // Create the table client.
//            CloudTableClient tableClient = storageAccount.createCloudTableClient();
//
//            // Loop through the collection of table names.
//            for (String table : tableClient.listTables())
//            {
//                // Output each table name.
//                System.out.println(table);
//            }
//
//           // array = Files.readAllBytes(new File("C:\\the set\\a.mp3").toPath());
//        }
//        catch (Exception e)
//        {
//            // Output the stack trace.
//            e.printStackTrace();
//        }
//
//        // Create a permissions object.
//        BlobContainerPermissions containerPermissions = new BlobContainerPermissions();
//
//// Include public access in the permissions object.
//        containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);
//
//// Set the permissions on the container.
//        container.uploadPermissions(containerPermissions);


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
        /*************************/
        // Create a new customer entity.
//        CustomerEntity customer1 = new CustomerEntity("Harp", "Walter");
//        customer1.Email = "Walter@contoso.com";
//        customer1.PhoneNumber = "425-555-0101";

// Create the TableOperation object that inserts the customer entity.
      //  TableOperation insertOperation = TableOperation.Insert(customer1);

// Execute the insert operation.
     //   table.Execute(insertOperation);
        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Retrieve reference to a previously created container.
            CloudBlobContainer container = blobClient.getContainerReference("muzikhost");

            // Define the path to a local file.
            final String filePath = "C:\\the set\\testsong.mp3";

            // Create or overwrite the "myimage.jpg" blob with contents from a local file.
            CloudBlockBlob blob = container.getBlockBlobReference("testsong.mp3");
            File source = new File(filePath);
            blob.upload(new FileInputStream(source), source.length());
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }

        /********************************************/
        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Retrieve reference to a previously created container.
            CloudBlobContainer container = blobClient.getContainerReference("muzikhost");


            Path path = Paths.get("C://test//testsong.mp3");

            Files.createDirectories(path.getParent());

            try {
                Files.createFile(path);
            } catch (FileAlreadyExistsException e) {
                System.err.println("already exists: " + e.getMessage());
            }
            // Loop through each blob item in the container.
            for (ListBlobItem blobItem : container.listBlobs()) {
                // If the item is a blob, not a virtual directory.
                if (blobItem instanceof CloudBlob) {
                    // Download the item and save it to a file with the same name.
                    CloudBlob blob = (CloudBlob) blobItem;
                    URI uri = blobItem.getUri();
                    String blobpath = uri.getPath();
                    String idStr = blobpath.substring(blobpath.lastIndexOf('/')+1 );
                    try {
                        if(new String("testsong.mp3").equals(idStr)) {
                            blob.download(new FileOutputStream("C://test//mydownloaded.mp3"));
                        }
                    } catch (StorageException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }
        /***********************************/

//        try
//        {
//            // Retrieve storage account from connection-string.
//            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
//
//            // Create the blob client.
//            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
//
//            // Retrieve reference to a previously created container.
//            CloudBlobContainer container = blobClient.getContainerReference("muzikhost");
//
//            // Loop over blobs within the container and output the URI to each of them.
//            for (ListBlobItem blobItem : container.listBlobs()) {
//                System.out.println(blobItem.getUri());
//            }
//        }
//        catch (Exception e)
//        {
//            // Output the stack trace.
//            e.printStackTrace();
//        }
    }

}

