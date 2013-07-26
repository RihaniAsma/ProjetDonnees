/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractiondesdonnees.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author Administrateur
 */
@Entity
public class Mots implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(unique=true)
    private String mot;
   
    //Set of MotsSentences
       // relation Mots (one) -> MotsSentences (many)
	// inverse de la relation existante MotsSentences (many) -> Mots (one)
	// cascade suppression Mots -> supression MotsSentences
	@OneToMany(mappedBy = "mot",cascade = { CascadeType.REMOVE })
	private Set<MotsSentences> sentences = new HashSet<MotsSentences>();

    //Set of MotsSenses
       // relation Mots (one) -> MotsSenses (many)
	// inverse de la relation existante MotsSenses (many) -> Mots (one)
	// cascade suppression Mots -> supression MotsSenses
	@OneToMany(mappedBy = "mot",cascade = { CascadeType.REMOVE })
	 private Set<MotsSenses> senseLemma=new HashSet<MotsSenses>();
        
        @OneToMany(mappedBy = "mot1",cascade = { CascadeType.REMOVE })
	 private Set<MotOccurMot> MotOccurMot=new HashSet<MotOccurMot>();
         @OneToMany(mappedBy = "mot2",cascade = { CascadeType.REMOVE })
	 private Set<MotOccurMot> MotOccurMot2=new HashSet<MotOccurMot>();
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMot() {
        return mot;
    }

    public void setMot(String mot) {
        this.mot = mot;
    }

    public Set<MotsSentences> getSentences() {
        return sentences;
    }

    public void setSentences(Set<MotsSentences> sentences) {
        this.sentences = sentences;
    }

    public Set<MotsSenses> getSenseLemma() {
        return senseLemma;
    }

    public void setSenseLemma(Set<MotsSenses> senseLemma) {
        this.senseLemma = senseLemma;
    }

    public Set<MotOccurMot> getMotOccurMot() {
        return MotOccurMot;
    }

    public void setMotOccurMot(Set<MotOccurMot> MotOccurMot) {
        this.MotOccurMot = MotOccurMot;
    }

    public Set<MotOccurMot> getMotOccurMot2() {
        return MotOccurMot2;
    }

    public void setMotOccurMot2(Set<MotOccurMot> MotOccurMot2) {
        this.MotOccurMot2 = MotOccurMot2;
    }

   

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mots other = (Mots) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.mot, other.mot)) {
            return false;
        }
        if (!Objects.equals(this.sentences, other.sentences)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.mot);
        hash = 67 * hash + Objects.hashCode(this.sentences);
        return hash;
    }

    @Override
    public String toString() {
        return "Mots{" + "id=" + id + ", mot=" + mot + ", sentences=" + sentences + '}';
    }
           
    
}
