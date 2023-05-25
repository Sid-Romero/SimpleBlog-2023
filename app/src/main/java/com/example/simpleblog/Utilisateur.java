package com.example.simpleblog;


public class Utilisateur {
    private String nom;
    private String prenom;
    private String password;

    private String pseudo;

    // Constructeur
    public Utilisateur( String nom, String prenom, String password, String pseudo) {
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.pseudo = pseudo;
    }

    // Getters et Setters


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}

