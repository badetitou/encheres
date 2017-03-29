package ejb.entites;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by badetitou on 14/03/17.
 */

@Entity
public class Aleatoire extends Client implements Serializable{

    public Aleatoire() {
    }

    @Override
    public double surenchere(double prixActuel) {
        return prixActuel + (int)(Math.random() * 11);
    }
}
