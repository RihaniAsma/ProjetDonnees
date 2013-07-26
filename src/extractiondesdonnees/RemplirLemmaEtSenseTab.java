/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractiondesdonnees;

import extractiondesdonnees.entity.Lemma;
import extractiondesdonnees.entity.SenseLemma;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Administrateur
 */
public class RemplirLemmaEtSenseTab {
    
      public static void main(String[] args) throws FileNotFoundException, IOException{
        //remplissage de lemma et senselemma
     EntityManagerFactory emf;
     EntityManager em;
     EntityTransaction tx;
     emf = Persistence.createEntityManagerFactory("ExtractionDesDonneesPU");
     em = null;
     // on récupère un EntityManager à  partir de l'EntityManagerFactory précédent
    em = emf.createEntityManager();

           // début transaction
      tx = em.getTransaction();
    tx.begin();
                ExtractionDesDonnees essai=new ExtractionDesDonnees();
                //recuperation de tous les lemma
       List<Lemma> test=essai.getListOfLemma();
       //pour chaque lemma recup sense et def
       for(Lemma lemma:test){
       // System.out.println(lemma.getLemma()+"--->"+lemma.getCat());
                Lemma e1=new Lemma();
                e1.setCat(lemma.getCat());
                e1.setLemma(lemma.getLemma().toLowerCase());
                List<SenseLemma> senselemma=essai.GetSenseOfLemma(lemma.getLemma(), lemma.getCat());
               // e1.setSenseId(senselemma);
                em.persist(e1);
                for(SenseLemma ss:senselemma){
                SenseLemma entitéss=new SenseLemma();
                entitéss.setSenseid(ss.getSenseid());
                entitéss.setDefenition(ss.getDefenition().toLowerCase());
                entitéss.setLemma(e1);
                em.persist(entitéss);
                }
                }
                tx.commit();
    // fin EntityManager
    em.close();
    // fin EntityManagerFactory
     emf.close();
    
}
}