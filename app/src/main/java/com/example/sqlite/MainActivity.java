package com.example.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler myDb;
    EditText editName, editSurename, editMarks, editId;
    Button btnAdd, btnView, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHandler(this);

        editName = (EditText) findViewById(R.id.idName);
        editSurename = (EditText) findViewById(R.id.idSurename);
        editMarks = (EditText) findViewById(R.id.idMarks);
        editId = (EditText) findViewById(R.id.idId);
        btnAdd = (Button) findViewById(R.id.idAdd);
        btnView = (Button) findViewById(R.id.idView);
        btnUpdate = (Button) findViewById(R.id.idUpdate);
        btnDelete = (Button) findViewById(R.id.idDelete);

        AddData();
        viewAll();
        UpdateData();
        deleteData();

    }

    public void AddData(){
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editName.getText().toString(),
                                editSurename.getText().toString(),
                                editMarks.getText().toString());

                        if(isInserted == true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data Not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll(){
        btnView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            showMesage("Error","Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ID : " + res.getString(0) + "\n");
                            buffer.append("Name : " + res.getString(1) + "\n");
                            buffer.append("Surename : " + res.getString(2) + "\n");
                            buffer.append("Marks : " + res.getString(3) + "\n\n");
                        }
                        //show all data
                        showMesage("Data",buffer.toString());
                    }
                }
        );
    }

    public void UpdateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.UpdateData(editId.getText().toString(),
                                editName.getText().toString(),
                                editSurename.getText().toString(),
                                editMarks.getText().toString());

                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data Not Updated",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void deleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editId.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data Not Deleted",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void showMesage(String title ,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
