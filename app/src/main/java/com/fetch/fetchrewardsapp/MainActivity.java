package com.fetch.fetchrewardsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    List<String> groups, names1, names2, names3, names4;
    Map<String, List<String>> entries;
    ExpandableListAdapter listAdapter;
    Handler mainHandler = new Handler();
    Button button;
    EditText putLink;
ExpandableListView resultsArea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        resultsArea = findViewById(R.id.resultsArea);
        putLink = findViewById(R.id.putLink);
        groups = new ArrayList<>();
        names1 = new ArrayList<>();
        names2 = new ArrayList<>();
        names3 = new ArrayList<>();
        names4 = new ArrayList<>();
        entries = new HashMap<String, List<String>>();
        /*resultsArea.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {

            }
        });*/
        listAdapter = new MyExpandableListAdapter(MainActivity.this, entries, groups);
        resultsArea.setAdapter(listAdapter);
        groups.add("1");
        groups.add("2");
        groups.add("3");
        groups.add("4");
        entries.put(groups.get(0), names1);
        entries.put(groups.get(1), names2);
        entries.put(groups.get(2), names3);
        entries.put(groups.get(3), names4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultsArea.setVisibility(View.VISIBLE);
                putLink.setVisibility(View.INVISIBLE);
                String url = putLink.getText().toString();
                OkHttpClient okHttp = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                okHttp.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
MainActivity.this.runOnUiThread(new Runnable() {
    @Override
    public void run() {

    }
});
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
if(response.isSuccessful()){
    final String myResponse = response.body().string();
    for(int i = 0; i <= 4; i++) {
        Scanner scanner = new Scanner(myResponse);
        List<String> namesList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String entry = scanner.nextLine();
            try {
                JSONObject jsonObject = new JSONObject(entry);
                if(entry.contains("listId")){
                    if (jsonObject.getString("listId").equals(String.valueOf(i))) {
                        String name = jsonObject.getString("name");
                        name = name.replaceAll("\\D+", "");
                        if (name.length() >= 1) {
                            namesList.add(name);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        scanner.close();
        int j = i;
        List<Integer> entryIntegers = new ArrayList<>();
        for(String point : namesList){
            entryIntegers.add(Integer.parseInt(point));
        }
        Collections.sort(entryIntegers);
        for(int point : entryIntegers){
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(j == 1){
                        names1.add(String.valueOf(point));
                    } else if(j == 2){
                        names2.add(String.valueOf(point));
                    } else if(j == 3){
                        names3.add(String.valueOf(point));
                    } else if(j == 4){
                        names4.add(String.valueOf(point));
                    }
                }
            });
        }
    }
    MainActivity.this.runOnUiThread(new Runnable() {
        @Override
        public void run() {

        }
    });
}
                    }
                });
            }
        });
    }

}