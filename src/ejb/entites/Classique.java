package ejb.entites;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by badetitou on 14/03/17.
 */

@Entity
public class Classique extends Client implements Serializable{

    public Classique() {
    }

    @Override
    public double surenchere(double prixActuel) {
        return 0;
    }
}
