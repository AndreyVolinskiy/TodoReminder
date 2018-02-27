package it.miracle.lord.todoreminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import model.Todo;
import model.User;

public class ViewActivity extends AppCompatActivity {

    private User currentUser = new User();
    private Button btnAdd;
    private Button btnExit;
    private Button btnDelete;
    private ListView listView;
    private Realm realmTodos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_layout);

        btnAdd = findViewById(R.id.btn_add);
        btnExit = findViewById(R.id.btn_exit);
        btnDelete = findViewById(R.id.btn_delete);
        listView = findViewById(R.id.list_view);
        currentUser.setUserId(getIntent().getIntExtra("userId", 0));
        realmConfiguration();
        showTodos(currentUser);
    }

    private void showTodos(User currentUser) {
        realmTodos.beginTransaction();
        RealmResults<Todo> realmResults = realmTodos
                .where(Todo.class)
                .equalTo("userId", currentUser.getUserId())
                .findAll();
        final ArrayAdapter<Todo> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, realmResults);
        listView.setAdapter(adapter);
        realmTodos.commitTransaction();
    }

    public void goToAddActivity(View view) {
        Intent intent = new Intent(ViewActivity.this, AddActivity.class);
        intent.putExtra("userId", currentUser.getUserId());
        startActivity(intent);
    }

    public void exit(View view) {
        this.finish();
    }

    public void deleteTodo(View view) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                TextView textView = (TextView) itemClicked;
                textView.setText("");
                int listId = textView.getId();
                realmTodos.beginTransaction();
                RealmResults<Todo> realmResults = realmTodos
                        .where(Todo.class)
                        .findAll();
                Todo todo = realmResults.get(listId);
                if (todo != null) {
                    todo.deleteFromRealm();
                }
                realmTodos.commitTransaction();
                showTodos(currentUser);
            }
        });
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
