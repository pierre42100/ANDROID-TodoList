package org.communiquons.android.todolist;

import android.content.Context;
import android.util.Log;

/**
 * Tasks management
 *
 * Created by pierre on 10/25/17.
 */

class TasksManagement {

    /**
     * Objects utilities
     */
    private Utilities utils;

    /**
     * Task management constructor
     *
     * @param context The context where the task is being added
     */
    public TasksManagement(Context context){
        //Create utilities object
        utils = new Utilities(context);
    }

    /**
     * Get the tasks list
     *
     * @return
     */
    public void get_all(){
        //Check if the tasks list exist or not
        if(!utils.file_exists("tasks.json")){
            utils.file_put_contents("tasks.json", "{}");
        }


    }

    /**
     * Create a new task
     *
     * @param task_name The name of the created task
     * @return False in case of failure
     */
    public boolean create_task(String task_name){

        Log.v("TodoList debug", utils.file_get_contents("tasks.json"));


        return false;
    }
}
