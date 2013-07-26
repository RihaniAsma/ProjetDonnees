/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractiondesdonnees;

import extractiondesdonnees.entity.Mots;
import extractiondesdonnees.entity.MotsSentences;
import extractiondesdonnees.entity.Sentences;
import extractiondesdonnees.entityCRUD.MotsCRUD;
import extractiondesdonnees.entityCRUD.SentencesCRUD;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Administrateur
 */
public class RemplirMotsSentencesTab {

    public static void main(String[] args) throws FileNotFoundException, IOException{

        //remplissage de Motssentences tab
    EntityManagerFactory emf;
    EntityManager em;
    EntityTransaction tx;
    emf = Persistence.createEntityManagerFactory("ExtractionDesDonneesPU");
    em = null;
    // on récupère un EntityManager à  partir de l'EntityManagerFactory précédent
    em = emf.createEntityManager();
    SentencesCRUD sentencecrud=new SentencesCRUD(em);
    MotsCRUD motscrud= new MotsCRUD(em);
    Mots mot;
      MotsSentences mst2;
      tx = em.getTransaction();
          tx.begin(); 
    Scanner scanner=new Scanner(new File("data/extract/extractMotSentence/extractword.csv"), "UTF-8");
      int i=1;
      while(scanner.hasNext()){
           String  line=scanner.nextLine();    
    String[] Line = line.split("\t");
    mot=motscrud.getOne(Integer.parseInt(Line[0]));
    Sentences sentence=sentencecrud.getOne(Integer.parseInt(Line[1]));
    int nbroccur=Integer.parseInt(Line[2]);
      mst2=new MotsSentences(mot,sentence,nbroccur);
           System.out.println(i);
            em.persist(mst2);
          i++;   
       }
  tx.commit();
// fin EntityManager
em.close();
// fin EntityManagerFactory
emf.close();
       
    
    }
}
