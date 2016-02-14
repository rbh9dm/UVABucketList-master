package rbh9dm.cs2110.virginia.edu.androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Student User on 2/5/2016.
 */
public class NewAdapter extends ArrayAdapter<LineItem> {

    private List<LineItem> items; // Array we are synching to
    private Context context;
    int layoutId; // Layout folder we are copying from
    private class LineItemHolder {
        TextView tv;
        CheckBox chk;
    }

    public NewAdapter(Context context, List<LineItem> items,  int layoutId) {
        super(context, layoutId, items);
        this.context  = context;
        this.items = items;
        this.layoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LineItemHolder holder = null;

        // If the view does not yet exist, create one
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutId, parent, false);

            holder = new LineItemHolder();
            holder.tv =  (TextView) convertView.findViewById(R.id.name);
            holder.chk = (CheckBox) convertView.findViewById(R.id.chk);

            convertView.setTag(holder);

            // This makes it so the items in our array will update when we click the checkboxes
            holder.chk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    LineItem line = (LineItem) cb.getTag();
                    line.setComplete(cb.isChecked());
                }
            });
        }
        else {
            holder = (LineItemHolder) convertView.getTag();
        }
        // Find the appropriate item in our array
        LineItem item = items.get(position);

        // Create the display according to our items attributes
        holder.tv.setText(item.getName());
        holder.chk.setChecked(item.isComplete());
        holder.chk.setTag(item);

        return convertView;
    }
}








