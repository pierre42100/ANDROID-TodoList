package org.communiquons.android.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /**
     * Tasks management object
     */
    TasksManagement tmanag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create task management object
        tmanag = new TasksManagement(getApplicationContext());

        //Set the view
        setContentView(R.layout.activity_main);

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
        Log.v("MainActivity Debug", tmanag.get_all().toString());

        //DEVELOPEMENT STAGE
        //Tasks are hard-coded, but it is temporary
        ArrayList<Task> tasksList = new ArrayList<>();

        //Define arbitrary tasks
        tasksList.add(new Task("Clean the table", false));
        tasksList.add(new Task("Set the table", true));
        tasksList.add(new Task("Call grand-father", true));
        tasksList.add(new Task("Call uncle", false));
        tasksList.add(new Task("Eat chocolate", false));

        TasksAdapter tasksAdapter = new TasksAdapter(this, tasksList);

        ListView listView = (ListView) findViewById(R.id.tasks_list);
        listView.setAdapter(tasksAdapter);

    }


}
