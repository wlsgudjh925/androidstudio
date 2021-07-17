package com.cookandroid.project_daily_diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageButton btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("오늘 뭐하지?");

        final ArrayList<String> midList = new ArrayList<String>();
        ListView list = (ListView) findViewById(R.id.checklistView);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, midList);
        list.setAdapter(adapter);

        final EditText editChecklist = (EditText) findViewById(R.id.editChecklist);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String insertChecklist = editChecklist.getText().toString();
                insertChecklist = insertChecklist.replaceAll(" ", "");
                if(insertChecklist.equals("")){
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "내용을 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else{
                    midList.add(editChecklist.getText().toString());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btnSave = (ImageButton) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream outFs = openFileOutput("CheckList", Context.MODE_PRIVATE);
                    for(int i=0; i<midList.size(); i++){
                        String str = midList.get(i)+"\n";
                        outFs.write(str.getBytes());
                    }
                    outFs.close();
                    Toast.makeText(MainActivity.this, "저장되었습니다.", Toast.LENGTH_LONG).show();
                } catch (IOException e){}
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {
                midList.remove(i);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        Button btnDiary = (Button) findViewById(R.id.btnDiary);
        btnDiary.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

        File file = new File(getFilesDir(), "CheckList") ;
        FileReader fr = null ;
        BufferedReader bufrd = null ;
        String str ;

        if (file.exists()) {
            try {
                // open file.
                fr = new FileReader(file) ;
                bufrd = new BufferedReader(fr) ;

                while ((str = bufrd.readLine()) != null) {
                    midList.add(str) ;
                    adapter.notifyDataSetChanged();
                }
                bufrd.close() ;
                fr.close() ;
                Toast.makeText(getApplicationContext(), "체크리스트를 불러왔습니다!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace() ;
                Toast.makeText(getApplicationContext(), "저장된 파일이 없음", Toast.LENGTH_LONG).show();
            }
        }

    }
}