package com.example.simpleblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText pseudo, password;
    Button btn_login,btn_to_signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
        setContentView(R.layout.activity_login);
        pseudo = findViewById(R.id.Loginusername);
        password = findViewById(R.id.Loginpassword);
        btn_login = findViewById(R.id.btn_login);
        btn_to_signin = findViewById(R.id.btn_to_signin);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String passwordValue = password.getText().toString().trim();
                String pseudoValue = pseudo.getText().toString().trim();
                if(TextUtils.isEmpty(passwordValue) || TextUtils.isEmpty(pseudoValue))
                {
                    Toast.makeText(LoginActivity.this, "Tous les champs sont requis", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(dbHelper.checkUtilisateur(pseudoValue, passwordValue)) //si le pseudo et le mdp existent et correspondent
                    {
                        //Instance de la classe utilisateur
                        Utilisateur user = dbHelper.getUtilisateurByPseudo(pseudoValue);
                        // UtilisateurManager pour définir l'utilisateur connecté
                        UtilisateurManager.getInstance().setUtilisateur(user);
                        Toast.makeText(LoginActivity.this, "Bon retour "+user.getNom()+ "" + user.getPrenom(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        // intent.putExtra("utilisateur", (CharSequence) user); // user est l'instance de l'utilisateur connecté
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Pseudo ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_to_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });
    }
}