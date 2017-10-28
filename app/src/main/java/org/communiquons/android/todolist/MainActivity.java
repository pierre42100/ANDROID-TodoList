package org.communiquons.android.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /**
     * Tasks management object
     */
    TasksManagement tmanag;

    /**
     * Not task message
     */
    TextView noTaskMsg;

    /**
     * Tasks list
     */
    private ArrayList<Task> tasksList;

    /**
     * Tasks adapter
     */
    private TasksAdapter tasksAdapter;

    /**
     * ListView containing the tasks
     */
    private ListView listView;

    /**
     * The ID of the last task to delete
     */
    int last_task_to_delete = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create task management object
        tmanag = new TasksManagement(getApplicationContext());

        //Set the view
        setContentView(R.layout.activity_main);

        //Get no task message
        noTaskMsg = (TextView) findViewById(R.id.not_task_msg);

        //Make the create a task button lives
        FloatingActionButton btn_create_task = (FloatingActionButton) findViewById(R.id.btn_create_task);
        btn_create_task.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        enter_task_creation(view);
                    }
                }
        );

        //Refresh the current list of tasks
        refresh_tasks_list();
    }

    /**
     * Refresh the list of tasks
     */
    void refresh_tasks_list(){

        //Get the tasks list
        JSONArray tasks_list =  tmanag.get_all();

        //Create an array list
        tasksList = new ArrayList<>();

        //Process the list of tasks
        for(int i = 0; i < tasks_list.length(); i++){

            try {
                //Retrieve task information
                JSONObject task_infos = tasks_list.getJSONObject(i);

                //Extract informations about the task
                String task_name = task_infos.getString("name");
                boolean task_done = task_infos.getBoolean("done");

                //Add the task to the list
                tasksList.add(new Task(task_name, task_done));

            } catch (JSONException e){
                //Display error stack trace
                e.printStackTrace();
            }


        }

        //Check the tasks list length
        //If the list is empty, show the "no task" message
        if(tasks_list.length() == 0){
            noTaskMsg.setVisibility(View.VISIBLE);
        }
        else
            noTaskMsg.setVisibility(View.INVISIBLE);


        tasksAdapter = new TasksAdapter(this, tasksList);

        listView = (ListView) findViewById(R.id.tasks_list);
        listView.setAdapter(tasksAdapter);

    }

    /**
     * Handles task deletion
     *
     * @param view the view that was clicked
     */
    public void delete_task(View view){

        //Get the ID of the task to delete
        LinearLayout task_view_parent = (LinearLayout) view.getParent();
        last_task_to_delete = listView.indexOfChild(task_view_parent);
        last_task_to_delete += listView.getFirstVisiblePosition();

        //Create a confirmation dialog to delete task
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.msg_confirm_delete_task)
                .setPositiveButton(R.string.btn_confirm_task_delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //Delete task in the storage
                        tmanag.delete_task(last_task_to_delete);

                        //Remove the task from the list and inform tasksAdapter for the change
                        tasksList.remove(last_task_to_delete);
                        tasksAdapter.notifyDataSetChanged();

                    }
                })
                .setNegativeButton(R.string.btn_cancel_task_delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        // = DO NOTHING
                    }
                });
        // Create the AlertDialog object and display it
        builder.create();
        builder.show();

    }

    /**
     * Handles task status change
     *
     * @param view the view to update
     */
    public void task_update_status(View view){

        //Get the new status of the task
        boolean new_status = ((CheckBox) view).isChecked();

        //Get the ID of the task to update
        LinearLayout task_view_parent = (LinearLayout) view.getParent();
        int task_id = listView.indexOfChild(task_view_parent);
        task_id += listView.getFirstVisiblePosition();

        //Update the task in the storage
        tmanag.update_task_status(task_id, new_status);

        //Update the task in the task list
        tasksList.get(task_id).set_done(new_status);
        tasksAdapter.notifyDataSetChanged();
    }

    /**
     * Handles tasks creation request
     *
     * @param view The view that performed the request
     */
    public void enter_task_creation(View view){

        //New version
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_create_task, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setTitle(R.string.dialog_create_task_title);
        builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton(R.string.btn_create_task, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Get the input
                        EditText taskname =  dialogView.findViewById(R.id.new_task_name);
                        String new_task_name =  ""+taskname.getText();

                        //Check if the name is empty
                        if(new_task_name.length() < 2){
                            Toast.makeText(MainActivity.this, R.string.err_task_name_too_short,
                                    Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //Else we can create the task
                        tmanag.create_task(new_task_name);

                        //Refresh list
                        MainActivity.this.refresh_tasks_list();
                    }
                })
                .setNegativeButton(R.string.btn_cancel_dialog_create_task, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create();
        builder.show();

    }
}
