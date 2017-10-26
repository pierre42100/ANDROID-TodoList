package org.communiquons.android.todolist;

/**
 * Task object
 *
 * Represents a single task object
 *
 * Created by pierre on 10/24/17.
 */

class Task {

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
    Task(String name, boolean done){

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
    boolean is_done(){
        return task_done;
    }

    /**
     * Mark the task as done or not done
     *
     * @param status The new status for the task
     */
    void set_done(boolean status){
        task_done = status;
    }
}
