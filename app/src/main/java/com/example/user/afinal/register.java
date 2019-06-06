package com.example.user.afinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class register extends AppCompatActivity {
    EditText edEmail,edPass,edPhone,edName;
    Button btnReg;
    user user;
    TextView tvlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initView();
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUserInput();
            }
        });
        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
        private void registerUserInput(){
            String email,pass,phone,name;
            email = edEmail.getText().toString();
            pass = edPass.getText().toString();
            phone = edPhone.getText().toString();
            name = edName.getText().toString();

        }

        private void insertData() {
            class RegisterUser extends AsyncTask<Void,Void,String> {

                ProgressDialog loading;
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(register.this,
                            "Registration","...",false,false);
                }

                @Override
                protected String doInBackground(Void... voids) {
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("email",user.email);
                    hashMap.put("password",user.password);
                    hashMap.put("phone",user.phone);
                    hashMap.put("name",user.name);
                    RequestHandler rh = new RequestHandler();
                    String s = rh.sendPostRequest
                            ("http://www.funsproject.com/URR/insert_registration.php",hashMap);
                    return s;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    if (s.equalsIgnoreCase("success")){
                        Toast.makeText(register.this, "Registration Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(register.this,login.class);
                        register.this.finish();
                        startActivity(intent);

                    }else if (s.equalsIgnoreCase("nodata")){
                        Toast.makeText(register.this, "Please fill in data first", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            RegisterUser registerUser = new RegisterUser();
            registerUser.execute();
        }
        private void initView() {
            edEmail = findViewById(R.id.txtEmail);
            edPass = findViewById(R.id.txtpassword);
            edPhone =findViewById(R.id.txtphone);
            edName = findViewById(R.id.txtname);
            btnReg = findViewById(R.id.register);
            tvlogin = findViewById(R.id.tvregister);
        }

    }
