package org.communiquons.android.todolist;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
     * Default content of the tasks JSON file
     */
    private String defaultFileContent = "{tasks:[]}";

    /**
     * Task management constructor
     *
     * @param context The context where the task is being added
     */
    TasksManagement(Context context){
        //Create utilities object
        utils = new Utilities(context);
    }

    /**
     * Get the tasks list
     *
     * @return The list of tasks, as a JSONArray
     */
    JSONArray get_all(){
        //Check if the tasks list exist or not
        if(!utils.file_exists("tasks.json")){
            reset_tasks_file();
        }

        JSONObject file_json = new JSONObject();

        //Try to decode JSON array
        try {
            file_json = new JSONObject(utils.file_get_contents("tasks.json"));
        }
        catch (JSONException e){
            //Display error
            e.printStackTrace();

            //Reset JSON file
            reset_tasks_file();
        }

        //Check for the task list
        if(!file_json.has("tasks")){
            Log.e("TasksManagement", "Tasks object not found ! Need to recreate file");
            reset_tasks_file();
            return new JSONArray();
        }

        //Returns tasks lis
        try{
            return file_json.getJSONArray("tasks");
        }
        catch (JSONException e){

            Log.e("TasksManagement", "Couldn't extract tasks array! Need to recreate file");
            e.printStackTrace();

            //Reset JSON file
            reset_tasks_file();

            //Return empty list
            return new JSONArray();
        }
    }

    /**
     * Saves a new tasks list
     *
     * @param taskslist The new list of tasks
     * @return true in case of success
     */
    private boolean save_new_tasks_list(JSONArray taskslist){

        //Generate new JSON file
        JSONObject newfile = new JSONObject();

        try {
            newfile.put("tasks", taskslist);
        }
        catch (JSONException e){
            e.printStackTrace();

            //It is a failure
            return false;
        }

        //Export the JSON object to a string
        String new_file_content = newfile.toString();

        //Save the new file
        if(!utils.file_put_contents("tasks.json", new_file_content)) {
            return false;
        }

        //Else it is a success
        return true;
    }

    /**
     * Create a new task
     *
     * @param task_name The name of the created task
     * @return False in case of failure
     */
    boolean create_task(String task_name){

        //Get the current list of tasks
        JSONArray tasksList = get_all();

        //Create the new task
        JSONObject newTask = new JSONObject();
        try {
            newTask.put("name", task_name);
            newTask.put("done", false);
        } catch (JSONException e){
            e.printStackTrace();

            //This is a failure
            return false;
        }

        //Append the new task to the list
        tasksList.put(newTask);

        //Save the new tasks list
        save_new_tasks_list(tasksList);

        //In case of success
        return true;
    }

    /**
     * Delete a task specified by its ID
     *
     * @param task_id The ID of the task to delete
     * @return False in case of failure
     */
    boolean delete_task(int task_id){
        //Get the current list of tasks
        JSONArray tasks_list = get_all();

        //Try to delete the task
        tasks_list.remove(task_id);

        //Save the new list of tasks
        save_new_tasks_list(tasks_list);

        //Success
        return true;
    }

    /**
     * Update a task status specified by its ID
     *
     * @param task_id The ID of the task to update
     * @param new_status The new status of the task
     * @return False in case of failure
     */
    boolean update_task_status(int task_id, boolean new_status){

        //Get the current list of tasks
        JSONArray tasks_list = get_all();

        //Try to perform operation
        try {
            //Get the current task
            JSONObject new_task = tasks_list.getJSONObject(task_id);

            new_task.put("done", new_status);

            //Push new task to task list
            tasks_list.put(task_id, new_task);

        } catch (JSONException e){
            e.printStackTrace();
            return false;
        }

        //Save the new list of tasks
        save_new_tasks_list(tasks_list);

        //Success
        return true;
    }

    /**
     * Reset tasks file to its default values
     */
    private void reset_tasks_file(){
        utils.file_put_contents("tasks.json", defaultFileContent);
    }
}
