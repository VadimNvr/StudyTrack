package com.studytrack.app.studytrack_v1;

/**
 * Created by vadim on 15.01.16.
 */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class SerializeObject {

    public static void WriteSettings(Context context, Object data, String filename){
        FileOutputStream fOut = null;
        ObjectOutputStream os = null;

        try {
            fOut = context.openFileOutput(filename, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fOut);
            os.writeObject(data);
            Log.d("myFile", "Settings saved");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("myFile", "Settings not saved");
        }
        finally {
            try {
                if(os!=null)
                    os.close();
                if (fOut != null)
                    fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object ReadSettings(Context context, String filename){
        FileInputStream fis = null;
        ObjectInputStream is = null;
        Object data = null;

        try {
            fis = context.openFileInput(filename);
            is = new ObjectInputStream(fis);
            data = is.readObject();
            Log.d("myFile", "Settings loaded");
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.d("myFile", "Settings not loaded");
        }
        finally {
            try {
                if (is != null)
                    is.close();
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return data;
    }

}