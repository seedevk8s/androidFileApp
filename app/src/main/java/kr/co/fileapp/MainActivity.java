package kr.co.fileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatePicker datePicker;
    EditText edtDiary;
    Button btnWrite;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        setTitle("간단 일기장 (수정)");

        datePicker = findViewById(R.id.datePicker);
        edtDiary = findViewById(R.id.edtDiary);
        btnWrite = findViewById(R.id.btnWrite);

        Calendar calendar = Calendar.getInstance();
        int cYear = calendar.get(Calendar.YEAR);
        int cMonth = calendar.get(Calendar.MONTH);
        int cDay = calendar.get(Calendar.DAY_OF_MONTH);

        //처음 실행시에 설정할 내용
        fileName = Integer.toString(cYear) +"_"+ Integer.toString(cMonth+1) +"_"+ Integer.toString(cDay) + ".txt";
        String str = readDiary(fileName);
        edtDiary.setText(str);


        datePicker.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                fileName = Integer.toString(i) +"_"+ Integer.toString(i1) +"_"+ Integer.toString(i2) +".txt";
                String str = readDiary(fileName);
                edtDiary.setText(str);
            }
        });


        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream outFs = openFileOutput(fileName, Context.MODE_PRIVATE);
                    String str = edtDiary.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();

                    Toast.makeText(getApplicationContext(), fileName +"이 저장됨.", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {

                }
            }
        });




    }

    private String readDiary(String fileName) {
        String diaryStr = null;
        FileInputStream inFs;

        try {
            inFs = openFileInput(fileName);
            byte[] txt = new byte[500];

            inFs.read(txt);
            inFs.close();

            diaryStr = (new String(txt)).trim();
            btnWrite.setText("수정하기");
        } catch (IOException e) {
            edtDiary.setHint("읽기 없음");
            btnWrite.setText("새로 저장");
        }
        return diaryStr;
    }
}



























