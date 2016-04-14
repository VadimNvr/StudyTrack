package com.studytrack.app.studytrack_v1.UniversitySearch.Filters.CitySearch;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;
import com.studytrack.app.studytrack_v1.MainActivity;
import com.studytrack.app.studytrack_v1.R;

import java.util.HashSet;
import java.util.Set;

public class CitiesSearchActivity extends AppCompatActivity {
    protected EditText inputSearch;
    protected ListView resultList;
    protected HashSet<String> chosenCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_city_search);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        chosenCities = (HashSet<String>) getIntent().getExtras().getSerializable("chosen_cities");

        inputSearch = (EditText) findViewById(R.id.search_input);
        resultList  = (ListView) findViewById(R.id.search_list);
        resultList.setDivider(null);

        SearchListAdapter adapter = new SearchListAdapter(this, chosenCities);
        resultList.setAdapter(adapter);
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String chosen = ((TextView) view.findViewById(R.id.city_text)).getText().toString();
                if (chosenCities.contains(chosen)) {
                    chosenCities.remove(chosen);
                } else {
                    chosenCities.add(chosen);
                }
                ((SearchListAdapter) resultList.getAdapter()).notifyDataSetChanged();
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int i, int i1, int i2) {
                ((SearchListAdapter) resultList.getAdapter()).Filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("chosen_cities", chosenCities);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_cities_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
