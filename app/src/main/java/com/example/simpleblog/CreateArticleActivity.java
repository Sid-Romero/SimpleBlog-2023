package com.example.simpleblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreateArticleActivity extends AppCompatActivity {

    private FloatingActionButton btn_return;
    private EditText editTitre, editContenu;
    private Button btnAjouter;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_article);
        btn_return = findViewById(R.id.btn_returnFromAddArticle);
        editTitre = findViewById(R.id.editTitre);
        editContenu = findViewById(R.id.editContenu);
        btnAjouter = findViewById(R.id.btn_to_AddArticle);
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);


        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateArticleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String titre = editTitre.getText().toString().trim();
                String contenu = editContenu.getText().toString().trim();
                //Obtention de l'instance de l'utilisateur actuel
                Utilisateur user = UtilisateurManager.getInstance().getUtilisateur();
                String pseudoUser = user.getPseudo(); // Obtenir le pseudo de l'utilisateur actuel


                // Envoyer l'article à MainActivity

                if (!titre.isEmpty() && !contenu.isEmpty())
                {
                    Article nouvelArticle = new Article(titre, contenu, pseudoUser);
                    dbHelper.insertArticle(nouvelArticle);

                    Intent intentToMain = new Intent(CreateArticleActivity.this, MainActivity.class);
                    startActivity(intentToMain);
                    finish();


                } else {
                    // Gérer le cas où les champs titre ou contenu sont vides
                }

            }
        });



    }
}