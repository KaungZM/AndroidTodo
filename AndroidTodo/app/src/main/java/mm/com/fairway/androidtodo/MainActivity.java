package mm.com.fairway.androidtodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    TodoAdapter todoAdapter;
    ArrayList<Todo> list;
    EditText editText;
    CheckBox checkBox;
    TodoOpenHelper todoOpenHelper;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.editText);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        add = (Button) findViewById(R.id.add);
        todoOpenHelper = new TodoOpenHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        list = todoOpenHelper.getAll();
        if(todoAdapter == null) {
            todoAdapter = new TodoAdapter(this, list, new Runnable() {
                @Override
                public void run() {
                    Todo currentTodo = list.get(TodoAdapter.currentIndex);
                    editText.setText(currentTodo.getTask());
                    add.setText("Update");
                }
            }, new Runnable() {
                @Override
                public void run() {
                    Todo currentTodo = list.get(TodoAdapter.currentIndex);
                    currentTodo.setDoneState(!currentTodo.getDoneState());
                    todoOpenHelper.updateTodo(currentTodo);
                    onStart();
                }
            }, new Runnable() {
                @Override
                public void run() {
                    Todo currentTodo = list.get(TodoAdapter.currentIndex);
                    todoOpenHelper.deleteTodo(currentTodo);
                    onStart();
                }
            });
            listView.setAdapter(todoAdapter);
        } else {
            todoAdapter.setList(list);
            todoAdapter.notifyDataSetChanged();
        }
        editText.setText("");
        TodoAdapter.currentIndex = -1;
        add.setText("Add");
    }

    public void add(View view) {
        if(TodoAdapter.currentIndex == -1) {
            todoOpenHelper.addTodo(new Todo(0, editText.getText().toString(), 0));
        } else {
            list.get(TodoAdapter.currentIndex).setTask(editText.getText().toString());
            Todo currentTodo = list.get(TodoAdapter.currentIndex);
            todoOpenHelper.updateTodo(currentTodo);
        }
        onStart();
    }

    public void clear(View view) {
        todoOpenHelper.clearAll();
        onStart();
    }
}