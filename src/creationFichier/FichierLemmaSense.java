/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creationFichier;

import au.com.bytecode.opencsv.CSVWriter;
import extractiondesdonnees.entity.SenseLemma;
import extractiondesdonnees.entityCRUD.SenseLemmaCRUD;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import prefuse.data.io.DataIOException;

/**
 *
 * @author acer
 */
public class FichierLemmaSense {
    public static void main(String[] argv) throws FileNotFoundException, DataIOException, IOException {
    EntityManagerFactory emf;
       EntityManager em;
       EntityTransaction tx;
       emf = Persistence.createEntityManagerFactory("ExtractionDesDonneesPU");
       em = null;
          em = emf.createEntityManager();
      tx=em.getTransaction();
      tx.begin();
             CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/senselemma.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
    
    SenseLemmaCRUD senselemmacrud=new SenseLemmaCRUD(em);
          List<SenseLemma> listSense=senselemmacrud.getAll();
          for(SenseLemma sense:listSense){
               String lemma=sense.getLemma().getLemma();
              String textesense=lemma+sense.getSenseid();
             
              int nbroccu=sense.getSentences().size();
             
              String[] entree={textesense,lemma,String.valueOf(nbroccu)};
        writer.writeNext(entree);     
}
          writer.close();
     tx.commit();
        em.close();
        emf.close();
    } 
}
