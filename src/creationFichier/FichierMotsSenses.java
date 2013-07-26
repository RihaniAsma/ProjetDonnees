/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creationFichier;

import au.com.bytecode.opencsv.CSVWriter;
import extractiondesdonnees.entity.Mots;
import extractiondesdonnees.entity.MotsSentences;
import extractiondesdonnees.entity.SenseLemma;
import extractiondesdonnees.entity.Sentences;
import extractiondesdonnees.entityCRUD.SenseLemmaCRUD;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author DELL
 */
public class FichierMotsSenses {
      public static void main(String[] args) throws FileNotFoundException, IOException{
          
          
    EntityManagerFactory emf;
EntityManager em;
emf = Persistence.createEntityManagerFactory("ExtractionDesDonneesPU");
em = null;
// on récupère un EntityManager à  partir de l'EntityManagerFactory précédent
em = emf.createEntityManager();
    SenseLemmaCRUD testsense=new SenseLemmaCRUD(em);
    long size=testsense.contsize();
      //List<SenseLemma> listsense=testsense.getAll();
       CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/MotsSenses/extractMotsSense.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
          int k=1;
        while(k<=size){
            SenseLemma senses=testsense.getId(k);
            String lemma=senses.getLemma().getLemma();
            List<Sentences> sentences=senses.getSentences();
                 if(!sentences.isEmpty())
                 {    //System.out.println("pas de sentences avec sense"+senses.getSenseid()+" de lemma "+senses.getLemma().getLemma());
                 
                 List<MotsSentences> mottotal=new ArrayList<MotsSentences>();
                 for(Sentences sentence:sentences)
                 {
                    // System.out.println(sentence.getSentense());
                     Set<MotsSentences> mts=sentence.getMot();
                     for(MotsSentences mot:mts){
                         String m=mot.getMot().getMot();
                        if(!(m.equalsIgnoreCase(lemma)))
                           mottotal.add(mot);    
                 }
                     
                 }
                // System.out.println(mottotal.size());
                  List<Mots> al2 = new ArrayList();
                  //recup la liste des mots qui occure avec le sense
        for (int i=0; i<mottotal.size(); i++) {
            Mots o = mottotal.get(i).getMot();
            //System.out.println(o.getMot());
            if (!al2.contains(o))

                al2.add(o);
        }
      //calcul d'occurence d'un mot avec un sense d'une lemma
       for(int i=0;i<al2.size();i++){
        int nbroccurmot=0;
        Mots ms=al2.get(i);
        for (int j=0; j<mottotal.size(); j++){
        if(mottotal.get(j).getMot().equals(ms))
        {
            nbroccurmot=nbroccurmot+mottotal.get(j).getNbroccur();
           //System.out.println(al2.get(i).getMot()+"-->"+nbroccurmot);
        }
     
        }
       int mot_id=al2.get(i).getId();
        int sense_id=senses.getId();
        String[] entree={String.valueOf(mot_id),String.valueOf(sense_id),String.valueOf(nbroccurmot)};
        writer.writeNext(entree);
        System.out.println(ms.getMot()+"-->"+nbroccurmot+" "+senses.getSenseid()+" "+lemma);
     /* FichierMotsOccurMots  test=new FichierMotsOccurMots();
        String mot=test.getMots(al2.get(i).getId());
        String sense=lemma+senses.getSenseid();
        String[] entree={mot,sense,String.valueOf(nbroccurmot)};
        writer.writeNext(entree);
        System.out.println(ms.getMot()+"-->"+nbroccurmot+" "+senses.getSenseid()+" "+lemma);*/
       }
                 }
                 k++;
        }
    
      writer.close();
      
      }
    
}
