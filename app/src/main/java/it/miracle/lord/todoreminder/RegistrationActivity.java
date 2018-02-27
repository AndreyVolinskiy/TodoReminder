package it.miracle.lord.todoreminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import model.User;

public class RegistrationActivity extends AppCompatActivity {

    private User currentUser;
    private Button btnLogin;
    private Button btnRegister;
    private EditText txtName;
    private EditText txtPassword;
    private EditText txtPasswordRe;
    private TextView txtMessage;
    private Realm realmUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        txtName = findViewById(R.id.txt_name);
        txtPassword = findViewById(R.id.txt_password);
        txtPasswordRe = findViewById(R.id.txt_passwordRe);
        txtMessage = findViewById(R.id.txt_message);
        realmConfiguration();
    }

    public void register(View view) {
        realmUsers.beginTransaction();

//        Number maxId = realmUsers.where(User.class).max("id");
//        int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;

        String name = txtName.getText().toString();
        String password = txtPassword.getText().toString();
        String passwordRe = txtPasswordRe.getText().toString();
        User user = realmUsers.createObject(User.class);
//        User user = realmUsers.createObject(User.class, nextId);
        if (!name.equals("") && (!password.equals("")) && (!passwordRe.equals(""))) {
            if (password.equals(passwordRe)) {
                user.setName(name);
                user.setPassword(password);
//                user.setUserId(nextId);
                toastOk();
                currentUser = user;
                goToViewActivity();
            } else {
                toastDismatchPasswords();
                txtName.setText("");
                txtPassword.setText("");
                txtPasswordRe.setText("");
            }
        } else {
            toastMistake();
        }
        realmUsers.commitTransaction();
    }

    private void toastDismatchPasswords() {
        Toast toast = Toast.makeText(this, "Passwords don`t match, try again", Toast.LENGTH_LONG);
        toast.show();
    }

    private void goToViewActivity() {
        Intent intent = new Intent(RegistrationActivity.this, ViewActivity.class);
//        intent.putExtra("userId", currentUser.getUserId());
        startActivity(intent);
    }

    private void toastMistake() {
        Toast toast = Toast.makeText(this, "Something wrong, try again", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void toastOk() {
        Toast toast = Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void realmConfiguration() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
        realmUsers = Realm.getDefaultInstance();
    }
}
