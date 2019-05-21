package com.example.vabolari.login_register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Entity;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServerRequest {

    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://victordeakin.site50.net";


    public ServerRequest (Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please Wait........");
    }


    public void storeUserDataInBackground(User user, GetUserCallBack userCallBack) {
        progressDialog.show();
        new StoredUserDataAsyncTask(user, userCallBack).execute();

    }

    public void getUserDataInBackground(User user, GetUserCallBack callBack){

        progressDialog.show();
        new getUserDataAsyncTask(user, callBack).execute();


    }

    public class StoredUserDataAsyncTask extends AsyncTask<Void, Void, Void> {

        User user;
        GetUserCallBack userCallBack;


        public StoredUserDataAsyncTask(User user, GetUserCallBack userCallBack){
            this.user = user;
            this.userCallBack = userCallBack;


        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair>dataToSend = new ArrayList<NameValuePair>();
            dataToSend.add(new BasicNameValuePair("name", user.name));
            dataToSend.add(new BasicNameValuePair("income", user.income + ""));
            dataToSend.add(new BasicNameValuePair("username", user.username));
            dataToSend.add(new BasicNameValuePair("password", user.password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");

            try{
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            }catch (Exception e){
                e.printStackTrace();
            }




            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallBack.done(null);

            super.onPostExecute(aVoid);
        }
    }
    public class getUserDataAsyncTask extends AsyncTask<Void, Void, User> {

        User user;
        GetUserCallBack userCallBack;




        public getUserDataAsyncTask(User user, GetUserCallBack userCallBack){
            this.user = user;
            this.userCallBack = userCallBack;


        }
        @Override
        protected User doInBackground(Void... params) {

            ArrayList<NameValuePair>dataToSend = new ArrayList<NameValuePair>();
             dataToSend.add(new BasicNameValuePair("username", user.username));
            dataToSend.add(new BasicNameValuePair("password", user.password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);


            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "GetData.php");


            User returnedUser = null;


            try{
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                if (jObject.length() == 0){
                    user = null;

                }else
                {
                    String name = jObject.getString("name");
                    int income = jObject.getInt("income");

                    returnedUser = new User(name, income, user.username, user.password);
                }



            }catch (Exception e){
                e.printStackTrace();
            }

            return returnedUser;
        }
        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallBack.done(returnedUser);

            super.onPostExecute(returnedUser);
        }



        }

}
