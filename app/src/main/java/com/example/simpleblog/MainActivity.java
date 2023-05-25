package com.example.simpleblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
{


    FloatingActionButton btnAdd;
    ArrayList<Article> listeArticles = new ArrayList<>();


    ListView listViewArticles;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewArticles = findViewById(R.id.listViewArticles);
        btnAdd = findViewById(R.id.btn_to_AddArticle);

        Utilisateur user = UtilisateurManager.getInstance().getUtilisateur();
        btnAdd = findViewById(R.id.btn_to_AddArticle);
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
        listeArticles = dbHelper.getArticles();
        ArticleAdapter articleAdapter = new ArticleAdapter(MainActivity.this, listeArticles);
        listViewArticles.setAdapter(articleAdapter);
        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, CreateArticleActivity.class);
                startActivity(intent);
            }
        });


    }



    @Override
    protected void onResume() {
        super.onResume();
        loadArticlesFromDatabase();
    }
    private void loadArticlesFromDatabase()
   {
       DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
       listeArticles = dbHelper.getArticles();
       ArticleAdapter articleAdapter = new ArticleAdapter(MainActivity.this, listeArticles);
       listViewArticles.setAdapter(articleAdapter);


       int ListCount = listViewArticles.getCount();
        Utilisateur user = UtilisateurManager.getInstance().getUtilisateur();
        System.out.println(user.getPseudo());

        if (listeArticles.size() > 0) {

            System.out.println("Il y'a exactement "+listeArticles.size() + "articles");
        } else {
            // La ListView est vide
            System.out.println("Aucun article mdr");
        }

        if (ListCount > 0) {

            System.out.println("La ListView contient des éléments");
        } else {
            // La ListView est vide
            System.out.println("La ListView est vide");
        }
        int itemCount = articleAdapter.getCount();

        if (itemCount > 0) {

            System.out.println("L'Adapter contient des éléments");
        } else {

            System.out.println("L'Adapter est vide");
        }
    }

}

