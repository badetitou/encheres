package ejb.entites;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by badetitou on 14/03/17.
 */

@Entity
public class Article implements Serializable{

    @Id
    private String code;
    @Enumerated(EnumType.STRING)
    private EtatEnchere etatEnchere;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "article")
    private Set<Acheteur> acheteur;

    private String nom;
    private double prixInitial;
    private double prixMeilleureOffre;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "article")
    private Set<Offre> offres;


    public Article() {

    }


    public Set<Acheteur> getAcheteur() {
        return acheteur;
    }

    public void setAcheteur(Set<Acheteur> acheteur) {
        this.acheteur = acheteur;
    }

    public EtatEnchere getEtatEnchere() {
        return etatEnchere;
    }

    public void setEtatEnchere(EtatEnchere etatEnchere) {
        this.etatEnchere = etatEnchere;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrixInitial() {
        return prixInitial;
    }

    public void setPrixInitial(double prixInitial) {
        this.prixInitial = prixInitial;
    }

    public double getPrixMeilleureOffre() {
        return prixMeilleureOffre;
    }

    public void setPrixMeilleureOffre(double prixMeilleureOffre) {
        this.prixMeilleureOffre = prixMeilleureOffre;
    }

    public Set<Offre> getOffres() {
        return offres;
    }

    public void setOffres(Set<Offre> offres) {
        this.offres = offres;
    }
}
