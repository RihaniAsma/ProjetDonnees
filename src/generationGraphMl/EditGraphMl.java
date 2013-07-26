/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package generationGraphMl;

import au.com.bytecode.opencsv.CSVReader;
import extractiondesdonnees.entity.*;
import extractiondesdonnees.entityCRUD.LemmaCRUD;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Table;
import prefuse.data.column.Column;
import prefuse.data.io.DataIOException;
import prefuse.data.io.GraphMLReader;
import prefuse.data.io.GraphMLWriter;

/**
 *
 * @author Administrateur
 */
public class EditGraphMl {
       //methode pr la cération de graph
     public Graph makeGraphpart1() throws FileNotFoundException, IOException {
           EntityManagerFactory emf;
       EntityManager em;
       EntityTransaction tx;
       emf = Persistence.createEntityManagerFactory("ExtractionDesDonneesPU");
       em = null;
          em = emf.createEntityManager();
      tx=em.getTransaction();
      tx.begin();
         //table pour les data des noeuds
        Table nodeData = new Table();
        //table pour les data des edges
        Table edgeData = new Table();
        //data node
        //nodeData.addColumn("Id",Node.class);
        nodeData.addColumn("texte", String.class);
        nodeData.addColumn("type", String.class);
        nodeData.addColumn("langue", String.class);
        edgeData.addColumn(Graph.DEFAULT_SOURCE_KEY, int.class);
        edgeData.addColumn(Graph.DEFAULT_TARGET_KEY, int.class);
        edgeData.addColumn("occur", int.class);
        //troisieme param est mis à true pr que les edges soit directed
        Graph g=new Graph(nodeData,edgeData,true);
        Node n1;
        Node n2;
        Edge esource;
        Edge etarget;
        //cree des noeuds et des arcs porteur des données
    LemmaCRUD lemmacrud=new LemmaCRUD(em);
     //recup tous les lemma
     List<Lemma> listLemma=lemmacrud.getAll();
     //pour chaque lemma get les senses et lier les noeuds senses aux lemma
   for(Lemma lemma:listLemma){
         String textelemma=lemma.getLemma();
         //cree un noud de types mots qui portes le lemma
          n1=g.addNode();
          n1.setString("texte",textelemma);
          n1.setString("type", "mot");
          n1.setString("langue", "Fr");
          //recup tous les senses d'un lemma
          List<SenseLemma> listSense=lemma.getSenseId();
          for(SenseLemma sense:listSense){
              String textesense=textelemma+sense.getSenseid();
          //cree un noeud de type sense
              n2=g.addNode();
              n2.setString("texte",textesense);
              n2.setString("type", "sense");
              n2.setString("langue", "Fr");
              //recup nombre occur d'un sense avec lemma
              int nbroccu=sense.getSentences().size();
              //cree un arc entre lemma et sense et vis versa
              esource=g.addEdge(n1, n2);
              esource.setInt("occur", nbroccu);
              etarget=g.addEdge(n2, n1);
              etarget.setInt("occur", nbroccu);
              //recuperer les mots qui occurre avec un sense
              Set<MotsSenses> listmot=sense.getMot();
              //ajoutter les mots qui occure avec le sense au graph
              for(MotsSenses motsense:listmot){
              Mots mot=motsense.getMot();
              String textemot=mot.getMot();
              int nbroccms=motsense.getNbroccur();
              //verif si le mots existe déja dans le graphe ou pas
              Node find=FindNode(g,textemot);
              if(find==null){
              Node m=g.addNode();
              m.setString("texte", textemot);
              m.setString("type", "mot");
              m.setString("langue", "Fr");
              esource=g.addEdge(n2, m);
              esource.setInt("occur", nbroccms);
              etarget=g.addEdge(m, n2);
              etarget.setInt("occur", nbroccms);
              }
              else{
               esource=g.addEdge(n2, find);
              esource.setInt("occur", nbroccms);
              etarget=g.addEdge(find, n2);
              etarget.setInt("occur", nbroccms);
              }
              }
          }
          
     }
     tx.commit();
        em.close();
        emf.close();
   /*
     CSVReader  reader = new CSVReader(new FileReader("data/extract/extractMotMot/extractmotall1.csv"), '\t');
        String[] nextLine;
        int i=1;
        while (((nextLine = reader.readNext()) != null)){
        System.out.println(i);
        String mot1=nextLine[0];
        String mot2=nextLine[1];
        int nbroccurmm=Integer.parseInt(nextLine[2]);
        Node m1=FindNode(g,mot1);
        Node m2=FindNode(g,mot2);
        if(m1!=null && m2!=null){
              esource=g.addEdge(m1, m2);
              esource.setInt("occur", nbroccurmm);
              etarget=g.addEdge(m2, m1);
              etarget.setInt("occur", nbroccurmm);
        
        }
        else if(m1==null && m2!=null){
        m1=g.addNode();
        m1.setString("texte", mot1);
        m1.setString("type","mot");
        m1.setString("langue", "Fr");
              esource=g.addEdge(m1, m2);
              esource.setInt("occur", nbroccurmm);
              etarget=g.addEdge(m2, m1);
              etarget.setInt("occur", nbroccurmm);
        }
        else if(m1!=null && m2==null){
        m2=g.addNode();
        m2.setString("texte", mot2);
        m2.setString("type","mot");
        m2.setString("langue", "Fr");
              esource=g.addEdge(m1, m2);
              esource.setInt("occur", nbroccurmm);
              etarget=g.addEdge(m2, m1);
              etarget.setInt("occur", nbroccurmm);
        }
        else if(m1==null && m2==null){
        m1=g.addNode(); 
        m1.setString("texte", mot1);
        m1.setString("type","mot");
        m1.setString("langue", "Fr");
        m2=g.addNode();
        m2.setString("texte", mot2);
        m2.setString("type","mot");
        m2.setString("langue", "Fr");
        esource=g.addEdge(m1, m2);
        esource.setInt("occur", nbroccurmm);
        etarget=g.addEdge(m2, m1);
        etarget.setInt("occur", nbroccurmm);
        }
        
        i++;
      }*/
     return g;
     }
public Graph makeGraphMlpart2(String path) throws FileNotFoundException, IOException{
    Graph g = null;
        Edge esource;
        Edge etarget;
try {
    g = new GraphMLReader().readGraph(path);
    CSVReader  reader = new CSVReader(new FileReader("data/extract/extractMotMot/carc/extract4.csv"), '\t');
        String[] nextLine;
        int i=1;
        while (((nextLine = reader.readNext()) != null)){
        System.out.println(i);
        String mot1=nextLine[0];
        String mot2=nextLine[1];
        int nbroccurmm=Integer.parseInt(nextLine[2]);
        Node m1=FindNode(g,mot1);
        Node m2=FindNode(g,mot2);
        if(m1!=null && m2!=null){
              esource=g.addEdge(m1, m2);
              esource.setInt("occur", nbroccurmm);
              etarget=g.addEdge(m2, m1);
              etarget.setInt("occur", nbroccurmm);
        
        }
        else if(m1==null && m2!=null){
        m1=g.addNode();
        m1.setString("texte", mot1);
        m1.setString("type","mot");
        m1.setString("langue", "Fr");
              esource=g.addEdge(m1, m2);
              esource.setInt("occur", nbroccurmm);
              etarget=g.addEdge(m2, m1);
              etarget.setInt("occur", nbroccurmm);
        }
        else if(m1!=null && m2==null){
        m2=g.addNode();
        m2.setString("texte", mot2);
        m2.setString("type","mot");
        m2.setString("langue", "Fr");
              esource=g.addEdge(m1, m2);
              esource.setInt("occur", nbroccurmm);
              etarget=g.addEdge(m2, m1);
              etarget.setInt("occur", nbroccurmm);
        }
        else if(m1==null && m2==null){
        m1=g.addNode(); 
        m1.setString("texte", mot1);
        m1.setString("type","mot");
        m1.setString("langue", "Fr");
        m2=g.addNode();
        m2.setString("texte", mot2);
        m2.setString("type","mot");
        m2.setString("langue", "Fr");
        esource=g.addEdge(m1, m2);
        esource.setInt("occur", nbroccurmm);
        etarget=g.addEdge(m2, m1);
        etarget.setInt("occur", nbroccurmm);
        }
        
        i++;
      }
    
} catch ( DataIOException e ) {
    e.printStackTrace();
    System.err.println("Error loading graph. Exiting...");
    System.exit(1);
}

return g;
}
    public EditGraphMl() {
    }
     
     /*methode qui permet de verfier si un noeud existe déja dans le graphe ou pas
      * prend en param le graph et le label de noeud
      */
     public Node FindNode(Graph g,String label){
     Node find=null;
      Table  iteratorNodes =  g.getNodeTable();
         Column essai=iteratorNodes.getColumn("texte");
         boolean found=false;
            // System.out.println(essai.getRowCount());
         int i=0;
             while(i<essai.getRowCount() && !found){
                 if(essai.getString(i).equals(label))
                 {found=true;
                  find=g.getNode(i);
                 }
                 i++;
             }
     return find;
     }
 public static void main(String[] argv) throws FileNotFoundException, DataIOException, IOException {
          
     // on récupère un EntityManager à  partir de l'EntityManagerFactory précédent
     EditGraphMl ex = new EditGraphMl();
       Graph g=ex.makeGraphMlpart2("data/output-file.xml");
        GraphMLWriter writer = new GraphMLWriter(); 
        OutputStream out = new FileOutputStream("data/output-file.xml");
        writer.writeGraph(g, out);
        
      
    }
    
}
