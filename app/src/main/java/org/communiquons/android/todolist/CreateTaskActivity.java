package org.communiquons.android.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateTaskActivity extends AppCompatActivity {

    /**
     * Task management object
     */
    TasksManagement tmanag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        //Create the taskManagement object
        tmanag = new TasksManagement(getApplicationContext());

        //Get the submit button and make it lives
        Button submit_button = (Button) findViewById(R.id.new_task_submit);
        submit_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //Handle submitted form
                handle_submitted_form();
            }
        });
    }

    /**
     * Handles submitted form
     */
    public void handle_submitted_form(){

        //Get the input value
        EditText input = (EditText) findViewById(R.id.new_task_name);

        //Check if the value is empty
        String value = "" + input.getText();
        if(value.length() < 2) {
            Toast.makeText(getApplicationContext(), R.string.err_task_name_too_short, Toast.LENGTH_SHORT).show();

            return;
        }

        //Create the task
        if(!tmanag.create_task(value))
            Toast.makeText(getApplicationContext(), R.string.err_task_creation, Toast.LENGTH_SHORT).show();
        else {
            //Empty the input
            input.setText("");

            //Go back to the main activity
            back_main_activity();
        }

    }

    /**
     * Go back to the main activity
     */
    public void back_main_activity(){
        Intent intent = new Intent(getApplicationContext() , MainActivity.class);
        startActivity(intent);
    }
}
