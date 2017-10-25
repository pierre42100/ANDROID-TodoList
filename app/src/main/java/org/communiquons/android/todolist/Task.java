package org.communiquons.android.todolist;

/**
 * Task object
 *
 * Represents a single task object
 *
 * Created by pierre on 10/24/17.
 */

class Task {

    //Number of the task
    private int task_number;

    //Name of the task
    private String task_name;

    //Specify wether the task is done or not
    private boolean task_done;

    /**
     * Create a new task
     *
     * @param num is the number of the task
     * @param name is the name of the task
     * @param done specify wether the task is done or not
     */
    Task(int num, String name, boolean done){

        //Save the values
        task_number = num;
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
     * Get the number of the task
     */
    int get_number(){
        return task_number;
    }
}
