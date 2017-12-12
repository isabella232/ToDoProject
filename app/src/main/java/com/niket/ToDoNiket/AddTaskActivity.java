package com.niket.ToDoNiket;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddTaskActivity extends Activity implements OnClickListener {

    //Declaring all view for assigning input and displaying output
    private Button addTodoBtn;
    private EditText subjectEditText;
    private EditText descEditText;
    private TextView mDateTextView;
    private ImageView mStatusImage;
    private DBManager dbManager;
    private DatePicker picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Record");

        setContentView(R.layout.activity_add_record);

        picker=(DatePicker)findViewById(R.id.datePicker1);
        subjectEditText = (EditText) findViewById(R.id.subject_edittext);



        descEditText = (EditText) findViewById(R.id.description_edittext);
        mDateTextView = (TextView) findViewById(R.id.dateTextView);
        addTodoBtn = (Button) findViewById(R.id.add_record);

        dbManager = new DBManager(this);
        dbManager.open();
        addTodoBtn.setOnClickListener(this);
    }

    /**
     * To add the task title and details on clicking
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_record:
                mDateTextView.setText(getCurrentDate());

                String name = "Invalid Task";
                if(subjectEditText.getText().toString().length() <= 2){
                    subjectEditText.setError("Must exceed 10 characters!");
                } else  {
                    name = subjectEditText.getText().toString();
                }
                /**
                 * Setting the validation and limits of characters for title and description.
                 */
                String desc = "Invalid Detail" ;
                if(descEditText.getText().toString().length() <= 2){
                    descEditText.setError("Must exceed 10 characters!");
                } else  {
                    desc = descEditText.getText().toString();
                }
                final String title = name;
                final String description = desc;
                final String date = mDateTextView.getText().toString();
                final String status = "0";
                dbManager.insert(title, description, date, status);
                Log.d("database"+status, "table details"+name);
                Intent main = new Intent(AddTaskActivity.this, TaskListActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);
                break;
        }
    }
// To get the date from spinner datepicker
    public String getCurrentDate(){
        StringBuilder builder=new StringBuilder();
        builder.append(picker.getYear()+"-");
        builder.append((picker.getMonth())+"-");//month is 0 based
        builder.append(picker.getDayOfMonth());

        return builder.toString();
    }

}