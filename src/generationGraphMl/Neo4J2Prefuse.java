/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package generationGraphMl;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import prefuse.data.Graph;
import prefuse.data.Table;
import prefuse.data.Tuple;
import prefuse.data.io.DataIOException;
import prefuse.data.io.GraphMLWriter;

/**
 *
 * @author acer
 */

public class Neo4J2Prefuse {

    public Neo4J2Prefuse() {
    }

    public Graph constructGraph() {
        /* Prepare tables which will store information about nodes and edges in
         * Prefuse data structures for the provenance graph
         */
        Table nodes = new Table();
        nodes.addColumn("NODE_ID", int.class);
        nodes.addColumn("TYPE", String.class);
        nodes.addColumn("Texte", String.class);
        nodes.addColumn("Langue", String.class);

        Table edges = new Table();
       
        edges.addColumn("SOURCE_ID", int.class);
        edges.addColumn("TARGET_ID", int.class);
        edges.addColumn("OCCUR", int.class);
       // edges.addColumn("TYPE", String.class);

        /* Connect to the Neo4j database and initiate transaction */
        GraphDatabaseService graphDb = new GraphDatabaseFactory().
               newEmbeddedDatabaseBuilder("C:/Users/acer/Desktop/NEO4J-HOME/data/graph.db" ).
               setConfig(GraphDatabaseSettings.node_keys_indexable, "texte"). 
               setConfig( GraphDatabaseSettings.node_auto_indexing, "true" ).
               newGraphDatabase();
        Transaction tx = graphDb.beginTx();

        /* Traverse all nodes in the Neo4j database and add them and their
         * relationships to the data tables
         */
        try {
            Iterator it = graphDb.getAllNodes().iterator();
            while (it.hasNext()) {
                Node node = (Node) it.next();
                addNode(node, nodes);
                Iterator rel_it =
                        node.getRelationships(Direction.OUTGOING).iterator();

                while (rel_it.hasNext()) {
                    Relationship r = (Relationship) rel_it.next();
                    addEdge(r, edges);
                }
            }
            tx.success();
        } finally {
            tx.finish();
        }
        Graph g = new Graph(nodes, edges, true, "NODE_ID", "SOURCE_ID",
                "TARGET_ID");
        return g;
    }

    private void addNode(Node n, Table nodes) {
        if(n.getId() == 0l) return;
        nodes.addRow();
        int rowcount = nodes.getRowCount();

        Tuple t = nodes.getTuple(rowcount - 1);
        t.set("NODE_ID", (int) n.getId());
        t.set("TYPE", n.getProperty("type"));
        t.set("Texte", n.getProperty("texte"));
        t.set("Langue", n.getProperty("langue"));
        
        //TODO other attributes
    }

    private void addEdge(Relationship r, Table edges) {
        edges.addRow();
        int rowcount = edges.getRowCount();

        Tuple t = edges.getTuple(rowcount - 1);
        t.set("SOURCE_ID", (int) r.getStartNode().getId());
        t.set("TARGET_ID", (int) r.getEndNode().getId());
        t.set("OCCUR", r.getProperty("OCCUR"));
        //t.set("TYPE", r.getType().name());

    }
    public static void main(String[] argv) throws FileNotFoundException, DataIOException, IOException {
          
     // on récupère un EntityManager à  partir de l'EntityManagerFactory précédent
     Neo4J2Prefuse ex = new Neo4J2Prefuse();
       Graph g=ex.constructGraph();
        GraphMLWriter writer = new GraphMLWriter(); 
        OutputStream out = new FileOutputStream("data/output-file.xml");
        writer.writeGraph(g, out);
        
      
    }
    
}


