package mm.com.fairway.androidtodo;

import android.content.Context;
import android.media.Ringtone;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.sql.BatchUpdateException;
import java.util.ArrayList;

public class TodoAdapter extends BaseAdapter {
    final String tag = "test";
    public static int currentIndex = -1;
    Context context;
    ArrayList<Todo> list = new ArrayList<>();
    private Runnable taskRunnable;
    private Runnable doneRunnable;
    private Runnable deleteRunnable;

    public ArrayList<Todo> getList() {
        return list;
    }

    public void setList(ArrayList<Todo> list) {
        this.list = list;
    }

    public TodoAdapter(Context context, ArrayList<Todo> list, Runnable taskRunnable, Runnable doneRunnable, Runnable deleteRunnable) {
        this.context = context;
        this.list = list;
        this.taskRunnable = taskRunnable;
        this.doneRunnable = doneRunnable;
        this.deleteRunnable = deleteRunnable;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).getId();
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View rootView;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            rootView = inflater.inflate(R.layout.todo_item, null);
        } else {
            rootView = convertView;
        }
        TextView task = (TextView) rootView.findViewById(R.id.textView1);
        CheckBox done = (CheckBox) rootView.findViewById(R.id.checkBox);
        Button delete = (Button) rootView.findViewById(R.id.delete);
        task.setText(list.get(i).getTask().toString());
        done.setChecked(list.get(i).getDoneState());

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = i;
                taskRunnable.run();
            }
        });

        done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                currentIndex = i;
                doneRunnable.run();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = i;
                deleteRunnable.run();
            }
        });
        return rootView;
    }
}
