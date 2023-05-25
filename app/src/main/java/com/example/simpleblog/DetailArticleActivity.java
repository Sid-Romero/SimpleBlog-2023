package com.example.simpleblog;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.simpleblog.databinding.ActivityDetailArticleBinding;

public class DetailArticleActivity extends AppCompatActivity {
    private TextView titreTextView;
    private TextView datePublicationTextView;
    private TextView auteurTextView;
    private TextView contenuTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);

        titreTextView = findViewById(R.id.titreTextView);
        datePublicationTextView = findViewById(R.id.datePublicationTextView);
        auteurTextView = findViewById(R.id.auteurTextView);
        contenuTextView = findViewById(R.id.contenuTextView);
        FloatingActionButton btn_returnToMain;

        // On récupère les données de l'intent
        Intent intent = getIntent();
        String titre = intent.getStringExtra("titre");
        String datePublication = intent.getStringExtra("datePublication");
        String auteur = intent.getStringExtra("auteur");
        String contenu = intent.getStringExtra("contenu");

        System.out.println(datePublication);

        //Définition du bouton de retour
        btn_returnToMain = findViewById(R.id.btn_to_MainActivity);

        // infos sur l'auteur
        auteur = "Ecrit par " + auteur;

        // et on affiche les informations de l'article dans les TextViews
        titreTextView.setText(titre);
        datePublicationTextView.setText(datePublication);
        auteurTextView.setText(auteur);
        contenuTextView.setText(contenu);

        btn_returnToMain.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailArticleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
