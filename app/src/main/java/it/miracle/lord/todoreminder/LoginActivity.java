package it.miracle.lord.todoreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import model.User;

public class LoginActivity extends AppCompatActivity {

    private User currentUser;
    private final String MESSAGE = "There are no registered users, please register ";
    private Button btnLogin;
    private Button btnRegister;
    private EditText txtName;
    private EditText txtPassword;
    private TextView txtMessage;
    private Realm realmUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        txtName = findViewById(R.id.txt_name);
        txtPassword = findViewById(R.id.txt_password);
        txtMessage = findViewById(R.id.txt_message);
        realmConfiguration();
        toastIfNotRegistered();
//        deleteAllUsers();
//        findAllUsers();
    }

    private void deleteAllUsers() {
        realmUsers.beginTransaction();
        realmUsers.where(User.class).findAll().deleteAllFromRealm();
        realmUsers.commitTransaction();
    }

    private void findAllUsers() {
        realmUsers.beginTransaction();
        RealmResults<User> users = realmUsers
                .where(User.class)
                .findAll();
        realmUsers.commitTransaction();
        for (User user : users) System.out.println(user);
    }

    private void toastIfNotRegistered() {
        if (realmUsers.isEmpty()) {
            Toast toast = Toast.makeText(this, MESSAGE, Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        }
    }

    private void realmConfiguration() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
        realmUsers = Realm.getDefaultInstance();
    }

    public void login(View view) {
        realmUsers.beginTransaction();
        String name = txtName.getText().toString();
        String password = txtPassword.getText().toString();
        RealmResults<User> users = realmUsers.
                where(User.class)
                .equalTo("name", name)
                .and()
                .equalTo("password", password)
                .findAll();
        if (!users.isEmpty()) {
            toastOk();
            currentUser = users.first();
            Intent intent = new Intent(LoginActivity.this, ViewActivity.class);
            startActivity(intent);
        } else {
            toastMistake();
        }
        txtName.setText("");
        txtPassword.setText("");
        realmUsers.commitTransaction();
    }

    private void toastMistake() {
            Toast toast = Toast.makeText(this, "Something wrong, try again", Toast.LENGTH_SHORT);
            toast.show();
        }

    private void toastOk() {
        Toast toast = Toast.makeText(this, "Successful", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void goToViewActivity(View view) {
        Intent intent = new Intent(LoginActivity.this, ViewActivity.class);
        intent.putExtra("userName", currentUser.getName());
        intent.putExtra("userPassword", currentUser.getPassword());
        startActivity(intent);
    }

    public void goToRegisterActivity(View view) {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }
}

