/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractiondesdonnees;

import creationFichier.FileManager;
import au.com.bytecode.opencsv.CSVReader;
import extractiondesdonnees.entity.Lemma;
import extractiondesdonnees.entity.SenseLemma;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Administrateur
 */
public class ExtractionDesDonnees {
    //methode pr recupere la liste des lemma utiliser avec le corpus ROMANSEVAL
    public List<Lemma> getListOfLemma() throws FileNotFoundException, IOException{
     
      
        List<Lemma> listLemma=new ArrayList<>();
    BufferedReader _buffline = new BufferedReader(new FileReader("data/input/wordlist-fr-adj.txt"), '\t');
   //lecture de la peremier ligne de fichier (nom des colonnes)
    String line=_buffline.readLine();
    //lecture des lignes est extraction des mots par ordre alpha
    while((line=_buffline.readLine())!=null){
         String[] Line = line.split("\t");
     Lemma lemma= new Lemma();
    lemma.setLemma(Line[2]);
    lemma.setCat(Line[0]);
    listLemma.add(lemma);
    
    }
    _buffline.close();
    List<Lemma> al2 = new ArrayList();
        for (int i=0; i<listLemma.size(); i++) {
            Lemma o = listLemma.get(i);
            if (!al2.contains(o))
                al2.add(o);
        }
    return al2;
    }

    public ExtractionDesDonnees() {
    }
    
    //methode pour recuperer les # sense et définition d'un lemma
 public List<SenseLemma> GetSenseOfLemma(String lemma,String cat) throws IOException{
    
     String path=null;
     switch(cat){
        case "A":
            path="data/dic/dic-a.txt";
            break;
        case "N":
            path="data/dic/dic-n.txt";
            break;
        case "V":
            path="data/dic/dic-v.txt";
            break;
    }
     List<SenseLemma> listSenseId=new ArrayList();
     CSVReader reader = new CSVReader(new FileReader(path), '\t');
         boolean found = false;
        boolean finish = false;
        String[] nextLine;
        while (((nextLine = reader.readNext()) != null) && (finish == false)) {
            if (nextLine[0].equalsIgnoreCase(lemma)) {
                found = true;
                SenseLemma sense=new SenseLemma();
                sense.setSenseid(nextLine[1]);
                sense.setDefenition(nextLine[2]);
                listSenseId.add(sense);
            }
            if (found && !nextLine[0].equalsIgnoreCase(lemma)) {
                finish = true;
            }
        }
        reader.close();
      
        return listSenseId;
 }
 
 public void deleteShortWord(){ 
     
        FileManager managefile=new FileManager();
        Vector vec=managefile.fileToVector("data/input/essai.txt", "UTF-8");
        Vector result= new Vector();
        for(int i=0;i<vec.size();i++){
            String ligne=vec.elementAt(i).toString().replaceAll("[+*]","");
            if(ligne.equals(" ")||ligne.startsWith("$")||ligne.startsWith("&"))
            {
             result.add(ligne);
            }
            else
            {
             String[] mots=ligne.split(" ");
             String ligne2="";
             for(String mot:mots){
             if(mot.length()>2)
                 ligne2=ligne2+mot+" ";
             }
             result.add(ligne2);
      
            }
        }
        vec.clear();
        managefile.creation_fichier("data/input/" + "result.txt", result, "UTF-8");
        result.clear();
    

 
 }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        ExtractionDesDonnees test=new ExtractionDesDonnees();
        test.deleteShortWord();
        // TODO code application logic here
      /*   EntityManagerFactory emf;
EntityManager em;
EntityTransaction tx;
emf = Persistence.createEntityManagerFactory("ExtractionDesDonneesPU");
em = null;
// on récupère un EntityManager à  partir de l'EntityManagerFactory précédent
em = emf.createEntityManager();
      SentencesCRUD motscrud=new SentencesCRUD(em);
     //MotsSentencesCRUD motssentencescrud=new MotsSentencesCRUD(em);
     List<Sentences> listmot=motscrud.getCentSentences(0,10);
     int size=listmot.size();
     System.out.println(size);
     for(int i=0;i<size;i++)
         System.out.println(listmot.get(i).getId());*/
    }
}
