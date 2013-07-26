/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package generationGraphMl;

import au.com.bytecode.opencsv.CSVReader;
import extractiondesdonnees.entity.Lemma;
import extractiondesdonnees.entity.SenseLemma;
import extractiondesdonnees.entityCRUD.LemmaCRUD;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;
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
 * @author acer
 */
public class GraphMl {


    public void creationNodeLemmaSesne() throws DataIOException, FileNotFoundException{
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
     EntityManagerFactory emf;
       EntityManager em;
       EntityTransaction tx;
       emf = Persistence.createEntityManagerFactory("ExtractionDesDonneesPU");
       em = null;
          em = emf.createEntityManager();
      tx=em.getTransaction();
      tx.begin();

       Node n1;
        Node n2;
        
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
          }
   
   }
    tx.commit();
        em.close();
        emf.close();
        
    GraphMLWriter writer = new GraphMLWriter(); 
        OutputStream out = new FileOutputStream("data/graph/outputaveclemmasense.xml");
        writer.writeGraph(g, out);
    }
    
    public void creationNodeMot(String path) throws FileNotFoundException, DataIOException{
        Graph g = null;
try {
    g = new GraphMLReader().readGraph(path);
    Scanner scanner=new Scanner(new File("data/extract/mot/mot.txt"), "UTF-8");
    int i=1;
      while (scanner.hasNextLine()) {
    String mot = scanner.nextLine();
     Node n1=g.addNode();
      n1.setString("texte", mot);
        n1.setString("type","mot");
        n1.setString("langue", "Fr");
        System.out.println(i);
        i++;
      
      }
    
    } catch ( DataIOException e ) {
    e.printStackTrace();
    System.err.println("Error loading graph. Exiting...");
    System.exit(1);
}
 GraphMLWriter writer = new GraphMLWriter(); 
        OutputStream out = new FileOutputStream("data/graph/outputavecmot.xml");
        writer.writeGraph(g, out);
    }
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
    public void creationArcSenseLemma(String path) throws FileNotFoundException, IOException, DataIOException{
         Graph g = null;      
try {
    g = new GraphMLReader().readGraph(path);
    
     Edge esource;
         // Edge etarget;
          CSVReader  reader = new CSVReader(new FileReader("data/extract/senselemma.csv"), '\t');
        String[] nextLine;
        int i=1;
        while (((nextLine = reader.readNext()) != null)){
        System.out.println(i);
        String sense=nextLine[0];
        String lemma=nextLine[1];
        Node m1=FindNode(g,sense);
        Node m2=FindNode(g,lemma);
         int nbroccurmm=Integer.parseInt(nextLine[2]);
        esource=g.addEdge(m1, m2);
              esource.setInt("occur", nbroccurmm);
            //  etarget=g.addEdge(m2, m1);
             // etarget.setInt("occur", nbroccurmm);
              i++;
        }
        } catch ( DataIOException e ) {
    e.printStackTrace();
    System.err.println("Error loading graph. Exiting...");
    System.exit(1);
}  
        GraphMLWriter writer = new GraphMLWriter(); 
        OutputStream out = new FileOutputStream("data/graph/outputavecarcsenselemma.xml");
        writer.writeGraph(g, out);
    }
    public void creationArcSenseMot(String path) throws FileNotFoundException, IOException, DataIOException{
         Graph g = null;      
try {
    g = new GraphMLReader().readGraph(path);
    
     Edge esource;
        //  Edge etarget;
          CSVReader  reader = new CSVReader(new FileReader("data/extract/MotsSenses/extractMotsSensecarc.csv"), '\t');
        String[] nextLine;
        int i=1;
        while (((nextLine = reader.readNext()) != null)){
        System.out.println(i);
        String mot=nextLine[0];
        String sense=nextLine[1];
        Node m1=FindNode(g,mot);
        Node m2=FindNode(g,sense);
         int nbroccurmm=Integer.parseInt(nextLine[2]);
        esource=g.addEdge(m1, m2);
              esource.setInt("occur", nbroccurmm);
            //  etarget=g.addEdge(m2, m1);
             // etarget.setInt("occur", nbroccurmm);
              i++;
        }
        } catch ( DataIOException e ) {
    e.printStackTrace();
    System.err.println("Error loading graph. Exiting...");
    System.exit(1);
}  
        GraphMLWriter writer = new GraphMLWriter(); 
        OutputStream out = new FileOutputStream("data/graph/outputavecarcsenseMot.xml");
        writer.writeGraph(g, out);
    }
    public void creationArcMotMot(String path) throws FileNotFoundException, IOException, DataIOException{
         Graph g = null;      
try {
    g = new GraphMLReader().readGraph(path);
    
     Edge esource;
         // Edge etarget;
         CSVReader  reader = new CSVReader(new FileReader("data/extract/extractMotMot/carc/extract11.csv"), '\t');
        String[] nextLine;
        int i=1;
        while (((nextLine = reader.readNext()) != null)){
        System.out.println(i);
        String mot=nextLine[0];
        String sense=nextLine[1];
        Node m1=FindNode(g,mot);
        Node m2=FindNode(g,sense);
         int nbroccurmm=Integer.parseInt(nextLine[2]);
        esource=g.addEdge(m1, m2);
              esource.setInt("occur", nbroccurmm);
             // etarget=g.addEdge(m2, m1);
              //etarget.setInt("occur", nbroccurmm);
              i++;
        }
        } catch ( DataIOException e ) {
    e.printStackTrace();
    System.err.println("Error loading graph. Exiting...");
    System.exit(1);
}  
        GraphMLWriter writer = new GraphMLWriter(); 
        OutputStream out = new FileOutputStream("data/graph/outputavecarcMotMot1.xml");
        writer.writeGraph(g, out);
    }
    public GraphMl() {
       
    }
    
    
    public static void main(String[] argv) throws FileNotFoundException, DataIOException, IOException {
    GraphMl graph=new GraphMl();
    //graph.creationNodeLemmaSesne();
  // graph.creationNodeMot("data/graph/outputaveclemmasense.xml");
   // graph.creationArcSenseLemma("data/graph/outputavecmot.xml");
    // graph.creationArcSenseMot("data/graph/outputavecarcsenselemma.xml");
     graph.creationArcMotMot("data/graph/outputavecarcMotMot1.xml");
    } 
}
