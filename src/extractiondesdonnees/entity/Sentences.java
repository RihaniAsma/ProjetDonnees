/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractiondesdonnees.entity;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 *
 * @author Administrateur
 */
@Entity
public class Sentences implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(nullable=false,length=10000)
    private String sentense;
     
    //relation Senses Sentences
    @ManyToMany(mappedBy = "sentences", fetch=FetchType.LAZY)
    private List<SenseLemma> senseLemma=new ArrayList<SenseLemma>();
    
    //Set of MotsSentences
       // relation Sentences (one) -> MotsSentences (many)
	// inverse de la relation existante MotsSentences (many) -> Sentences (one)
	// cascade suppression Sentences -> supression MotsSentences
    @OneToMany(mappedBy = "sentence",cascade = { CascadeType.REMOVE })
    private Set<MotsSentences> mot=new HashSet<MotsSentences>();
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSentense() {
        return sentense;
    }

    public void setSentense(String sentense) {
        this.sentense = sentense;
    }

    public List<SenseLemma> getSenseLemma() {
        return senseLemma;
    }

    public void setSenseLemma(List<SenseLemma> senseLemma) {
        this.senseLemma = senseLemma;
    }

    public Set<MotsSentences> getMot() {
        return mot;
    }

    public void setMot(Set<MotsSentences> mot) {
        this.mot = mot;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sentences other = (Sentences) obj;
        if (!Objects.equals(this.sentense, other.sentense)) {
            return false;
        }
        if (!Objects.equals(this.senseLemma, other.senseLemma)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.sentense);
        hash = 17 * hash + Objects.hashCode(this.senseLemma);
        return hash;
    }

    @Override
    public String toString() {
        return "Sentences{" + "id=" + id + ", sentense=" + sentense + ", senseLemma=" + senseLemma + '}';
    } 
}
