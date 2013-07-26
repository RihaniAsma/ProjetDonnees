/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creationFichier;

/**
 *
 * @author Administrateur
 */
public class VoteBean {

    private String lemma;
    private String vote;
    private String ParSce;
    public VoteBean() {
    }

    public String getLemma() {
        return lemma;
    }

    public String getVote() {
        return vote;
    }

    public String getParSce() {
        return ParSce;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public void setParSce(String ParSce) {
        this.ParSce = ParSce;
    }
    
    
}
