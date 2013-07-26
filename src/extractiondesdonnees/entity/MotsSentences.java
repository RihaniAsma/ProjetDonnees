/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractiondesdonnees.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Administrateur
 */
@Entity
public class MotsSentences implements Serializable {
     private static final long serialVersionUID = 1L;
   @Embeddable
	public static class Id implements Serializable {
       private static final long serialVersionUID = 1L;
		// composantes de la clé composite
		// pointe sur un mot
		@Column(name = "mot_id")
		private int mot_id;

		// pointe sur une sentence
		@Column(name = "sentence_id")
		private int sentence_id;

		// constructeurs
		public Id() {
		}

		// getters et setters
                public Id(int motid, int sentenceid) {
			super();
			this.mot_id = motid;
			this.sentence_id = sentenceid;
		}

        public int getMot_id() {
            return mot_id;
        }

        public void setMot_id(int mot_id) {
            this.mot_id = mot_id;
        }

        public int getSentence_id() {
            return sentence_id;
        }

        public void setSentence_id(int sentence_id) {
            this.sentence_id = sentence_id;
        }
                 //toString

        @Override
        public String toString() {
            return "Id{" + "mot_id=" + mot_id + ", sentence_id=" + sentence_id + '}';
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Id other = (Id) obj;
            if (this.mot_id != other.mot_id) {
                return false;
            }
            if (this.sentence_id != other.sentence_id) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 73 * hash + this.mot_id;
            hash = 73 * hash + this.sentence_id;
            return hash;
        }
        
	}

	// champs de la classe MotsSentences
	// clé composite
	@EmbeddedId
	private Id id = new Id();
        
        @Column
        private int nbroccur;

	// relation principale MotsSentences (many) -> Mots (one)
	// implémentée par la clé étrangère : mot_id (MotsSentences (many) -> Mots (one)
	// mot_id est en même temps élément de la clé primaire composite
	// JPA ne doit pas gérer cette clé étrangère (insertable = false, updatable = false) car c'est fait par l'application
	// elle-même dans son
	// constructeur
	@ManyToOne
	@JoinColumn(name = "mot_id", insertable = false, updatable = false)
	private Mots mot;

	// relation principale MotsSentences -> Sentences
	// implémentée par la clé étrangère : sentence_id (MotsSentences (many) -> Sentences (one)
	// sentence_id est en même temps élément de la clé primaire composite
	// JPA ne doit pas gérer cette clé étrangère (insertable = false, updatable = false) car c'est fait par l'application
	// elle-même dans son
	// constructeur
	@ManyToOne()
	@JoinColumn(name = "sentence_id", insertable = false, updatable = false)
	private Sentences sentence;

	// constructeurs
	public MotsSentences() {

	}

	public MotsSentences(Mots m, Sentences s,int nbroccur) {
		this.mot=m;
                this.sentence=s;
		m.getSentences().add(this);
		s.getMot().add(this);
                this.id = new Id(m.getId(), s.getId());
                this.nbroccur = nbroccur;
	}
        
        //Getter et Setter

    public Id getId() {
        return id;
    }

    public Mots getMot() {
        return mot;
    }

    public Sentences getSentence() {
        return sentence;
    }

    public int getNbroccur() {
        return nbroccur;
    }

    public void setNbroccur(int nbroccur) {
        this.nbroccur = nbroccur;
    }

        
}
