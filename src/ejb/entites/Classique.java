package ejb.entites;

import javax.persistence.Entity;
import java.io.Serializable;


@Entity
public class Classique extends Client implements Serializable{

    public Classique() {
    }

    /**
     * @param prixActuel
     * @return
     */
    @Override
    public double surenchere(double prixActuel) {
        return 0;
    }
}
