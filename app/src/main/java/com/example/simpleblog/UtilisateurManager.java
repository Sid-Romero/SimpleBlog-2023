package com.example.simpleblog;

public class UtilisateurManager {
    private static UtilisateurManager instance;
    private Utilisateur utilisateur;

    private UtilisateurManager() {
        // Constructeur privé pour empêcher l'instanciation directe de la classe
    }

    public static UtilisateurManager getInstance() {
        if (instance == null) {
            instance = new UtilisateurManager();
        }
        return instance;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        // Sauvegarder l'utilisateur dans les préférences partagées ou dans une base de données
    }

    public Utilisateur getUtilisateur() {
        // Récupérer l'utilisateur à partir des préférences partagées ou de la base de données
        return utilisateur;
    }
}
