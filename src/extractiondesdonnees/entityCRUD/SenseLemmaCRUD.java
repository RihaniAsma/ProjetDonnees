/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractiondesdonnees.entityCRUD;

import extractiondesdonnees.entity.SenseLemma;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class SenseLemmaCRUD {
     private EntityManager em;

    public SenseLemmaCRUD(EntityManager em) {
        this.em = em;
    }
    public SenseLemma getId(int id){
    return em.find(SenseLemma.class, id);
    }

	public SenseLemma getOne(int id,Integer lemma_id) {
		Query resultat= em.createQuery("select p from SenseLemma p where p.id = :id and p.lemma.id = :lemma_id");
		resultat.setParameter("id", id);
                resultat .setParameter("lemma_id", lemma_id);
                SenseLemma sense=(SenseLemma) resultat.getSingleResult();
                return sense;
	}
	
	// obtenir les senses d'un lemma
	public List<SenseLemma> getSenseLemma(String lemma){
                List<SenseLemma> resultat=new ArrayList<SenseLemma>();
		resultat=em.createQuery("select p from SenseLemma p where p.lemma = :lemma")
		.setParameter("lemma", lemma).getResultList();
		return resultat;
		
	}

	@SuppressWarnings("unchecked")
	public List<SenseLemma> getAll() {
		return em.createQuery("select p from SenseLemma p").getResultList();
	}

	public void saveOne(SenseLemma sense) {
		em.persist(sense);
	}

	public SenseLemma updateOne(SenseLemma sense) {
		return em.merge(sense);
	}

	public void deleteOne(Integer id) {
		SenseLemma sense = em.find(SenseLemma.class, id);
		em.merge(sense);
		em.remove(sense);
	}

    public long contsize() {
         return (long) em.createQuery("select count(*) from SenseLemma").getSingleResult();
    }
  
}
