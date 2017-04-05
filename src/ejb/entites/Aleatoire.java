package ejb.entites;

import javax.persistence.Entity;
import java.io.Serializable;


@Entity
public class Aleatoire extends Client implements Serializable{

    public Aleatoire() {
    }

    /**
     * @param prixActuel le prix de l'offre à laquel on veut surencherir
     * @return le prix plus un nombre entre 0 et 10 compris
     */
    @Override
    public double surenchere(double prixActuel) {
        return prixActuel + (int)(Math.random() * 11);
    }
}
