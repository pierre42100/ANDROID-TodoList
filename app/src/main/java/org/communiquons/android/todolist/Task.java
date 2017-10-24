package org.communiquons.android.todolist;

/**
 * Task object
 *
 * Represents a single task object
 *
 * Created by pierre on 10/24/17.
 */

public class Task {

    //Name of the task
    private String task_name;

    //Specify wether the task is done or not
    private boolean task_done;

    /**
     * Create a new task
     *
     * @param name is the name of the task
     * @param done specify wether the task is done or not
     */
    public Task(String name, boolean done){

        //Save the values
        task_name = name;
        task_done = done;

    }

    /**
     * Get the name of the task
     */
    public String getName(){
        return task_name;
    }

    /**
     * Get the status of the task (check if the task is done or not)
     */
    public boolean is_done(){
        return task_done;
    }
}
