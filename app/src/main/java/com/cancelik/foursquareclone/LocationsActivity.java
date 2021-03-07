package com.cancelik.foursquareclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class LocationsActivity extends AppCompatActivity {
    ListView locations;
    ArrayList<String> placeName;
    ArrayAdapter arrayAdapter;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_place){
            //İntent
            Intent intent = new Intent(LocationsActivity.this,CreatePlaceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.signOut){
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null){
                        Toast.makeText(LocationsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(LocationsActivity.this,SignUpActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_place,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        locations = findViewById(R.id.listview_locations_activity);
        placeName = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,placeName);
        locations.setAdapter(arrayAdapter);

        locations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LocationsActivity.this,DetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("name",placeName.get(i));
                startActivity(intent);
            }
        });

        download();
    }
    public void download(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Places");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                     if (e == null){
                         if (objects.size() > 0){
                             placeName.clear();
                             for (ParseObject object : objects){
                                 placeName.add(object.getString("name"));
                                 arrayAdapter.notifyDataSetChanged();

                             }
                         }
                     }
                     else {
                         Toast.makeText(LocationsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                     }
            }
        });
    }

}