package ejb.entites;

import javax.persistence.Entity;
import java.io.Serializable;



@Entity
public class Systematique extends Client implements Serializable{

    private double pourcentage;

    public Systematique() {
    }

    /**
     * @param prixActuel la valeur de la meilleur offre actuelle sur laquel on veut sur encherir
     * @return la prix que l'on propose (prix actuel plus un pourcentage prédéfini
     */
    @Override
    public double surenchere(double prixActuel) {
        return prixActuel + prixActuel*pourcentage/100;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }
}
