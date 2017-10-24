package org.communiquons.android.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /**
     * Utilities variable
     */
    Utilities utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Enable utilities
        utils = new Utilities(getApplicationContext());

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

        //Debug
        //utils.file_put_contents("test.txt", "This is a test content.");
        String fileContent = utils.file_get_contents("test.txt");
        Toast.makeText(getApplicationContext(), "FileContent: "+fileContent, Toast.LENGTH_LONG).show();

    }
}
