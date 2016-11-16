package com.mersens.gank.utils;

import com.mersens.gank.app.Constans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import okhttp3.ResponseBody;

/**
 * Created by Mersens on 2016/11/10.
 */

public class PictureUtils {

    public static boolean saveToDisk(ResponseBody body) {
        try {
            File futureStudioIconFile = new File(Constans.ALBUM_PATH);
            if(!futureStudioIconFile.exists()){
                futureStudioIconFile.mkdirs();
            }
            File myCaptureFile = new File(futureStudioIconFile, UUID.randomUUID()+".jpg");
            if (!myCaptureFile.exists()) {
                myCaptureFile.createNewFile();
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[1024];
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(myCaptureFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

}
