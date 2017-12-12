package com.niket.ToDoNiket;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TaskListActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private DBManager dbManager;
    private ImageView todoImage;
    private long _id;

    private ListView listView;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[]{DatabaseHelper._ID,
            DatabaseHelper.SUBJECT, DatabaseHelper.DESC, DatabaseHelper.DUEDATE};

    final int[] to = new int[]{R.id.id, R.id.title, R.id.desc, R.id.dueDateText};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_emp_list);


        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();



        //Setting listView to the list_view to display the task list.
        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        //Setting simple cursor adapter to display the task list from sqlite database to the list view.
        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListiner For List Items to perform the update and delete operations.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView titleTextView = (TextView) view.findViewById(R.id.title);
                TextView descTextView = (TextView) view.findViewById(R.id.desc);
                TextView dateTextView = (TextView) view.findViewById(R.id.dueDateText);

                String id = idTextView.getText().toString();
                String title = titleTextView.getText().toString();
                String desc = descTextView.getText().toString();
                String dueDate = dateTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), ModifyTaskActivity.class);
                modify_intent.putExtra("title", title);
                modify_intent.putExtra("desc", desc);
                modify_intent.putExtra("id", id);
                modify_intent.putExtra("dueDate", dueDate);

                startActivity(modify_intent);
            }
        });
    }

    /**
     * to inflate the options menu to add task
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * To add and to display completed task in seperate activity
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (item.getItemId()) {

            case R.id.add_record: {

                Intent add_mem = new Intent(this, AddTaskActivity.class);
                startActivity(add_mem);
                return true;

            }
            case R.id.task_done: {
                 Intent taskCompleted = new Intent(this, CompletedTaskActivity.class);
                 startActivity(taskCompleted);
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }

    }
// To change the by default status from 0 to 1.
    //Inserting and updating the database for status.

    public void changeIcon(View v) {


        // Updating the status

        View view = (View) v.getParent();
        TextView idTextView = (TextView) view.findViewById(R.id.id);
        TextView titleTextView = (TextView) view.findViewById(R.id.title);
        TextView descTextView = (TextView) view.findViewById(R.id.desc);
        TextView dateTextView = (TextView) view.findViewById(R.id.dueDateText);

        String id = idTextView.getText().toString();
        String title = titleTextView.getText().toString();
        String desc = descTextView.getText().toString();
        String dueDate = dateTextView.getText().toString();

        String todoTaskStatus ="1";
/*
        String updateTodoItemStatus = "UPDATE FROM " + DatabaseHelper.TABLE_NAME +
                " WHERE " + DatabaseHelper.STATUS + " = '" + todoTaskStatus + ' ';*/

        databaseHelper = new DatabaseHelper(TaskListActivity.this);
        SQLiteDatabase sqlDB = databaseHelper.getWritableDatabase();

        _id = Long.parseLong(id);
        dbManager.update(_id, title, desc, dueDate, todoTaskStatus);

        Log.d("Status row 1 :" + todoTaskStatus, "current");
        sqlDB.close();
    }
}