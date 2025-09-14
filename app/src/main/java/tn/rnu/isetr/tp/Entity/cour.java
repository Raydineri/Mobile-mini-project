package tn.rnu.isetr.tp.Entity;

public class cour {

    private String nom;
    private String type;
    private int heure;
    private String enseignant;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    private String ID;


    public cour(String nom, String type, int heure, String enseignant) {
        this.nom = nom;
        this.type = type;
        this.heure = heure;
        this.enseignant = enseignant;

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }

    public String getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(String enseignant) {
        this.enseignant = enseignant;
    }
}
