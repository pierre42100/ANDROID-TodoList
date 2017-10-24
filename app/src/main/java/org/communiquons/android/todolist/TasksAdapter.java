package org.communiquons.android.todolist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Tasks adapter
 *
 * Created by pierre on 10/24/17.
 */

public class TasksAdapter extends ArrayAdapter<Task> {

    /**
     * Class constructor
     */
    public TasksAdapter(Activity context, ArrayList<Task> tasks){
        //Initializate the TaskAdapter
        super(context, 0, tasks);
    }

    /**
     * Provide a view for an AdapterView
     * @param position The position in the list
     * @param convertView The recycled view to populate
     * @param parent The parent ViewGroup that is used for inflation
     * @return The View for the position in the Adapter View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Check if the existing view can be reused or not
        View listItemView = convertView;

        //Check if a view has to be inflated
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent,
                    false);
        }

        //Get the item in the list
        Task current_task = getItem(position);

        //Checkbox for the tasks done
        CheckBox task_checkbox = listItemView.findViewById(R.id.task_done_checkbox);

        //Set the checkbox as checked or not, depending of the current state
        task_checkbox.setChecked(current_task.is_done());

        //Name of the task
        //TextView task_name = (TextView) listItemView.findViewById(R.id.task_name);

        //Get name of the task
        task_checkbox.setText(current_task.getName());

        //Return result
        return listItemView;
    }


}
