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
 * Classe crée pour enregistrer tous les lemma utliser dans ROMANSEVAL
 * chaque lemma est enregitrer dans la table LEMMA dans la bd avec 2 attr
 * Cat pour indiquer la categorie garamatical de lemma
 * lemma le texte de lemma
 */
@Entity
public class Lemma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique=true)
    private String Lemma;
    
    @Column
    private String Cat;
    
    //Set of Sense
    @OneToMany(mappedBy = "lemma",cascade = CascadeType.ALL)
	private List<SenseLemma> senseId = new ArrayList<SenseLemma>();
    
    public Lemma() {
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCat() {
        return Cat;
    }

    public void setCat(String Cat) {
        this.Cat = Cat;
    }

    public String getLemma() {
        return Lemma;
    }

    public void setLemma(String Lemma) {
        this.Lemma = Lemma;
    }

    public List<SenseLemma> getSenseId() {
        return senseId;
    }

    public void setSenseId(List<SenseLemma> senseId) {
        this.senseId = senseId;
    }

   
   
    // association bidirectionnelle Lemma <--> SenseLemma
    public void addSenseId(SenseLemma senseid) {
		senseId.add(senseid);
		senseid.setLemma(this);
         
    } 
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Lemma other = (Lemma) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.Lemma, other.Lemma)) {
            return false;
        }
        if (!Objects.equals(this.Cat, other.Cat)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.Lemma);
        hash = 29 * hash + Objects.hashCode(this.Cat);
        return hash;
    }

    @Override
    public String toString() {
        return "LemmaTable{" + "id=" + id + ", Lemma=" + Lemma + ", Cat=" + Cat + '}';
    }

    


  
}
