package com.Aman.todolist;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;

    Button signupButton;

    TextView errorMessage;

    private FirebaseAuth mAuth;

    TextView login_to_signup;

    Button already_has_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.email);

        editTextPassword = findViewById(R.id.password);

        signupButton = findViewById(R.id.signup_Button);

        already_has_account = findViewById(R.id.already_has_account);

        errorMessage = findViewById(R.id.errorMessage);

        already_has_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alreadyHasAccount();
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;

                Log.e(TAG, "Account Created: ");

                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                //    errorMessage.setText("Enter an email");
                    Toast.makeText(Signup.this, "Enter email or password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isInternetConnected()){

                }else {
                    errorMessage.setText("No active internet connection.");
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                   errorMessage.setText("Account Created Successfully");
                                    Intent intent;
                                    intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    mAuth.signOut();


                                } else {
                                    errorMessage.setText("Sign Up failed, try again");


                                }
                            }
                        });
            }

        });

    }

    public void alreadyHasAccount() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();


    }

    public  boolean isInternetConnected(){
        try{
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if(manager != null) {
                networkInfo = manager.getActiveNetworkInfo();
            }
            return networkInfo !=null && networkInfo.isConnected();
        }catch (NullPointerException e){
            return false;
        }
    }


}