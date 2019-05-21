package com.example.vabolari.login_register;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;



public class UserLogin extends Activity implements View.OnClickListener {

    Button btnLogin;
    EditText editUsername, editPassword;
    TextView tReg;
    UserLocalStore userLocalStore;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        getActionBar().setDisplayHomeAsUpEnabled(true);




        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tReg = (TextView) findViewById(R.id.tReg);


        btnLogin.setOnClickListener(this);
        tReg.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);

    }









    public void openVideo (View view)
    {
        startActivity(new Intent(this, video.class));
    }




    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnLogin:
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();

                User user = new User (username, password);


                authenticate(user);






                break;

            case R.id.tReg:

                startActivity(new Intent(this, UserRegister.class));


                break;


        }



    }
    private  void  authenticate(User user){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.getUserDataInBackground(user,new GetUserCallBack() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null){
                    showErrorMessage();
                }else {
                    logUserIn(returnedUser);
                }
            }
        });
    }

    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(UserLogin. this);
        dialogBuilder.setMessage("ERROR!!! Wrong User Details");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    private void logUserIn(User returnedUser){
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);

        startActivity(new Intent(this, MainActivity.class));

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_login, menu);
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
