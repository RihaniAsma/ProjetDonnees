/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractiondesdonnees.entityCRUD;

import extractiondesdonnees.entity.Mots;
import extractiondesdonnees.entity.Sentences;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class MotsSentencesCRUD {
     private EntityManager em;

    public MotsSentencesCRUD(EntityManager em) {
        this.em = em;
    }

    //recup le nbr des sentences qui contiennent les 2 mots à la fois
    public long countSentences(int mot_id1,int mot_id2){
    long nbrsentences;
     Query resultat= em.createQuery("select count(*) from MotsSentences as p , MotsSentences as c where p.id.mot_id= :id1 and c.id.mot_id= :id2 and c.id.sentence_id=p.id.sentence_id");
    resultat.setParameter("id1", mot_id1);
    resultat.setParameter("id2", mot_id2);
    nbrsentences=(long) resultat.getSingleResult();
    return nbrsentences;
    }
    
    public List<Mots> motsDeSentence(int sentenceid){
        List<Mots> listmots=new ArrayList();
        Query resultat= em.createQuery("select p.mot from MotsSentences as p  where p.id.sentence_id= :id");
        resultat.setParameter("id", sentenceid);
        listmots=resultat.getResultList();
    return listmots;
    }
    
    public List<Sentences> sentencesDeMot(int mot_id){
     List<Sentences> listsentences=new ArrayList();
        Query resultat= em.createQuery("select p.sentence from MotsSentences as p  where p.id.mot_id= :id");
        resultat.setParameter("id", mot_id);
        listsentences=resultat.getResultList();
    return listsentences;
    }
}
