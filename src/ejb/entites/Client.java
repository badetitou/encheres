package ejb.entites;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by badetitou on 14/03/17.
 */

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

    public abstract double surenchere(double prixActuel);
}