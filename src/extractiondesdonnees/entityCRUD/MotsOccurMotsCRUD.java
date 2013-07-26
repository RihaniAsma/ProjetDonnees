/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractiondesdonnees.entityCRUD;

import extractiondesdonnees.entity.MotOccurMot;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.persistence.*;
import prefuse.data.io.DataIOException;

/**
 *
 * @author Administrateur
 */
public class MotsOccurMotsCRUD {
     private EntityManager em;

    public MotsOccurMotsCRUD(EntityManager em) {
        this.em = em;
    }
     
      public MotOccurMot getMotoccurMot(int x,int y) {	
           Query q= em.createQuery("select p from MotOccurMot p");
               q.setFirstResult(x);
               q.setMaxResults(y);
             MotOccurMot  resultat=(MotOccurMot) q.getSingleResult();
                return resultat;
	}
      public long countSize(){
        return (long) em.createQuery("select count(*) from MotOccurMot").getSingleResult();
        }
      
      public List<MotOccurMot> getAll() {
		return em.createQuery("select p from MotOccurMot p").getResultList();
	}
      public boolean exist(int mot_id1,int mot_id2){
        boolean exist = false;
        Query resultat= em.createQuery("select count(*) from MotOccurMot p where p.id.mot_id1 in(:mot_id1,:mot_id2) and p.id.mot_id2 in(:mot_id1,:mot_id2)");
        resultat.setParameter("mot_id1", mot_id1);
        resultat.setParameter("mot_id2", mot_id2);
        long x=(long) resultat.getSingleResult();
        if(x!=0)
            exist=true;
        return exist;
        }
    /*  public static void main(String[] argv) throws FileNotFoundException, DataIOException, IOException {
             EntityManagerFactory emf;
       EntityManager em;
       EntityTransaction tx;
       emf = Persistence.createEntityManagerFactory("ExtractionDesDonneesPU");
       em = null;
     // on récupère un EntityManager à  partir de l'EntityManagerFactory précédent
      em = emf.createEntityManager();
      int i=0;
      MotsOccurMotsCRUD MotsOccurMotsCRUD=new MotsOccurMotsCRUD(em);
      MotOccurMot motoccurmot=MotsOccurMotsCRUD.getMotoccurMot(0, 1);
      System.out.println(motoccurmot.getMot1().getId());
       System.out.println(motoccurmot.getMot2().getId());
       System.out.println(motoccurmot.getNbroccur());
      em.close();
      emf.close();
      }
    */
}
