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
public class MotsSenses implements Serializable {
     private static final long serialVersionUID = 1L;
     @Embeddable
	public static class Id implements Serializable {
       private static final long serialVersionUID = 1L;
		// composantes de la clé composite
		// pointe sur un mot
		@Column(name = "mot_id")
		private int mot_id;

		// pointe sur une sentence
		@Column(name = "sense_id")
		private int sense_id;

		// constructeurs
		public Id() {
		}

		// getters et setters
                public Id(int motid, int senseid) {
			super();
			this.mot_id = motid;
			this.sense_id = senseid;
		}

        public int getMot_id() {
            return mot_id;
        }

        public void setMot_id(int mot_id) {
            this.mot_id = mot_id;
        }

        public int getSense_id() {
            return sense_id;
        }

        public void setSense_id(int sense_id) {
            this.sense_id = sense_id;
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
            if (this.sense_id != other.sense_id) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 13 * hash + this.mot_id;
            hash = 13 * hash + this.sense_id;
            return hash;
        }

        @Override
        public String toString() {
            return "Id{" + "mot_id=" + mot_id + ", sense_id=" + sense_id + '}';
        }
                
                
     }
     
     // champs de la classe MotsSentences
	// clé composite
	@EmbeddedId
	private Id id = new Id();
        
        @Column
        private int nbroccur;
        
        // relation principale MotsSenses (many) -> Mots (one)
	// implémentée par la clé étrangère : mot_id (MotsSenses (many) -> Mots (one)
	// mot_id est en même temps élément de la clé primaire composite
	// JPA ne doit pas gérer cette clé étrangère (insertable = false, updatable = false) car c'est fait par l'application
	// elle-même dans son
	// constructeur
	@ManyToOne
	@JoinColumn(name = "mot_id", insertable = false, updatable = false)
	private Mots mot;

	// relation principale MotsSenses -> sense
	// implémentée par la clé étrangère : sentence_id (MotsSenses (many) -> sense (one)
	// sense_id est en même temps élément de la clé primaire composite
	// JPA ne doit pas gérer cette clé étrangère (insertable = false, updatable = false) car c'est fait par l'application
	// elle-même dans son
	// constructeur
	@ManyToOne()
	@JoinColumn(name = "sense_id", insertable = false, updatable = false)
	private SenseLemma senseLemma;
        
        // constructeurs
	public MotsSenses() {

	}

	public MotsSenses(Mots m, SenseLemma s,int nbroccur) {
		this.mot=m;
                this.senseLemma=s;
		m.getSenseLemma().add(this);
		s.getMot().add(this);
                this.id = new MotsSenses.Id(m.getId(), s.getId());
                this.nbroccur = nbroccur;
	}

         //Getter et Setter
    public Id getId() {
        return id;
    }

    public Mots getMot() {
        return mot;
    }

    public void setMot(Mots mot) {
        this.mot = mot;
    }

    public int getNbroccur() {
        return nbroccur;
    }

    public void setNbroccur(int nbroccur) {
        this.nbroccur = nbroccur;
    }

    public SenseLemma getSenseLemma() {
        return senseLemma;
    }

    public void setSenseLemma(SenseLemma senseLemma) {
        this.senseLemma = senseLemma;
    }

   
        
}
