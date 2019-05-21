package com.example.vabolari.login_register;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.security.auth.login.LoginException;


public class MainActivity extends Activity implements View.OnClickListener{


    Button btnReg;
    Button btnLogout;
    EditText editName, editIncome, editUsername;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getActionBar();
        actionBar.setLogo(R.drawable.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        editName = (EditText) findViewById(R.id.editName);
        editIncome = (EditText) findViewById(R.id.editIncome);
        editUsername = (EditText) findViewById(R.id.editUsername);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnReg = (Button) findViewById(R.id.btnReg);

        btnLogout.setOnClickListener(this);

        btnReg.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);
    }

    public void openUserRegister (View view)
    {
        startActivity(new Intent(this, UserRegister.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate() == true){
            displayUserDetails();

        }
        else {
            startActivity(new Intent(MainActivity.this, UserLogin.class));
        }
    }


    private  boolean authenticate() {

        return userLocalStore.getUserLoggedIn();
    }

    private  void displayUserDetails(){
        User user = userLocalStore.getLoggedInUser();

        editName.setText(user.name);
        editUsername.setText(user.username);
        editIncome.setText(user.income + "");

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);






                break;
            case R.id.btnReg:
                startActivity(new Intent(this, UserRegister.class));

                break;
        }
    }

    public void imageButton (View view) {
        Intent imageIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.deakin.edu.au/~vabolari/SIT104Ass2ABOVD1403/ABOVD1403.HTML"));
        startActivity(imageIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
