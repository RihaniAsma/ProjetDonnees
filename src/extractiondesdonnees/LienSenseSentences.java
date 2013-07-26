/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractiondesdonnees;

import creationFichier.VoteBean;
import extractiondesdonnees.entity.Lemma;
import extractiondesdonnees.entity.SenseLemma;
import extractiondesdonnees.entity.Sentences;
import extractiondesdonnees.entityCRUD.LemmaCRUD;
import extractiondesdonnees.entityCRUD.SenseLemmaCRUD;
import extractiondesdonnees.entityCRUD.SentencesCRUD;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Administrateur
 */
public class LienSenseSentences {

    public LienSenseSentences() {
    }
    /*methode pour recuperer les votes des juges
     les nbr des lignes des sentences liée à chaque mot
     */
    public List<VoteBean> voteJudges(String lemma) throws UnsupportedEncodingException, FileNotFoundException, IOException{
     BufferedReader _buff = new BufferedReader(new InputStreamReader(new FileInputStream("data/reference/reference-fr-adj.txt"), "UTF-8"));
       List<VoteBean> listvoteBean=new ArrayList<VoteBean>();
           String line=_buff.readLine();
           while((line=_buff.readLine())!=null){
           String[] lineTerms = line.split("\t");
           if(lineTerms[2].equals(lemma))
           {  
              VoteBean bean= new VoteBean();
              bean.setLemma(lineTerms[2]);
              bean.setParSce(lineTerms[3]);
              bean.setVote(lineTerms[15]);
              listvoteBean.add(bean);
           }
           }
       
    return listvoteBean;
}
    //remplir le tab de jointure sense et sentences
        public static void main(String[] args) throws FileNotFoundException, IOException{
            
            LienSenseSentences methode=new LienSenseSentences();
            EntityManagerFactory emf;
       EntityManager em;
       EntityTransaction tx;
       emf = Persistence.createEntityManagerFactory("ExtractionDesDonneesPU");
       em = null;
     // on récupère un EntityManager à  partir de l'EntityManagerFactory précédent
      em = emf.createEntityManager();
      
        SenseLemmaCRUD senselemmacrud=new SenseLemmaCRUD(em);
        SentencesCRUD sentencescrud=new SentencesCRUD(em);
        //recup all lemma
        LemmaCRUD lemmacrud=new LemmaCRUD(em);
        List<Lemma> listlemma=lemmacrud.getAll();
        //boucler pour tous les lemma
        for (Iterator<Lemma> it = listlemma.iterator(); it.hasNext();) {
            Lemma lemma = it.next();
            List<VoteBean> listvoteBean=methode.voteJudges(lemma.getLemma());
            //System.out.println(listvoteBean.size());
            List<SenseLemma> listsense=lemma.getSenseId();
            //boucler pour tous les senses d'un lemma
            for(SenseLemma sense:listsense){
                      // début transaction
             tx = em.getTransaction();
             tx.begin();
            int senseid=sense.getId();
            SenseLemma sensebase=senselemmacrud.getOne(senseid, lemma.getId());
            List<Sentences> stces=new ArrayList<Sentences>();
            for(VoteBean vb:listvoteBean)
            {
                if(vb.getVote().equalsIgnoreCase(sensebase.getSenseid()))
                {   Sentences stc=sentencescrud.getOne(Integer.parseInt(vb.getParSce()));
                   stces.add(stc);
                }
            }
            if(!stces.isEmpty())
            {
             sensebase.setSentences(stces);
             senselemmacrud.updateOne(sensebase);
             System.out.println("nombre de sentences "+stces.size()+" avec le sense"+sensebase.getSenseid()+" de lemma " +sensebase.getLemma().getLemma());
            }
            tx.commit();
            }
        }
        
    // fin EntityManager
    em.close();
    // fin EntityManagerFactory
     emf.close();
        }
}
