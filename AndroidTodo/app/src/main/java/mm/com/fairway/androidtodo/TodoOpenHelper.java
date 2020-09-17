package mm.com.fairway.androidtodo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TableLayout;

import java.util.ArrayList;

public class TodoOpenHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "tododb";
    private static final int DBVERSION = 1;
    private static final String TABLE = "todo";
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, task TEXT, done INTEGER)";

    public TodoOpenHelper(Context c) {
        super(c, DBNAME, null, DBVERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE " + TABLE );
        onCreate(db);
    }

    public ArrayList<Todo> getAll() {
        ArrayList<Todo> todo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE, null);
        while(c.moveToNext()) {
            todo.add(new Todo(c.getInt(0), c.getString(1), c.getInt(2)));
        }
        db.close();
        return todo;
    }

    public void addTodo(Todo todo) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE + " (task, done) VALUES (?, ?)", new Object[] {todo.getTask(), todo.getDone()});
        db.close();
    }

    public void clearAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE);
        db.close();
    }

    public void deleteTodo(Todo todo) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE + " WHERE id = ?", new Object[] {todo.getId()});
        db.close();
    }

    public void updateTodo(Todo todo) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE + " SET task = ?, done = ? WHERE id = ?", new Object[] {todo.getTask(), todo.getDone(), todo.getId()});
        db.close();
    }
}
