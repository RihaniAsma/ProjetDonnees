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
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.graphdb.index.ReadableIndex;
import prefuse.data.io.DataIOException;

/**
 *
 * @author acer
 */
public class GraphWithNeo4j {

    public GraphWithNeo4j() {
    }
    
    private enum RelTypes implements RelationshipType {
OCCUR
}
    public void creationNodeLemmaSesne() throws DataIOException, FileNotFoundException{
          EntityManagerFactory emf;
       EntityManager em;
       EntityTransaction tx;
       emf = Persistence.createEntityManagerFactory("ExtractionDesDonneesPU");
       em = null;
          em = emf.createEntityManager();
      tx=em.getTransaction();
      tx.begin();
         GraphDatabaseService graphDb = new GraphDatabaseFactory().
               newEmbeddedDatabaseBuilder("C:/Users/acer/Desktop/NEO4J-HOME/data/graph.db" ).
               setConfig(GraphDatabaseSettings.node_keys_indexable, "texte"). 
               setConfig( GraphDatabaseSettings.node_auto_indexing, "true" ).
               newGraphDatabase();
     Transaction tx4j = graphDb.beginTx();
     try {
LemmaCRUD lemmacrud=new LemmaCRUD(em);
     //recup tous les lemma
     List<Lemma> listLemma=lemmacrud.getAll();
     //pour chaque lemma get les senses et lier les noeuds senses aux lemma
   for(Lemma lemma:listLemma){
         String textelemma=lemma.getLemma();
         //cree un noud de types mots qui portes le lemma
         Node firstNode = graphDb.createNode();
          firstNode.setProperty("texte",textelemma);
           firstNode.setProperty("type", "mot");
           firstNode.setProperty("langue", "Fr");
          //recup tous les senses d'un lemma
          List<SenseLemma> listSense=lemma.getSenseId();
          for(SenseLemma sense:listSense){
              String textesense=textelemma+sense.getSenseid();
          //cree un noeud de type sense
              Node secondNode = graphDb.createNode();
             secondNode.setProperty("texte",textesense);
               secondNode.setProperty("type", "sense");
               secondNode.setProperty("langue", "Fr");
          }
   
   }
tx4j.success();
} finally {
tx4j.finish();
graphDb.shutdown();
}
     em.close();
     emf.close();
      }
    public void creationNodeMot() throws FileNotFoundException, DataIOException{
      GraphDatabaseService graphDb = new GraphDatabaseFactory().
               newEmbeddedDatabaseBuilder("C:/Users/acer/Desktop/NEO4J-HOME/data/graph.db" ).
               setConfig(GraphDatabaseSettings.node_keys_indexable, "texte"). 
               setConfig( GraphDatabaseSettings.node_auto_indexing, "true" ).
               newGraphDatabase();
     Transaction tx4j = graphDb.beginTx();
     try {
    Scanner scanner=new Scanner(new File("data/extract/mot/mot.txt"), "UTF-8");
    int i=1;
      while (scanner.hasNextLine()) {
    String mot = scanner.nextLine();
  Node firstNode = graphDb.createNode();
          firstNode.setProperty("texte",mot);
           firstNode.setProperty("type", "mot");
           firstNode.setProperty("langue", "Fr");
        System.out.println(i);
        i++;
      
      } 
    
tx4j.success();
} finally {
tx4j.finish();
graphDb.shutdown();
}

    }
    public void creationArcSenseLemma() throws FileNotFoundException, IOException, DataIOException{
        GraphDatabaseService g = new GraphDatabaseFactory().
               newEmbeddedDatabaseBuilder("C:/Users/acer/Desktop/NEO4J-HOME/data/graph.db" ).
               setConfig(GraphDatabaseSettings.node_keys_indexable, "texte"). 
               setConfig( GraphDatabaseSettings.node_auto_indexing, "true" ).
               newGraphDatabase();
     Transaction tx4j = g.beginTx();
     try {  
         // Edge etarget;
          CSVReader  reader = new CSVReader(new FileReader("data/extract/senselemma.csv"), '\t');
        String[] nextLine;
        int i=1;
        while (((nextLine = reader.readNext()) != null)){
        System.out.println(i);
        String sense=nextLine[0];
        String lemma=nextLine[1];
        Node firstNode=FindNode(g,lemma);
        Node secondNode=FindNode(g,sense);
         int nbroccurmm=Integer.parseInt(nextLine[2]);
        Relationship relationship = firstNode.createRelationshipTo( secondNode,RelTypes.OCCUR );
        relationship.setProperty("OCCUR",nbroccurmm );
              i++;
        
} 
       tx4j.success();
} finally {
tx4j.finish();
g.shutdown();
}
    }
     public void creationArcSenseMot() throws FileNotFoundException, IOException, DataIOException{
      GraphDatabaseService g = new GraphDatabaseFactory().
               newEmbeddedDatabaseBuilder("C:/Users/acer/Desktop/NEO4J-HOME/data/graph.db" ).
               setConfig(GraphDatabaseSettings.node_keys_indexable, "texte"). 
               setConfig( GraphDatabaseSettings.node_auto_indexing, "true" ).
               newGraphDatabase();
     Transaction tx4j = g.beginTx();
     try {
          CSVReader  reader = new CSVReader(new FileReader("data/extract/MotsSenses/extractMotsSensecarc.csv"), '\t');
        String[] nextLine;
        int i=1;
        while (((nextLine = reader.readNext()) != null)){
        System.out.println(i);
        String mot=nextLine[0];
        String sense=nextLine[1];
        Node secondNode=FindNode(g,mot);
        Node firstNode=FindNode(g,sense);
         int nbroccurmm=Integer.parseInt(nextLine[2]);
         Relationship relationship = firstNode.createRelationshipTo( secondNode,RelTypes.OCCUR );
         relationship.setProperty("OCCUR",nbroccurmm );
              i++;
        }
        tx4j.success();
} finally {
tx4j.finish();
g.shutdown();
}
    }
    public void creationArcMotMot() throws FileNotFoundException, IOException, DataIOException{
     GraphDatabaseService g = new GraphDatabaseFactory().
               newEmbeddedDatabaseBuilder("C:/Users/acer/Desktop/NEO4J-HOME/data/graph.db" ).
               setConfig(GraphDatabaseSettings.node_keys_indexable, "texte"). 
               setConfig( GraphDatabaseSettings.node_auto_indexing, "true" ).
               newGraphDatabase();
     Transaction tx4j = g.beginTx();
     try {
         CSVReader  reader = new CSVReader(new FileReader("data/extract/extractMotMot/carc/extract15.csv"), '\t');
        String[] nextLine;
        int i=1;
        while (((nextLine = reader.readNext()) != null)){
        System.out.println(i);
        String mot=nextLine[0];
        String mot2=nextLine[1];
        Node firstNode=FindNode(g,mot);
        Node secondNode=FindNode(g,mot2);
         int nbroccurmm=Integer.parseInt(nextLine[2]);
         Relationship relationship = firstNode.createRelationshipTo( secondNode,RelTypes.OCCUR );
        relationship.setProperty("OCCUR",nbroccurmm );
        
              i++;
        }
   tx4j.success();
} finally {
tx4j.finish();
g.shutdown();
}
    }
     public Node FindNode(GraphDatabaseService g,String label){
     Node find=null;
      ReadableIndex<Node> autoNodeIndex = g.index()
        .getNodeAutoIndexer()
        .getAutoIndex();
  find=autoNodeIndex.get("texte",label).getSingle();
//System.out.println(n1.getProperty("message"));
     return find;
     }
     public static void main(String[] args) throws DataIOException, FileNotFoundException, IOException{
     GraphWithNeo4j test=new GraphWithNeo4j();
    // test.creationNodeLemmaSesne();
    //test.creationNodeMot();
     //test.creationArcSenseLemma();
     //test.creationArcSenseMot();
     //test.creationArcMotMot();
     }
}
