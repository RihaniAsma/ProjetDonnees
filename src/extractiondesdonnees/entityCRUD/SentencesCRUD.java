/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractiondesdonnees.entityCRUD;

import extractiondesdonnees.entity.Sentences;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class SentencesCRUD {
    private EntityManager em;

    public SentencesCRUD(EntityManager em) {
        this.em = em;
    }

	public Sentences getOne(int id) {
		return em.find(Sentences.class, id);
	}
	
	public List<Sentences> getAll() {
		return em.createQuery("select p from Sentences p").getResultList();
	}
        
        public List<Integer> getAllId() {
	List<Integer> resultat=em.createQuery("select p.id from Sentences p where p.sentense not like '$%' and p.sentense not like '&%' ").getResultList();
	return resultat;
        }
        
        public Sentences getCentSentences(int x,int y) {
	 // List<Sentences> resultat=new ArrayList();	
           Query q= em.createQuery("select p from Sentences p where p.sentense not like '$%' and p.sentense not like '&%'");
               q.setFirstResult(x);
               q.setMaxResults(y);
             Sentences  resultat=(Sentences) q.getSingleResult();
                return resultat;
	}
 public int getIdSentences(int x,int y) {
	 // List<Sentences> resultat=new ArrayList();	
           Query q= em.createQuery("select p.id from Sentences p where p.sentense not like '$%' and p.sentense not like '&%'");
               q.setFirstResult(x);
               q.setMaxResults(y);
             int  resultat=(int) q.getSingleResult();
                return resultat;
	}
	public void saveOne(Sentences sentence) {
		em.persist(sentence);
	}

	public Sentences updateOne(Sentences sentence) {
		return em.merge(sentence);
	}

	public void deleteOne(Integer id) {
		Sentences sentence = em.find(Sentences.class, id);
		em.merge(sentence);
		em.remove(sentence);
	}
        
         public long countSize(){
        return (long) em.createQuery("select count(*) from Sentences").getSingleResult();
        }
          public long countSize2(){
        return (long) em.createQuery("select count(*) from Sentences p where p.sentense not like '$%' and p.sentense not like '&%'").getSingleResult();
        }
 
}
