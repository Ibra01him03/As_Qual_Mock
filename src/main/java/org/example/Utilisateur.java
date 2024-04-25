package org.example;

public class Utilisateur {
    private Integer id = null;
    private String Nom;
    private String Prenom;
    private String email;

     public Integer Utilisateur(Integer id, String nom, String prenom, String email) {
        this.id = id;
        Nom = nom;
        Prenom = prenom;
        this.email = email;
        return id ;
    }

    public Utilisateur(String nom, String prenom, String email) {

        Nom = nom;
        Prenom = prenom;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
