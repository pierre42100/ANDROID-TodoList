package org.communiquons.android.todolist;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Application utilities
 *
 * Created by pierre on 10/24/17.
 */
public class Utilities {

    /**
     * The application context
     */
    private Context appContext;

    /**
     * Public constructor
     *
     * @param appContext The context of the application context
     */
    public Utilities(Context appContext){
        this.appContext = appContext;
    }

    /**
     * Get and return the content of a file
     *
     * @param filename String The name of the file to read
     * @return String The content of the file
     */
    public String file_get_contents(String filename){

        FileInputStream inputStream;
        String result = "";

        try {
            inputStream = appContext.openFileInput(filename);

            //Read file content
            int currByte;
            do {

                currByte = inputStream.read();

                if(currByte != -1)
                    result += (char) currByte;

            } while(currByte != -1);


            inputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();

            Log.e("AppError", "ERROR accessing file " + filename + "! The app may become unstable");
        }

        //Return result
        return result;

    }

    /**
     * Write on a file
     *
     * @param filename String The name of the file to write
     * @param content String The content to append on the file
     * @return boolean True for a success, false else
     */
    public boolean file_put_contents(String filename, String content){

        FileOutputStream fileStream;

        //Try to write on the file
        try {
            //Open the file
            fileStream = appContext.openFileOutput(filename, Context.MODE_PRIVATE);
            fileStream.write(content.getBytes());
            fileStream.close();
        }
        catch (Exception e){
            //Return error
            e.printStackTrace();

            //This is a failure
            return false;
        }

        //This is a success
        return true;

    }

}
