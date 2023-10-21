package com.Kaique.appbarber;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity2 extends AppCompatActivity {
    private TextView cadastar;
    private TextView email;
    private TextView senha;
    private Button botao;
    SignInButton signin;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        cadastar = findViewById(R.id.idIns);
        email = findViewById(R.id.IdEmm);
        senha = findViewById(R.id.idS);
        botao= findViewById(R.id.idOk);
        signin= findViewById(R.id.idGoogle);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("39215645037-2fhtpllj7l4thb50epamlh5k9gj51448.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this,gso);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty() || senha.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!!!",Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), senha.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        // Log.d(TAG, "signInWithCustomToken:success");
                                        Snackbar snackbar = Snackbar.make(view,"Salvo com sucesso", Snackbar.LENGTH_SHORT);
                                        snackbar.setBackgroundTint(Color.BLUE);
                                        snackbar.show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        telaprincipal();
                                        finish();
                                        // updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        //Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                                        Snackbar snackbar = Snackbar.make(view,"Salvo com sucesso", Snackbar.LENGTH_SHORT);
                                        snackbar.setBackgroundTint(Color.RED);
                                        snackbar.show();
                                        // updateUI(null);
                                    }
                                }
                            });
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singnInG();
            }
        });

        cadastar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telacadastro();
            }
        });


    }
    private void loginGoogle(String token){
        AuthCredential credencial = GoogleAuthProvider.getCredential(token,null);
        mAuth.signInWithCredential(credencial).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Login com o Google efetuado com sucesso", Toast.LENGTH_SHORT).show();
                    telaprincipal();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "Erro ao efetuar o Login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void singnInG(){
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,1);
    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode,Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode==1){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
            try {
                    GoogleSignInAccount conta = task.getResult(ApiException.class);
                    loginGoogle(conta.getIdToken());
            }catch (ApiException exception){
                Toast.makeText(getApplicationContext(), "Nenhum usuario logado", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void  telaprincipal(){
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void telacadastro(){
        Intent intent= new Intent(this, CadastroActivity2.class);
        startActivity(intent);
    }
}
