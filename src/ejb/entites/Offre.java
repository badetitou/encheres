package ejb.entites;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by badetitou on 19/03/17.
 */

@Entity
public class Offre implements Serializable, Comparable {

    @Id
    @GeneratedValue
    private int id;
    private Timestamp date;
    private double prix;
    @ManyToOne
    private Acheteur acheteur;
    @ManyToOne
    private Article article;

    public Offre() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }


    public Acheteur getAcheteur() {
        return acheteur;
    }

    public void setAcheteur(Acheteur acheteur) {
        this.acheteur = acheteur;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public int compareTo(Object o) {
        return ((Offre) o).getPrix().compareTo(this.getPrix());
    }
}
