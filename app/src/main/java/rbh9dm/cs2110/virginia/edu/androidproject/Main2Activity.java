package rbh9dm.cs2110.virginia.edu.androidproject;

import android.app.Activity;
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

public class Main2Activity extends Activity {
    private int position;
    private String name;
    private String description;
    private boolean checked;
    private boolean old_checked;

    private static final String POS = "pos";
    private static final String NAME = "name";
    private static final String DES = "des";
    private static final String CHK = "chk";
    private static final String OLD_CHK = "old_chk";

    public static final String RESULT = "result";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt(POS);
            name = savedInstanceState.getString(NAME);
            description = savedInstanceState.getString(DES);
            checked = savedInstanceState.getBoolean(CHK);
            old_checked = savedInstanceState.getBoolean(OLD_CHK);
        }
        else {
            Intent intent = getIntent();
            position = intent.getIntExtra(MainActivity.POSITION, 0);
            name = MainActivity.items.get(position).getName();
            description = MainActivity.items.get(position).getDescription();
            checked = MainActivity.items.get(position).isComplete();
            old_checked = checked;
        }

        ret();

        TextView aux = (TextView) findViewById(R.id.description);
        aux.setText(description);
        aux = (TextView) findViewById(R.id.title);
        aux.setText(name);
        CheckBox chk = (CheckBox) findViewById(R.id.checkBox);
        chk.setChecked(checked);
    }

    public void clicked(View view){
        switch(view.getId()){
            case R.id.checkBox:
                // changes the state of the item
                CheckBox chk = (CheckBox) findViewById(R.id.checkBox);
                MainActivity.items.get(position).setComplete(chk.isChecked());
                checked = chk.isChecked();
                ret();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void ret() {
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        if(old_checked != checked) {
            intent.putExtra(RESULT, 1);
        }
        else {
            intent.putExtra(RESULT, 0);
        }
        setResult(RESULT_OK, intent);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POS, position);
        outState.putString(NAME, name);
        outState.putString(DES, description);
        outState.putBoolean(CHK, checked);
        outState.putBoolean(OLD_CHK, old_checked);
    }
}
