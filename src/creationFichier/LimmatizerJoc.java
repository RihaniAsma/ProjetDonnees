/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creationFichier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

/**
 *
 * @author Administrateur
 */
public class LimmatizerJoc {

    public LimmatizerJoc() {
    }
    
    
     public String deleteExtraWord(String sentence){
       String phrase;
               //,phrase1;
       phrase=sentence.replaceAll("\\s", " ");
       //phrase1=phrase.replaceAll("[/-]"," ");
       return phrase;
    }
     public static void main(String[] args) throws FileNotFoundException, IOException{ 
/*  //cette methode a limmatizer le contenu de fichier joc-fr
            String[] pathsource={"part61","part62","part63","part64"};
        for(String source:pathsource ){
        String destination=source+"Lematizer.txt";
        FileManager managefile=new FileManager();
        Vector vec=managefile.fileToVector("data/input/" + source+".txt", "UTF-8");
        Vector result= new Vector();
        for(int i=0;i<vec.size();i++){
            String ligne=vec.elementAt(i).toString();
            if(ligne.equals(" "))
            {
             result.add(ligne);
            System.out.println(i+" de "+ source+" est vide");
            }
            else
            {
        String ligne2=TokenizerPipeline.tokenizeSentence(ligne);
        result.add(ligne2);
        System.out.println(i+" de "+ source+" est limmatizer");
            }
        }
        vec.clear();
        managefile.creation_fichier("data/input/" + destination, result, "UTF-8");
        result.clear();
        System.out.println("Le fichier "+destination+" est ecrit avec succées");
    }
  }*/
      
     // cette methode supprime tt les cc indésirable de fichier jac-fr
 
   /*  Scanner scanner=new Scanner(new File("data/input/" + "essai.txt"), "UTF-8");
 
// On boucle sur chaque champ detecté
while (scanner.hasNextLine()) {
    String line = scanner.nextLine();
     if(line.isEmpty()||line.startsWith(" "))
         {
             
          System.out.println("Sentence est vide");
          //sentence=listsentences[i];
         }
 else
    System.out.println(line);
	//faites ici votre traitement
}
 
scanner.close();*/
    LimmatizerJoc essai=new LimmatizerJoc(); 
     FileManager test=new FileManager();
     Vector vec=test.fileToVector("data/input/"+ "essai.txt", "UTF-8");
        System.out.println(vec.size());
        Vector result= new Vector();
        for(int i=0;i<vec.size();i++){
        String ligne=(String) vec.elementAt(i);
        String ligne2=essai.deleteExtraWord(ligne);
       result.add(ligne2);}
        vec.clear();
        System.out.println(result.size());
        test.creation_fichier("data/input/" + "jocessai-fr.txt", result, "UTF-8");
        result.clear();
        
}
}
