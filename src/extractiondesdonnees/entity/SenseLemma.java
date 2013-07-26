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
 * Classe pour enregistrer les senses et les def des lemma
 */
@Entity
public class SenseLemma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column
    private String Senseid;

    @Column(nullable = false,length=1000)
    private String defenition;

    // relation principale Sense (many) -> Lemma (one)
	// implémentée par une clé étrangère (lemma_id) dans Sense
	// 1 Sense a nécessairement 1 Lemma (nullable=false)
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "lemma_id", nullable = false)
	private Lemma lemma;
        
       //Set of Sentences
        // relation Sense (many) -> Sentence (many) via une table de jointure sense_sentence
	// sense_sentence(sense_id) est clé étangère sur sense(id)
	// sense_sentence(sentence_id) est clé étangère sur sentence(id)
	// cascade=CascadeType.PERSIST : persistance d'1 sense  entraîne celle de ses sentences
	@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "sense_sentence", joinColumns = @JoinColumn(name = "sense_id"), inverseJoinColumns = @JoinColumn(name = "sentence_id"))
	private List<Sentences> sentences = new ArrayList<Sentences>();
    
        //Set of MotsSenses
       // relation Sense (one) -> MotsSenses (many)
	// inverse de la relation existante MotsSenses (many) -> Sense (one)
	// cascade suppression Sense -> supression MotsSenses
    @OneToMany(mappedBy = "senseLemma",cascade = { CascadeType.REMOVE })
    private Set<MotsSenses> mot=new HashSet<MotsSenses>();
    
    public SenseLemma() {
    }

    public String getSenseid() {
        return Senseid;
    }

    public void setSenseid(String Senseid) {
        this.Senseid = Senseid;
    }

    public String getDefenition() {
        return defenition;
    }

    public void setDefenition(String defenition) {
        this.defenition = defenition;
    }

    public List<Sentences> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentences> sentences) {
        this.sentences = sentences;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SenseLemma other = (SenseLemma) obj;
        if (!Objects.equals(this.Senseid, other.Senseid)) {
            return false;
        }
        if (!Objects.equals(this.defenition, other.defenition)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.Senseid);
        hash = 53 * hash + Objects.hashCode(this.defenition);
        return hash;
    }

    public Lemma getLemma() {
        return lemma;
    }

    public void setLemma(Lemma lemma) {
        this.lemma = lemma;
    }

    public Set<MotsSenses> getMot() {
        return mot;
    }

    public void setMot(Set<MotsSenses> mot) {
        this.mot = mot;
    }

    
    @Override
    public String toString() {
        return "SenseLemma{" + "Senseid=" + Senseid + ", defenition=" + defenition + '}';
    }
    
    
}
