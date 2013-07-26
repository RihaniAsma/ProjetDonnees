/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creationFichier;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import extractiondesdonnees.entity.Mots;
import extractiondesdonnees.entityCRUD.MotsCRUD;
import extractiondesdonnees.entityCRUD.MotsSentencesCRUD;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class FichierMotsOccurMots {

    public FichierMotsOccurMots() {
    }
    
    
    public String getMots(int idmot) throws FileNotFoundException, IOException{
        boolean found = false;
        boolean finish = false;
        String word=null;
        CSVReader reader = new CSVReader(new FileReader("data/extract/mot/mot.txt"), '\t');
        String[] nextLine;
        int i=1;
        while (((nextLine = reader.readNext()) != null) && (finish == false)) {
            if (i==idmot) {
                found = true;
                word=nextLine[0];
            }
            if (found && i!=idmot) {
                finish = true;
            }
            i++;
        }
        reader.close();
    return word;
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FichierMotsOccurMots  test= new FichierMotsOccurMots();
       
        CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/carc/extractMotMotall1.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
    // int i=13;
    int j=1;
        // while(i<17){
           CSVReader reader = new CSVReader(new FileReader("data/extract/extractMotMot/nbr/extractall2.csv"), '\t');
         String[] nextLine;
        while (((nextLine = reader.readNext()) != null)){
             System.out.println(j);
        int idm1=Integer.parseInt(nextLine[0]);
        int idm2=Integer.parseInt(nextLine[1]);
        String m1=test.getMots(idm1);
        String m2=test.getMots(idm2);
        String[] entree={m1,m2,nextLine[2]};
        writer.writeNext(entree);
      
       j++;
        }
        reader.close();
         //i++;
     // }
      writer.close();
      
  /*  EntityManagerFactory emf;
    EntityManager em;
    emf = Persistence.createEntityManagerFactory("ExtractionDesDonneesPU");
    em = null;
    // on récupère un EntityManager à  partir de l'EntityManagerFactory précédent
    em = emf.createEntityManager();
  /*  long nbrocc;
     MotsSentencesCRUD motssentencescrud=new MotsSentencesCRUD(em);
     int size=18219;
     int j=1;
     CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/nbr/extract.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);    
      while(j<=size){   
           System.out.println(j);
           for(int k=j+1;k<=size;k++){
           nbrocc=motssentencescrud.countSentences(j,k);
        if(nbrocc>0){
           // String m1=test.getMots(j);
           // String m2=test.getMots(k);
           //String[] entree = {m1,m2,String.valueOf(nbrocc)}; 
            String[] entree = {String.valueOf(j),String.valueOf(k),String.valueOf(nbrocc)};
             writer.writeNext(entree); 
      }
        }
           j++;
         writer.close(); 
       }*/
   /* for (int i=1;i<18220;i++){
           Query resultat= em.createQuery("select c.id.mot_id,p.id.mot_id,count(*) as occur   from MotsSentences as p , MotsSentences as c where p.id.mot_id<>c.id.mot_id and p.id.mot_id<c.id.mot_id and p.id.mot_id= :id and c.id.sentence_id=p.id.sentence_id group by c.id.mot_id");
        resultat.setParameter("id", i);
      List res= resultat.getResultList();
      // System.out.println(res.size());
       CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/nbr/extract"+i+".csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);    
     System.out.println(i) ;
       for (Object ligneAsObject : res) {

     // ligne correspond à une des lignes du résultat
    Object[] ligne = (Object[])ligneAsObject ;
	
     // cette liste est composée de deux éléments : nom et prenom
    int idm1 = (int) ligne[0] ;
    int idm2 = (int) ligne[1] ;
    long occur=(long) ligne[2];
	String[] entree = {String.valueOf(idm1),String.valueOf(idm2),String.valueOf(occur)};
             writer.writeNext(entree);
    
}
       writer.close();
    }      
    em.close();
    emf.close();*/
}
    
}
