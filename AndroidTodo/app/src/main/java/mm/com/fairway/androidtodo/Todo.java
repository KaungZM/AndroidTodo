package mm.com.fairway.androidtodo;

public class Todo {
    private int id;
    private String task;
    private int done;

    public Todo(int id, String task, int done) {
        this.id = id;
        this.task = task;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public void setDoneState(boolean doneState) {
        this.done = (doneState)? 1 : 0;
    }

    public boolean getDoneState() {
        return (this.done == 1)? true : false;
    }

}
