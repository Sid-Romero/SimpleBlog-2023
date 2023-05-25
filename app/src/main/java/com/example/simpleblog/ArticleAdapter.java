package com.example.simpleblog;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ArticleAdapter extends ArrayAdapter<Article> {

    private static final int MAX_LINES = 3; // Nombre maximum de lignes
    private static final int CHARS_PER_LINE = 40; // Nombre maximum de caractères par ligne
    public ArticleAdapter(Context context, List<Article> articles) {
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_article, parent, false);

        }

        //On récupère l'article concerné
        Article article = getItem(position);

        //Définiion et initialisation du titre de l'article
        TextView idTextView = convertView.findViewById(R.id.articleTitleTextView);
        idTextView.setText(article.getTitre());

        //Définition du contenu de l'article
        TextView contenuTextView = convertView.findViewById(R.id.articleContentTextView);

        // Initialiser et rogner le contenu de l'article avec des points de suspension si nécessaire
        String contenu = article.getContenu();
        if (contenu.length() > MAX_LINES * CHARS_PER_LINE) {
            // Réduire le contenu à trois lignes et demi en ajoutant des points de suspension
            String shortenedContenu = contenu.substring(0, MAX_LINES * CHARS_PER_LINE) + "...";
            contenuTextView.setText(shortenedContenu);
        }
        else{
            contenuTextView.setText(contenu);
        }

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this.getContext());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Récupérer les informations de l'article
                System.out.println("L'id : "+article.getId());
                String titre = article.getTitre();
                String datePublication = article.getDatePublication();
                System.out.println(datePublication);
                datePublication = formatDateTime(datePublication);
                System.out.println(datePublication);
                String auteur = dbHelper.getUserNameById(article.getUserPseudo());
                System.out.println("L'auteur : " +auteur);
                String contenu = article.getContenu();

                // Créer l'intent pour démarrer l'activité DetailArticleActivity
                Intent intent = new Intent(getContext(), DetailArticleActivity.class);
                intent.putExtra("titre", titre);
                intent.putExtra("datePublication", datePublication);
                intent.putExtra("auteur", auteur);
                intent.putExtra("contenu", contenu);

                // Démarrer l'activité DetailArticleActivity
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    private String formatDateTime(String dateTime) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm", new Locale("fr", "FR"));

        try {
            Date date = inputFormat.parse(dateTime);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateTime;
    }
}
