/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractiondesdonnees;

import extractiondesdonnees.entity.MotOccurMot;
import extractiondesdonnees.entity.Mots;
import extractiondesdonnees.entityCRUD.MotsCRUD;
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
public class RemplirMotOccurMot {
    public static void main(String[] args) throws FileNotFoundException, IOException {
     EntityManagerFactory emf;
EntityManager em;
EntityTransaction tx;
emf = Persistence.createEntityManagerFactory("ExtractionDesDonneesPU");
em = null;
// on récupère un EntityManager à  partir de l'EntityManagerFactory précédent
em = emf.createEntityManager();
 tx=em.getTransaction();
  MotsCRUD motscrud=new MotsCRUD(em);
  
 // String mot=motscrud.texteMot(3531);
  //System.out.println(mot);
  int i=15500;
 while(i<18220)
  {
  Scanner scanner=new Scanner(new File("data/extract/extractMotMot/nbr/extract"+i+".csv"));
       
      tx.begin();
       //int k=1;
      while(scanner.hasNext()){
         // System.out.println(k);
           String line=scanner.nextLine();    
    String[] Line = line.split("\t");
        Mots mot1=motscrud.getOne(Integer.parseInt(Line[0]));
        Mots mot2=motscrud.getOne(Integer.parseInt(Line[1]));
        int nbroccur=Integer.parseInt(Line[2]);
    MotOccurMot motoccurmot= new MotOccurMot(mot1,mot2,nbroccur);
    em.persist(motoccurmot);
    // k++;
    }
 tx.commit();
  System.out.println("fichier "+i+" est terminer");
   i++;
  scanner.close();
 }
  
  em.close();
    emf.close();
}
}
