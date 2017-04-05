package ejb.entites;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Acheteur implements Serializable {
    @Id
    @ManyToOne
    private Client client;

    @Id
    @ManyToOne
    private Article article;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "acheteur")
    private Set<Offre> offres;

    private double plafond;


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public double getPlafond() {
        return plafond;
    }

    public void setPlafond(double plafond) {
        this.plafond = plafond;
    }

    public Set<Offre> getOffres() {
        return offres;
    }

    public void setOffres(Set<Offre> offres) {
        this.offres = offres;
    }
}
