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
public class MotOccurMot implements Serializable {
    private static final long serialVersionUID = 1L;
    @Embeddable
	public static class Id implements Serializable {
       private static final long serialVersionUID = 1L;
		// composantes de la clé composite
		// pointe sur un mot
		@Column(name = "mot_id1")
		private int mot_id1;

		// pointe sur une sentence
		@Column(name = "mot_id2")
		private int mot_id2;

		// constructeurs
		public Id() {
		}

		// getters et setters
                public Id(int mot_id1, int mot_id2) {
			super();
			this.mot_id1 = mot_id1;
			this.mot_id2 = mot_id2;
		}

        public int getMot_id1() {
            return mot_id1;
        }

        public void setMot_id1(int mot_id1) {
            this.mot_id1 = mot_id1;
        }

        public int getMot_id2() {
            return mot_id2;
        }

        public void setMot_id2(int mot_id2) {
            this.mot_id2 = mot_id2;
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
            if (this.mot_id1 != other.mot_id1) {
                return false;
            }
            if (this.mot_id2 != other.mot_id2) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 89 * hash + this.mot_id1;
            hash = 89 * hash + this.mot_id2;
            return hash;
        }  
    }
       

	// clé composite
	@EmbeddedId
	private Id id = new Id();
        
        @Column
        private long nbroccur;
        
        @ManyToOne
	@JoinColumn(name = "mot_id1", insertable = false, updatable = false)
	private Mots mot1;
        
        @ManyToOne
	@JoinColumn(name = "mot_id2", insertable = false, updatable = false)
	private Mots mot2;

    public MotOccurMot() {
    }
        
        
        public MotOccurMot(Mots mot_id1,Mots mot_id2,long nbroccur) {
               this.mot1=mot_id1;
                this.mot2=mot_id2;
		mot_id1.getMotOccurMot().add(this);
		mot_id2.getMotOccurMot2().add(this);
                this.id = new Id(mot_id1.getId(),mot_id2.getId());
                this.nbroccur = nbroccur;
	}

    public long getNbroccur() {
        return nbroccur;
    }

    public void setNbroccur(long nbroccur) {
        this.nbroccur = nbroccur;
    }

    public Id getId() {
        return id;
    }

    public Mots getMot1() {
        return mot1;
    }

    public void setMot1(Mots mot1) {
        this.mot1 = mot1;
    }

    public Mots getMot2() {
        return mot2;
    }

    public void setMot2(Mots mot2) {
        this.mot2 = mot2;
    }
        
        
}
