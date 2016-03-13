package com.kam.slani.kamino;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.kam.slani.kamino.kamino.Resident;

import java.util.ArrayList;
import java.util.LinkedList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Residents extends AppCompatActivity {

    private static final String TAG = Resident.class.getName();
    public static final String RESIDENT = "resident";

    private ArrayList<Resident> residents;
    private ArrayAdapter arrayAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residents);


        ListView listView = (ListView) findViewById(R.id.listView);

        if (savedInstanceState == null  || !savedInstanceState.containsKey(RESIDENT)) {

            Intent intent = getIntent();
            ArrayList<String> residentsUrl = intent
                    .getStringArrayListExtra(MainActivity.RESIDENTS);
            residents = new ArrayList<>();
            arrayAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, residents);
            listView.setAdapter(arrayAdapter);

            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            GetAllResidtents getAllResidtents = new GetAllResidtents();
            getAllResidtents.execute(residentsUrl);
        } else {
            residents = savedInstanceState.getParcelableArrayList(RESIDENT);
            arrayAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, residents);
            listView.setAdapter(arrayAdapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Resident res = residents.get(position);
                Gson gson = new Gson();
                String resJson = gson.toJson(res);
                Intent intentResdientDetail = new Intent(getApplicationContext(),
                        ResidentDetail.class);
                intentResdientDetail.putExtra(RESIDENT, resJson);
                startActivity(intentResdientDetail);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(RESIDENT, residents);
        super.onSaveInstanceState(outState);
    }
    private class GetAllResidtents extends AsyncTask<ArrayList<String>, Integer, Void> {

        @Override
        protected Void doInBackground(ArrayList<String>... params) {
            KaminoRestClient kaminoRestClient = ServiceGenerator
                    .createService(KaminoRestClient.class);

            for (int i = 0; i < params[0].size(); i++) {
                String s = params[0].get(i);
                int id = Integer.parseInt(s.substring(s.lastIndexOf("/") + 1));
                residents.add(kaminoRestClient.getResident(id));
                publishProgress((int) ((i / (float) params[0].size()) * 100));
            }
            return  null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.INVISIBLE);
            arrayAdapter.notifyDataSetChanged();
        }
    }



}
