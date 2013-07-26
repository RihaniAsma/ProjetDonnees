/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractiondesdonnees.entityCRUD;

import extractiondesdonnees.entity.Lemma;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class LemmaCRUD {
	private EntityManager em;

    public LemmaCRUD(EntityManager em) {
        this.em = em;
    }

	public Lemma getOne(int id) {
		return em.find(Lemma.class, id);
	}
	
	// obtenir une Lemma
	public Lemma getLemma(String lemma){
		Lemma resultat=(Lemma) em.createQuery("select p from Lemma p where p.Lemma = :lemma")
		.setParameter("lemma", lemma).getSingleResult();
		return resultat;
		
	}
   public boolean exist(String lemma){
        boolean exist = false;
        Query resultat= em.createQuery("select count(*) from Lemma p where p.Lemma = :lemma");
        resultat.setParameter("lemma", lemma);
        long x=(long) resultat.getSingleResult();
        if(x!=0)
            exist=true;
        return exist;
        }
	@SuppressWarnings("unchecked")
	public List<Lemma> getAll() {
		return em.createQuery("select p from Lemma p").getResultList();
	}

	public void saveOne(Lemma lemma) {
		em.persist(lemma);

	}

	public Lemma updateOne(Lemma lemma) {
		return em.merge(lemma);
	}

	public void deleteOne(Integer id) {
		Lemma lemma = em.find(Lemma.class, id);
		em.merge(lemma);
		em.remove(lemma);
	}

}
