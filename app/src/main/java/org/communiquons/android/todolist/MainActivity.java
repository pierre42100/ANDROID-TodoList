package org.communiquons.android.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
        Button btn_create_task = (Button) findViewById(R.id.btn_create_task);
        btn_create_task.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Intent createTask = new Intent(view.getContext(), CreateTaskActivity.class);
                        startActivity(createTask);
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
        ArrayList<Task> tasksList = new ArrayList<>();

        //Process the list of tasks
        for(int i = 0; i < tasks_list.length(); i++){

            try {
                //Retrieve task informations
                JSONObject task_infos = tasks_list.getJSONObject(i);

                //Extract informations about the task
                String task_name = task_infos.getString("name");
                boolean task_done = task_infos.getBoolean("done");

                //Add the task to the list
                tasksList.add(new Task(i, task_name, task_done));

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


        TasksAdapter tasksAdapter = new TasksAdapter(this, tasksList);

        ListView listView = (ListView) findViewById(R.id.tasks_list);
        listView.setAdapter(tasksAdapter);

    }

    /**
     * Handles task deletion
     *
     * @param view the view that was clicked
     */
    public void delete_task(View view){
        //Get the ID of the task to delete
        LinearLayout task_parent = (LinearLayout) view.getParent();
        TextView id_container = (TextView) task_parent.getChildAt(2);
        int task_id = Integer.decode(id_container.getText() + "");


    }
}
