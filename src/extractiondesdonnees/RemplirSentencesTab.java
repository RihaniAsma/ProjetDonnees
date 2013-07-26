/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractiondesdonnees;

import extractiondesdonnees.entity.Sentences;
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
public class RemplirSentencesTab {

    public RemplirSentencesTab() {
    }
    public static void main(String[] args) throws FileNotFoundException, IOException{
    
    
        //remplissage de sentences
    EntityManagerFactory emf;
EntityManager em;
EntityTransaction tx;
emf = Persistence.createEntityManagerFactory("ExtractionDesDonneesPU");
em = null;
// on récupère un EntityManager à  partir de l'EntityManagerFactory précédent
em = emf.createEntityManager();
Scanner scanner=new Scanner(new File("data/input/essai.txt"), "UTF-8");
 int i=0;
  tx = em.getTransaction();
        tx.begin();
// On boucle sur chaque champ detecté
while (scanner.hasNextLine()) {
    String sentence = scanner.nextLine();
 
         if(sentence.isEmpty()||sentence.startsWith(" "))
         {
             
          System.out.println("Sentence "+(i+1)+" est vide");
          //sentence=listsentences[i];
         }
         else{
            // sentence=listsentences[i];
        
     Sentences ss=new Sentences();
     ss.setSentense(sentence.toLowerCase());
     ss.setId(i+1);
     em.persist(ss);
     System.out.println("Sentence "+(i+1)+" ajouter");
         }
         i++;
     }
tx.commit();
scanner.close();
// fin EntityManager
em.close();
// fin EntityManagerFactory
emf.close();
  }
}
