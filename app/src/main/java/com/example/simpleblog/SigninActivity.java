package com.example.simpleblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SigninActivity extends AppCompatActivity {

    EditText nom, prenom, pseudo, password;
    Button btn_signin, btn_to_login;
    Utilisateur user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
        nom = findViewById(R.id.name);
        prenom = findViewById(R.id.username);
        pseudo = findViewById(R.id.SigninPseudo);
        password = findViewById(R.id.SigninPassword);
        btn_signin = findViewById(R.id.btn_addUser);
        btn_to_login = findViewById(R.id.btn_to_login);

        btn_signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String nomValue = nom.getText().toString().trim();
                String prenomValue = prenom.getText().toString().trim();
                String passwordValue = password.getText().toString().trim();
                String pseudoValue = pseudo.getText().toString().trim();
                if(TextUtils.isEmpty(nomValue) ||TextUtils.isEmpty(prenomValue) || TextUtils.isEmpty(passwordValue) || TextUtils.isEmpty(pseudoValue))
                {
                    Toast.makeText(SigninActivity.this, "Tous les champs sont requis", Toast.LENGTH_SHORT).show();
                }
                else{
                    user = new Utilisateur(nomValue,prenomValue, passwordValue, pseudoValue); //On instancie la classe utilisateur
                    if(dbHelper.insertUtilisateur(user)) //renvoie true si les données de l'utilisateur ont été inséré dans la base
                    {
                        Toast.makeText(SigninActivity.this, "Bienvenue !", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SigninActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(SigninActivity.this, "Erreur lors de l'inscription !", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        btn_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}