/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creationFichier;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import extractiondesdonnees.entityCRUD.LemmaCRUD;
import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.commons.collections.Bag;
import org.apache.commons.collections.HashBag;

/**
 *
 * @author Administrateur
 */
public class FichierMotsSentences {

    public FichierMotsSentences() {
    }
    
    public int getMots(String mot) throws FileNotFoundException, IOException{
        boolean found = false;
        boolean finish = false;
        int idmot=0;
        CSVReader reader = new CSVReader(new FileReader("data/extract/mot/mot.txt"), '\t');
        String[] nextLine;
        int i=1;
        while (((nextLine = reader.readNext()) != null) && (finish == false)) {
            if (mot.equalsIgnoreCase(nextLine[0])) {
                found = true;
                idmot=i;
            }
            if (found && !(mot.equalsIgnoreCase(nextLine[0]))) {
                finish = true;
            }
            i++;
        }
        reader.close();
    return idmot;
    }
    public static void main(String[] args) throws FileNotFoundException, IOException{

        //remplissage de Motssentences tab
   EntityManagerFactory emf;
    EntityManager em;
    emf = Persistence.createEntityManagerFactory("ExtractionDesDonneesPU");
    em = null;
    // on récupère un EntityManager à  partir de l'EntityManagerFactory précédent
    em = emf.createEntityManager();
   CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotSentence/extractword.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
        Scanner scanner=new Scanner(new File("data/input/essai.txt"), "UTF-8");
    int z=1;
    LemmaCRUD lemmacrud= new LemmaCRUD(em);
    FichierMotsSentences test=new FichierMotsSentences();
    while (scanner.hasNextLine()) {
    String sentence = scanner.nextLine().toLowerCase();
 
         if(sentence.isEmpty()||sentence.startsWith(" ")||sentence.contains("$")||sentence.contains("&"))
         {
             
          System.out.println(z+" est vide");
          //sentence=listsentences[i];
         }
         else{
     //recup list mot
     String[] eLigne=sentence.split(" ");
    // System.out.println(eLigne.length);
     Set<String> lMot=new HashSet<String>();
     Bag b = new HashBag();
     //mettre mot dans une liste et une bag pour faciliter le calcul
     for(String mot:eLigne)
     { if((!mot.isEmpty())&&(!mot.equals(" "))&&(!mot.contains("|")))
     {
         if(!lemmacrud.exist(mot))
         {  lMot.add(mot.toLowerCase());
       b.add(mot.toLowerCase());}
     }
     }
      //recup les mots qui existe déja dans la table Mots
      // MotsCRUD motcru=new MotsCRUD(em);
          //si la tab Mots n'est pas vide on vérifier si le mot existe déja ou pas
       for(String mot3:lMot){  
          // System.out.println(mot3);
           int m=test.getMots(mot3);
           int x=b.getCount(mot3);
             String[] entree = {String.valueOf(m),String.valueOf(z),String.valueOf(x)};
               writer.writeNext(entree);
                System.out.println(z+" est traité");
       }
        lMot.clear();
        b.clear();
         }
     z++;
    }
    writer.close();
// fin EntityManager
em.close();
// fin EntityManagerFactory
emf.close();
       
    
    }
    
}
