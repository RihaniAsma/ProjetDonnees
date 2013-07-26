/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractiondesdonnees;

import extractiondesdonnees.entity.Mots;
import extractiondesdonnees.entityCRUD.MotsCRUD;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Administrateur
 */
public class RemplirTabMots {
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
    
     EntityManagerFactory emf;
EntityManager em;
EntityTransaction tx;
emf = Persistence.createEntityManagerFactory("ExtractionDesDonneesPU");
em = null;
// on récupère un EntityManager à  partir de l'EntityManagerFactory précédent
em = emf.createEntityManager();
Scanner scanner=new Scanner(new File("data/extract/mot/mot.txt"), "UTF-8");
     //   Set<String> mots=new TreeSet<String>();
        int i=0;
// On boucle sur chaque champ detecté
         tx = em.getTransaction();
        tx.begin();
while (scanner.hasNextLine()) {
    String mot = scanner.nextLine();
 
       /*  if(!sentence.isEmpty()&&!sentence.startsWith(" ")&&!sentence.contains("$")&&!sentence.contains("&"))
         {
             String[] listmot=sentence.split(" ");
             for(String mot:listmot)
                 if(!mot.contains("|"))
                 mots.add(mot.toLowerCase());
        // System.out.println((i+1)+" est traité");
         }
         /*else{
         System.out.println((i+1)+" non traité");
         }
         i++;*/
      
    System.out.println(mot);
   
        MotsCRUD motcru=new MotsCRUD(em);
        Mots m=new Mots();
       m.setMot(mot);
       motcru.saveOne(m);
       System.out.println(mot);  
    
    }
    tx.commit();
        
    scanner.close();
    }
    
}
