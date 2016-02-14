package rbh9dm.cs2110.virginia.edu.androidproject;

import android.app.LauncherActivity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    /*
     * Array of LineItems. LineItems hold the data regarding each item on the list (i.e. name, description, whether complete)
     */
    public final static String POSITION = "position";

    public static ArrayList<LineItem> items = new ArrayList<>();
    private NewAdapter adapter;
    private ListView listView;

    private static final String ITEM_MESSAGE = "items";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            items = (ArrayList<LineItem>) savedInstanceState.getSerializable(ITEM_MESSAGE);
            adapter = new NewAdapter(this, items, R.layout.main_listview);
            listView = (ListView) findViewById(R.id.listview);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);

                    intent.putExtra(POSITION, position);

                    startActivityForResult(intent, 0);
                }
            });
        }

        // Attempt to fill the array by reading in from the file
        // Data in the file is listed in the following order: item name, description, whether complete (1=complete, 0=incomplete)
        if(items.isEmpty()) {
            try {
                String str = "";

                // R.raw.info refers to info.txt, the file we are reading from. It is located in res -> raw.
                InputStream is = this.getResources().openRawResource(R.raw.info);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                if (is != null) {
                    // Checks whether we have reached the end of the file
                    while ((str = reader.readLine()) != null) {
                        // Read the next three lines from the file to create a new LineItem
                        LineItem item = new LineItem(str, reader.readLine(), Integer.parseInt(reader.readLine()));
                        // Adds the new LineItem to our LineItem array
                        items.add(item);
                    }
                }
                is.close();
            } catch (IOException e) {
            }

            // Sets up the adapter and listView. This displays a list based on the contents of our LineItem array
            // Note: changing the contents of the array will cause the list on the screen to change
            // Likewise, tapping the checkbox on the screen will change LineItem's 'complete' field to the new value
            adapter = new NewAdapter(this, items, R.layout.main_listview);
            listView = (ListView) findViewById(R.id.listview);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);

                    intent.putExtra(POSITION, position);

                    startActivityForResult(intent, 0);
                }
            });
        }
        listView.setAdapter(adapter);
    }

    // Method gets called whenever a list item is tapped (not called when checkbox is tapped though).
    // Eventually, we need to make it so this method brings us to a "more info" screen.

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null && data.getIntExtra(Main2Activity.RESULT,0) == 1) {
            adapter.notifyDataSetChanged();
        }
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
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ITEM_MESSAGE, items);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
    }
}

// resources
// http://www.javacodegeeks.com/2014/01/android-tutorial-two-methods-of-passing-object-by-intent-serializableparcelable.html
// http://www.survivingwithandroid.com/2013/02/android-listview-adapter-checkbox-item_7.html
// http://www.mysamplecode.com/2012/07/android-listview-checkbox-example.html
// http://www.survivingwithandroid.com/2012/10/android-listview-custom-adapter-and.html
// http://stackoverflow.com/questions/13643940/refresh-listview-after-updating-in-another-activity
// Android Developers Website

