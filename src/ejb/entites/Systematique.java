package ejb.entites;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by badetitou on 14/03/17.
 */

@Entity
public class Systematique extends Client implements Serializable{

    private double pourcentage;

    public Systematique() {
    }

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
