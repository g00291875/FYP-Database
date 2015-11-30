package work2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by user on 30/11/2015.
 */
public class MuzikHostSong {
    public String songName;
    public String ArtistName;


    public  MuzikHostSong(){

    }


    public byte[] inputStreamToByteArray(InputStream inStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inStream.read(buffer)) > 0) {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }
}
