/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creationFichier;

import extractiondesdonnees.entityCRUD.LemmaCRUD;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author acer
 */
public class FichierMots {
        public static void main(String[] args) throws FileNotFoundException, IOException{
             EntityManagerFactory emf;
       EntityManager em;
       EntityTransaction tx;
       emf = Persistence.createEntityManagerFactory("ExtractionDesDonneesPU");
       em = null;
          em = emf.createEntityManager();
  
    
Scanner scanner=new Scanner(new File("data/input/essai.txt"), "UTF-8");
        Set<String> mots=new TreeSet<String>();
// On boucle sur chaque champ detecté
while (scanner.hasNextLine()) {
    String sentence = scanner.nextLine().toLowerCase();
 
         if(!sentence.isEmpty()&&!sentence.startsWith(" ")&&!sentence.contains("$")&&!sentence.contains("&"))
         {
             String[] listmot=sentence.split(" ");
             for(String mot:listmot)
                 if(!mot.contains("|"))
                 mots.add(mot.toLowerCase());
  
         }
       
}
LemmaCRUD motcrud=new LemmaCRUD(em);
 Vector vec= new  Vector();
 int i=1;
 for(String mot:mots){
     if(motcrud.exist(mot))
     { System.out.println("c'est un lemma "+ mot+" "+i);
     
     i++;}
     else
     vec.add(mot);
 }
 FileManager managefile=new FileManager();
 managefile.creation_fichier("data/extract/mot/mot.txt",vec, "UTF-8");
        
    scanner.close();
    em.close();
    emf.close();
    }
}
