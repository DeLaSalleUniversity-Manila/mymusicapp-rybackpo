package com.example.rybackpo.mymusicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private String[] filenames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupPlaylist();
    }

    private void setupPlaylist(){
        ListView listview = (ListView) findViewById(R.id.song_list);
        filenames = getResources().getStringArray(R.array.filenames);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.list_item, filenames);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                Intent intent = new Intent(MainActivity.this, MusicService.class);
                intent.putExtra("filename", filenames[index]);
                intent.setAction(MusicService.ACTION_PLAY);
                startService(intent);
            }
        });
    }

    public void onClickStop(View view) {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(MusicService.ACTION_STOP);
        startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
