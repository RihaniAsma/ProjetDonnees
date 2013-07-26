/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractiondesdonnees;

import extractiondesdonnees.entity.*;
import extractiondesdonnees.entityCRUD.MotsCRUD;
import extractiondesdonnees.entityCRUD.SenseLemmaCRUD;
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
public class RemplirMotsSenses {
    public static void main(String[] args) throws FileNotFoundException, IOException{
    
    
        //remplissage de sentences
    EntityManagerFactory emf;
EntityManager em;
EntityTransaction tx;
emf = Persistence.createEntityManagerFactory("ExtractionDesDonneesPU");
em = null;
// on récupère un EntityManager à  partir de l'EntityManagerFactory précédent
em = emf.createEntityManager();
SenseLemmaCRUD senselemmacrud=new SenseLemmaCRUD(em);
MotsCRUD motscrud=new MotsCRUD(em);
    Scanner scanner=new Scanner(new File("data/extract/MotsSenses/extractMotsSense.csv"), "UTF-8");
        tx=em.getTransaction();
      tx.begin();
       int k=1;
      while(scanner.hasNext()){
          System.out.println(k);
           String line=scanner.nextLine();    
    String[] Line = line.split("\t");
        Mots mot1=motscrud.getOne(Integer.parseInt(Line[0]));
        SenseLemma sense=senselemmacrud.getId(Integer.parseInt(Line[1]));
        int nbroccur=Integer.parseInt(Line[2]);
    MotsSenses motssenses=new MotsSenses(mot1,sense,nbroccur);
    em.persist(motssenses);
     k++;
    }
 tx.commit();
 em.close();
 emf.close();
    }
   
}
