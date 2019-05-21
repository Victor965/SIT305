package com.example.vabolari.login_register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class UserRegister extends Activity implements View.OnClickListener{

    Button btnRegister;
    EditText editName, editIncome, editUsername, editPassword, editRepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        getActionBar().setDisplayHomeAsUpEnabled(true);


        editName = (EditText) findViewById(R.id.editName);
        editIncome = (EditText) findViewById(R.id.editIncome);
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editRepassword = (EditText) findViewById(R.id.editRePassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);
    }

    public void openUserLogin (View view)
    {
        startActivity(new Intent(this, UserLogin.class));
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnRegister:
                String name = editName.getText().toString();
                int income = Integer.parseInt(editIncome.getText().toString());
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();

                User user = new User(name, income, username, password);

                registerUser(user);






                break;

        }
    }

    private void registerUser(User user){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.storeUserDataInBackground(user, new GetUserCallBack() {
            @Override
            public void done(User returnedUser) {
                startActivity(new Intent(UserRegister.this, UserLogin.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
