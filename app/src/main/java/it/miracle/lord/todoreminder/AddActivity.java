package it.miracle.lord.todoreminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import model.Todo;
import model.User;

public class AddActivity extends AppCompatActivity {

    private User currentUser = new User();
    private Button btnAdd;
    private Button btnView;
    private EditText txtTodo;
    private EditText txtTime;
    private Realm realmTodos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);
        btnAdd = findViewById(R.id.btn_add);
        btnView = findViewById(R.id.btn_view);
        txtTodo = findViewById(R.id.txt_todo);
        txtTime = findViewById(R.id.txt_time);
        realmConfiguration();
        currentUser.setUserId(getIntent().getIntExtra("userId", 0));
    }

    public void add(View view) {
        realmTodos.beginTransaction();
        Todo todo = realmTodos.createObject(Todo.class);
        todo.setName(txtTodo.getText().toString());
        todo.setTime(txtTime.getText().toString());
        todo.setUserId(currentUser.getUserId());
        realmTodos.commitTransaction();
        toastAddedTodo();
        txtTodo.setText("");
        txtTime.setText("");
    }

    private void toastAddedTodo() {
        Toast toast = Toast.makeText(this, "Todo added successfully", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void goToViewActivity(View view) {
        Intent intent = new Intent(AddActivity.this, ViewActivity.class);
        startActivity(intent);
    }

    private void realmConfiguration() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
        realmTodos = Realm.getDefaultInstance();
    }
}
