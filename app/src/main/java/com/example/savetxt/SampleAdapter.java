package com.example.savetxt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SampleAdapter extends BaseAdapter {

    private List<ItemData> items;
    private LayoutInflater inflater;
    private Context ctx;

    SampleAdapter(Context context, List<ItemData> list) {
        ctx = context;
        items = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_list_view, parent, false);
        }

        final ItemData itemData = items.get(position);

        TextView title  = view.findViewById(R.id.nameSample);

        ImageView btnDel = view.findViewById(R.id.btnDel);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                delItem(itemData.getTitle());
                notifyDataSetChanged();


            }
        });
        title.setText(itemData.getTitle());

        return view;
    }

    public void delItem(String string) {
        final File logFile = new File(ctx.getApplicationContext().getExternalFilesDir(null),"Sample.txt");
        try {
            FileReader logReader = new FileReader(logFile);
            BufferedReader reader = new BufferedReader(logReader);
            String[] samples = reader.readLine().split(";");
            FileWriter logWriter = new FileWriter(logFile);

            for (String s : samples ) {
                if (s != string) {
                    logWriter.append(s + ";");
                }
            }
            logWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}