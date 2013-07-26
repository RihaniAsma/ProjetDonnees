/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractiondesdonnees.entityCRUD;

import extractiondesdonnees.entity.Mots;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class MotsCRUD {
    private EntityManager em;

    public MotsCRUD(EntityManager em) {
        this.em = em;
    }

	public Mots getOne(int id) {
		return em.find(Mots.class, id);
	}
	
	// obtenir une un mot
	public Mots getMot(String mot){
		Mots resultat=(Mots) em.createQuery("select p from Mots p where p.mot = :mot")
		.setParameter("mot", mot).getSingleResult();
		return resultat;
		
	}

	
	/*public List<Mots> getAllSauf$() {
		return em.createQuery("select p from Mots p where p.mot NOT Like '$%' and p.mot NOT Like '&%'").getResultList();
	}*/
        
        public List<Mots> getAll() {
		return em.createQuery("select p from Mots p").getResultList();
	}
        
        public boolean exist(String mot){
        boolean exist = false;
        Query resultat= em.createQuery("select count(*) from Mots p where p.mot= :mot");
        resultat.setParameter("mot", mot);
        long x=(long) resultat.getSingleResult();
        if(x!=0)
            exist=true;
        return exist;
        }
 
	public void saveOne(Mots mot) {
		em.persist(mot);
	}

	public Mots updateOne(Mots mot) {
		return em.merge(mot);
	}

	public void deleteOne(Integer id) {
		Mots mot = em.find(Mots.class, id);
		em.merge(mot);
		em.remove(mot);
	}
        
        public long countSize(){
        return (long) em.createQuery("select count(*) from Mots").getSingleResult();
        }
        
        public String texteMot(int id){
        Query resultat= em.createQuery("select p.mot from Mots p where p.id= :id");
        resultat.setParameter("id", id);
        String mot=(String) resultat.getSingleResult();
        return mot;
        }
        public List<Mots> getEnsembleMot(int x,int y) {
	  List<Mots> resultat=new ArrayList();	
           Query q= em.createQuery("select p from Mots p");
               q.setFirstResult(x);
               q.setMaxResults(y);
               resultat=q.getResultList();
                return resultat;
	}
        
        
}
