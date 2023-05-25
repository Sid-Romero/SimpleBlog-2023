package com.example.simpleblog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper
{

    private static DatabaseHelper instance;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }
    private static final String DATABASE_NAME = "SimpleBlog.db";
    private static final int DATABASE_VERSION = 1;

    // Table Utilisateur
    private static final String TABLE_UTILISATEUR = "Utilisateur";
    private static final String COLUMN_UTILISATEUR_ID = "idUser";
    private static final String COLUMN_UTILISATEUR_NOM = "nom";
    private static final String COLUMN_UTILISATEUR_PRENOM = "prenom";
    private static final String COLUMN_UTILISATEUR_PASSWORD = "password";

    private static final String COLUMN_UTILISATEUR_PSEUDO = "pseudo";
    private static final String CREATE_TABLE_UTILISATEUR = "CREATE TABLE " + TABLE_UTILISATEUR +
            "(" +
            COLUMN_UTILISATEUR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            COLUMN_UTILISATEUR_NOM + " VARCHAR(30)," +
            COLUMN_UTILISATEUR_PRENOM + " VARCHAR(50)," +
            COLUMN_UTILISATEUR_PASSWORD + " VARCHAR(100)," +
            COLUMN_UTILISATEUR_PSEUDO + " VARCHAR(80) UNIQUE" +
            ")";

    // Table Article
    private static final String TABLE_ARTICLE = "Article";
    private static final String COLUMN_ARTICLE_ID = "idArticle";
    private static final String COLUMN_ARTICLE_TITRE = "titre";
    private static final String COLUMN_ARTICLE_CONTENU = "contenu";
    private static final String COLUMN_ARTICLE_DATE_PUBLICATION = "datePublication";
    private static final String COLUMN_ARTICLE_PSEUDO_USER = "userPseudo";
    private static final String CREATE_TABLE_ARTICLE = "CREATE TABLE " + TABLE_ARTICLE +
            "(" +
            COLUMN_ARTICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_ARTICLE_TITRE + " TEXT," +
            COLUMN_ARTICLE_CONTENU + " TEXT," +
            COLUMN_ARTICLE_DATE_PUBLICATION + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
            COLUMN_ARTICLE_PSEUDO_USER + " INTEGER," +
            "FOREIGN KEY(" + COLUMN_ARTICLE_PSEUDO_USER + ") REFERENCES " + TABLE_UTILISATEUR + "(" + COLUMN_UTILISATEUR_PSEUDO + ")" +
            ")";

    // Table Commentaires
    private static final String TABLE_COMMENTAIRE = "Commentaires";
    private static final String COLUMN_COMMENTAIRE_ID = "idComment";
    private static final String COLUMN_COMMENTAIRE_CONTENU = "contenu";
    private static final String COLUMN_COMMENTAIRE_DATE_PUBLICATION = "datePublication";
    private static final String COLUMN_COMMENTAIRE_ID_ARTICLE = "idArticle";
    private static final String COLUMN_COMMENTAIRE_ID_USER = "idUser";
    private static final String CREATE_TABLE_COMMENTAIRE = "CREATE TABLE " + TABLE_COMMENTAIRE +
            "(" +
            COLUMN_COMMENTAIRE_ID + " INTEGER," +
            COLUMN_COMMENTAIRE_CONTENU + " TEXT," +
            COLUMN_COMMENTAIRE_DATE_PUBLICATION + " DATETIME," +
            COLUMN_COMMENTAIRE_ID_ARTICLE + " INTEGER," +
            COLUMN_COMMENTAIRE_ID_USER + " INTEGER," +
            "PRIMARY KEY(" + COLUMN_COMMENTAIRE_ID_USER + ", " + COLUMN_COMMENTAIRE_ID_ARTICLE + ", " + COLUMN_COMMENTAIRE_ID + ")," +
            "FOREIGN KEY(" + COLUMN_COMMENTAIRE_ID_ARTICLE + ") REFERENCES " + TABLE_ARTICLE + "(" + COLUMN_ARTICLE_ID + ")," +
            "FOREIGN KEY(" + COLUMN_COMMENTAIRE_ID_USER + ") REFERENCES " + TABLE_UTILISATEUR + "(" + COLUMN_UTILISATEUR_ID + ")" +
            ")";



    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création des tables
        db.execSQL(CREATE_TABLE_UTILISATEUR);
        db.execSQL(CREATE_TABLE_ARTICLE);
        db.execSQL(CREATE_TABLE_COMMENTAIRE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Supprimer les tables existantes lors d'une mise à jour de la base de données
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEUR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTAIRE);
        // Recréer les tables
        onCreate(db);
    }

    public boolean insertUtilisateur(Utilisateur utilisateur)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // Vérifier si le pseudo existe déjà dans la base de données
        Cursor cursor = db.query(TABLE_UTILISATEUR, new String[]{COLUMN_UTILISATEUR_PSEUDO}, COLUMN_UTILISATEUR_PSEUDO + " = ?", new String[]{utilisateur.getPseudo()}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            // Le pseudo existe déjà, retourner faux
            cursor.close();
            return false;
        }
        // Fermer le curseur
        cursor.close();

        // Insérer l'utilisateur dans la base de données
        ContentValues values = new ContentValues();
        values.put(COLUMN_UTILISATEUR_NOM, utilisateur.getNom());
        values.put(COLUMN_UTILISATEUR_PRENOM, utilisateur.getPrenom());
        values.put(COLUMN_UTILISATEUR_PASSWORD, utilisateur.getPassword());
        values.put(COLUMN_UTILISATEUR_PSEUDO, utilisateur.getPseudo());

        long id = db.insert(TABLE_UTILISATEUR, null, values);

        // Vérifier si l'insertion a réussi
        if (id == -1) {
            // L'insertion a échoué, retourner faux
            return false;
        } else {
            // L'insertion a réussi, retourner vrai
            return true;
        }
    }

    public boolean checkUtilisateur(String pseudo, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        // Vérifier si le pseudo de l'utilisateur est présent dans la base de données
        Cursor cursor = db.query(TABLE_UTILISATEUR, new String[]{COLUMN_UTILISATEUR_PASSWORD}, COLUMN_UTILISATEUR_PSEUDO + " = ?", new String[]{pseudo}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int passwordIndex = cursor.getColumnIndex(COLUMN_UTILISATEUR_PASSWORD);
            if (passwordIndex >= 0) {
                // Le pseudo existe, vérifier si les mots de passe correspondent
                String savedPassword = cursor.getString(passwordIndex);
                cursor.close();
                if (savedPassword.equals(password)) {
                    // Les mots de passe correspondent, retourner vrai
                    return true;
                } else {
                    // Les mots de passe ne correspondent pas, retourner faux
                    return false;
                }
            }
        }

        // Le pseudo n'existe pas ou une erreur s'est produite, retourner faux
        if (cursor != null) {
            cursor.close();
        }
        return false;
    }

    public Utilisateur getUtilisateurByPseudo(String pseudo) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_UTILISATEUR, null, COLUMN_UTILISATEUR_PSEUDO + " = ?", new String[]{pseudo}, null, null, null);
        Utilisateur utilisateur = null;

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_UTILISATEUR_ID);
            int nomIndex = cursor.getColumnIndex(COLUMN_UTILISATEUR_NOM);
            int prenomIndex = cursor.getColumnIndex(COLUMN_UTILISATEUR_PRENOM);
            int passwordIndex = cursor.getColumnIndex(COLUMN_UTILISATEUR_PASSWORD);

            int id = cursor.getInt(idIndex);
            String nom = cursor.getString(nomIndex);
            String prenom = cursor.getString(prenomIndex);
            String password = cursor.getString(passwordIndex);

            utilisateur = new Utilisateur(nom, prenom, password, pseudo);

            cursor.close();
        }

        return utilisateur;
    }


    public boolean insertArticle(Article article) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            // Récupérer la date actuelle
            long timestamp = new Date().getTime();

            // Insérer l'article dans la base de données
            ContentValues values = new ContentValues();
            values.put(COLUMN_ARTICLE_TITRE, article.getTitre());
            values.put(COLUMN_ARTICLE_CONTENU, article.getContenu());
            values.put(COLUMN_ARTICLE_PSEUDO_USER, article.getUserPseudo());

            long id = db.insert(TABLE_ARTICLE, null, values);
            return id != -1;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;


    }

    public ArrayList<Article> getArticles() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Article> articles = new ArrayList<>();

        // Sélectionner tous les articles de la table Article
        String selectQuery = "SELECT * FROM " + TABLE_ARTICLE;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Parcourir les lignes du curseur et créer des objets Article
        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ARTICLE_ID);
                int titreIndex = cursor.getColumnIndex(COLUMN_ARTICLE_TITRE);
                int contenuIndex = cursor.getColumnIndex(COLUMN_ARTICLE_CONTENU);
                int datePublicationIndex = cursor.getColumnIndex(COLUMN_ARTICLE_DATE_PUBLICATION);
                int userPseudoIndex = cursor.getColumnIndex(COLUMN_ARTICLE_PSEUDO_USER);

                int id = cursor.getInt(idIndex);
                String titre = cursor.getString(titreIndex);
                String contenu = cursor.getString(contenuIndex);
                String datePublication = cursor.getString(datePublicationIndex);
                String userPseudo = cursor.getString(userPseudoIndex);

                // Créer un nouvel objet Article avec les données du curseur
                Article article = new Article(titre, contenu, userPseudo);
                article.setId(id);
                article.setDatePublication(datePublication);
                articles.add(article);
            } while (cursor.moveToNext());
        }

        // Fermer le curseur
        cursor.close();

        return articles;
    }

    public String getUserNameById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userName = "";

        // Sélectionner le prénom et le nom de l'utilisateur en fonction de l'id
        String selectQuery = "SELECT " + COLUMN_UTILISATEUR_PRENOM + ", " + COLUMN_UTILISATEUR_NOM +
                " FROM " + TABLE_UTILISATEUR +
                " WHERE " + COLUMN_UTILISATEUR_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        // Vérifier si le curseur contient des données
        if (cursor.moveToFirst()) {
            // Récupérer le prénom et le nom de l'utilisateur à partir du curseur
            int prenomIndex = cursor.getColumnIndex(COLUMN_UTILISATEUR_PRENOM);
            int nomIndex = cursor.getColumnIndex(COLUMN_UTILISATEUR_NOM);
            String prenom = cursor.getString(prenomIndex);
            String nom = cursor.getString(nomIndex);
            userName = prenom + " " + nom;
        }
        cursor.close();

        return userName;
    }



}

