package com.example.savetxt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final File logFile = new File(getApplicationContext().getExternalFilesDir(null),"Sample.txt");
        if (logFile.length() == 0) {
            try (FileWriter logWriter = new FileWriter(logFile)) {
                String[] sample = getResources().getStringArray(R.array.sample);
                for (String s : sample) {
                    logWriter.append(s).append(";");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loadSample(logFile);
        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try (FileWriter logWriterName = new FileWriter(logFile, true);){
                    BufferedWriter bufferWriter = new BufferedWriter(logWriterName);
                    bufferWriter.write(getString(R.string.nameAuthor)+";");
                    bufferWriter.close();
                    loadSample(logFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void loadSample(File file) {
        try {
            FileReader logReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(logReader);
            String[] samples = reader.readLine().split(";");
            List<ItemData> data = new ArrayList<>();
            for (String s : samples ) {
                data.add(new ItemData(s));
            }
            final SampleAdapter adapter = new SampleAdapter(this, data);
            ListView list = findViewById(R.id.list_sample);
            list.setAdapter(adapter);
            logReader.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
