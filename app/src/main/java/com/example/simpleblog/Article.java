package com.example.simpleblog;

import java.util.Date;

public class Article {



    private int id;
    private String titre;
    private String contenu;


    private String datePublication;
    private String userPseudo;

    // Constructeur
    public Article(String titre, String contenu, String userPseudo) {

        this.titre = titre;
        this.contenu = contenu;
        this.userPseudo = userPseudo;
    }

    // Getters et Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(String datePublication) {
        this.datePublication = datePublication;
    }
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getUserPseudo() {
        return userPseudo;
    }

    public void setUserPseudo(String userPseudo) {
        this.userPseudo = userPseudo;
    }
}
