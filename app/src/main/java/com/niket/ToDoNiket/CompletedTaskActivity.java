package com.niket.ToDoNiket;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * To display completed task in seperate activity
 */

public class CompletedTaskActivity extends AppCompatActivity {
    //Declaraing  data members to handle all database CRUD operations.
    private DatabaseHelper databaseHelper;
    private DBManager dbManager;
    private ImageView todoImage;
    private SQLiteDatabase database;
    private long _id;

    private ListView listView;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[]{DatabaseHelper._ID,
            DatabaseHelper.SUBJECT, DatabaseHelper.DESC, DatabaseHelper.DUEDATE};

    final int[] to = new int[]{R.id.id, R.id.title, R.id.desc, R.id.dueDateText};

    /**
     * Fetching completed task list from database to show in list
     * using simple cursor adapter database a
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_emp_list);
//Opening database to fetch data from table
        dbManager = new DBManager(this);
        dbManager.open();

//Assigning records to cursor to access and put into list view.

        Cursor cursor = dbManager.taskCompletedFetch();



        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_completed_task, cursor, from, to, 0);
        adapter.notifyDataSetChanged();



        listView.setAdapter(adapter);

        // OnCLickListiner For List Items
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
//Explicit intent to navigate to modifyActivity to edit data and update database table.
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
     * option home to return back to main list
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.completed, menu);
        return true;
    }

    /**
     * option home to return back to main list
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (item.getItemId()) {

            case R.id.home_record: {

                Intent add_mem = new Intent(this, TaskListActivity.class);
                startActivity(add_mem);
                return true;

            }


            default:
                return super.onOptionsItemSelected(item);
        }

    }
    //nothing to do.
    public void changeIcon(View view){
//TODO in future for enhancement
    }
}
