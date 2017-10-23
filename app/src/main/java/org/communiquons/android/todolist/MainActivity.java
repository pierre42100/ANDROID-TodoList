package org.communiquons.android.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }
}
