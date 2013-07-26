/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creationFichier;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import java.io.*;

/**
 *
 * @author Administrateur
 */
public class NewClass {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
            CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/nbr/extractall1.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
    
        int i=1;
        while(i<18220){
    CSVReader scanner1=new CSVReader(new FileReader("data/extract/extractMotMot/nbr/extract"+i+".csv"),'\t');
    String[] line1;
    while((line1=scanner1.readNext())!=null){
        
        writer.writeNext(line1);
    }
    i++;
        }
        writer.close();
    }
    
}
