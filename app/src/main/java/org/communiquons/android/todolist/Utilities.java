package org.communiquons.android.todolist;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Application utilities
 *
 * Created by pierre on 10/24/17.
 */
class Utilities {

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
            fileStream.write(content.getBytes("UTF-8"));
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

    /**
     * Check where a file exists or not
     *
     * @param filename The name of the file to check
     * @return True if the file exists, false else
     */
    public boolean file_exists(String filename){
        //Try to open file
        FileInputStream fileInputStream;

        try{
            fileInputStream = appContext.openFileInput(filename);
            fileInputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();

            //It seems that the file does not exists
            return false;
        }

        //The file exists
        return true;
    }

    /**
     * Encode a string in UTF-8 or does not do anything in case of failure
     *
     * @param toconvert the string to convert
     * @return The result
     */
    String encode_utf_8(String toconvert){

        //Encode string into UTF-8
        String encodedString;

        try{
            encodedString = new String(toconvert.getBytes(), "UTF-8");
        }
        catch(UnsupportedEncodingException e){
            e.printStackTrace();
            Log.e("UtilitiesIssue", "Couldn't convert string to UTF8 !");
            return toconvert;
        }

        //Return result
        return encodedString;
    }
}
