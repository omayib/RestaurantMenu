package id.technomotion.restaurantmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    EditText editTextEmail,editTextPwd;
    Button buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail= (EditText) findViewById(R.id.editTextEmail);
        editTextPwd= (EditText) findViewById(R.id.editTextPassword);
        buttonLogin= (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputEmail=editTextEmail.getText().toString();
                String inputPassword=editTextPwd.getText().toString();

                if(inputEmail.isEmpty()){
                    Toast.makeText(MainActivity.this,"email wajib diisi",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(inputPassword.isEmpty()){
                    Toast.makeText(MainActivity.this,"password wajib diisi",Toast.LENGTH_SHORT).show();
                    return;
                }

                RestaurantClient.Signin(inputEmail,inputPassword).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String jsonString = response.body().string();
                        boolean isSigninSucceeded = false;
                        String token="";
                        try {
                            JSONObject jsonObject=new JSONObject(jsonString);
                            isSigninSucceeded=jsonObject.getBoolean("succeeded");
                            token=jsonObject.getString("token");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(isSigninSucceeded){
                            Intent intent=new Intent(MainActivity.this,FoodsActivity.class);
                            intent.putExtra("data_token",token);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
    }
}
