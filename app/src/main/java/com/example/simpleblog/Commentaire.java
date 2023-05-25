package com.example.simpleblog;

import java.util.Date;

public class Commentaire {
    private int idComment;
    private String contenu;
    private Date datePublication;
    private int idArticle;
    private int idUser;

    // Constructeur
    public Commentaire(int idComment, String contenu, Date datePublication, int idArticle, int idUser) {
        this.idComment = idComment;
        this.contenu = contenu;
        this.datePublication = datePublication;
        this.idArticle = idArticle;
        this.idUser = idUser;
    }

    // Getters et Setters
    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}

