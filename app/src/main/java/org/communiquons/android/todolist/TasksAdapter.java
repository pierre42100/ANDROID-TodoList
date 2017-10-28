package org.communiquons.android.todolist;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Tasks adapter
 *
 * Created by pierre on 10/24/17.
 */

class TasksAdapter extends ArrayAdapter<Task> {

    /**
     * Main activity object
     */
    MainActivity mainActivity;

    /**
     * Update status listener
     */
    View.OnClickListener uSatutsListener;

    /**
     * Delete task listener
     */
    View.OnLongClickListener dDaskListener;

    /**
     * Class constructor
     */
    TasksAdapter(Activity context, ArrayList<Task> tasks){
        //Initializate the TaskAdapter
        super(context, 0, tasks);

        //Create listeners
        mainActivity = (MainActivity) context;

        //Update status listener
        uSatutsListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.task_update_status(v);
            }
        };

        //Delete task listner
        dDaskListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mainActivity.delete_task(v);
                return false;
            }
        };
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

        //Get name of the task
        task_checkbox.setText(current_task.getName());

        //Make the checkbox lives
        listItemView.findViewById(R.id.task_done_checkbox).setOnClickListener(uSatutsListener);
        listItemView.findViewById(R.id.task_done_checkbox).setOnLongClickListener(dDaskListener);

        //Return result
        return listItemView;
    }
}
