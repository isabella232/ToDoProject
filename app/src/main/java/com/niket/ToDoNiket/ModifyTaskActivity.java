package com.niket.ToDoNiket;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * public class to perform modification and updation in table
 */
public class ModifyTaskActivity extends Activity implements OnClickListener {

    private EditText titleText;
    private Button updateBtn, deleteBtn;
    private EditText descText;
    private TextView dateText;
    private long _id;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");

        setContentView(R.layout.activity_modify_record);

        dbManager = new DBManager(this);
        dbManager.open();

        titleText = (EditText) findViewById(R.id.subject_edittext);
        descText = (EditText) findViewById(R.id.description_edittext);
        dateText = (TextView)findViewById(R.id.dateTextModify);


        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        String date = intent.getStringExtra("dueDate");

        _id = Long.parseLong(id);

        titleText.setText(name);
        descText.setText(desc);
        dateText.setText(date);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    /**
     * to update and to delete button created andd performing the operations
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                String title = titleText.getText().toString();
                String desc = descText.getText().toString();
                String date =  dateText.getText().toString();
                String status = "0";
                dbManager.update(_id, title, desc, date,status);
                this.returnHome();
                break;

            case R.id.btn_delete:
                dbManager.delete(_id);
                this.returnHome();
                break;
        }
    }
//Explicite intent to return back to the main list
    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), TaskListActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
