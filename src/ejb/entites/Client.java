package ejb.entites;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
public abstract class Client implements Serializable {

    @Id
    private String adresseMail;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<Acheteur> acheteur;

    public Client() {
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public Set<Acheteur> getAcheteur() {
        return acheteur;
    }

    public void setAcheteur(Set<Acheteur> acheteur) {
        this.acheteur = acheteur;
    }

    /**
     * @param prixActuel la valeur de la meilleur offre actuelle sur laquel on veut sur encherir
     * @return la prix que l'on propose
     */
    public abstract double surenchere(double prixActuel);
}