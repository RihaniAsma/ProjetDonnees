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
public class NewClass1 {
    public static void main(String[] args) throws FileNotFoundException, IOException {
       
        CSVReader scanner=new CSVReader(new FileReader("data/extract/extractMotMot/carc/extractMotMotall.csv"),'\t');
    String[] line;
    int i=1;
    CSVWriter writer1 = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/carc/extract1.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
    CSVWriter writer2 = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/carc/extract2.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
    CSVWriter writer3 = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/carc/extract3.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
    CSVWriter writer4 = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/carc/extract4.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
    CSVWriter writer5 = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/carc/extract5.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
    CSVWriter writer6 = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/carc/extract6.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
    CSVWriter writer7 = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/carc/extract7.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
    CSVWriter writer8 = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/carc/extract8.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
    CSVWriter writer9 = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/carc/extract9.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
    CSVWriter writer10 = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/carc/extract10.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
    CSVWriter writer11 = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/carc/extract11.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
    CSVWriter writer12 = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/carc/extract12.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
    CSVWriter writer13 = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/carc/extract13.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
    CSVWriter writer14 = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/carc/extract14.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
    CSVWriter writer15 = new CSVWriter(new OutputStreamWriter(new FileOutputStream("data/extract/extractMotMot/carc/extract15.csv"), "UTF-8"), '\t',CSVWriter.NO_QUOTE_CHARACTER);
             
    while((line=scanner.readNext())!=null && i<2200000){
        if(i<150000){
             writer1.writeNext(line);
        }
        else{
        if(i>=150000 && i<300000){
        writer2.writeNext(line);
        }
        else{
        if(i>=300000 && i<450000)
            {
        writer3.writeNext(line);
        }
        else{
         if(i>=450000 && i<600000)
            {
          writer4.writeNext(line);
        }
         
         else{
         if(i>=600000 && i<750000)
            {
         writer5.writeNext(line);
        }
         else{
             if(i>=750000 && i<900000)
            {
         writer6.writeNext(line);
        }
             else{
             if(i>=900000 && i<1050000)
            {
          writer7.writeNext(line);
        }
             else{
            if(i>=1050000 && i<1200000)
            {
        writer8.writeNext(line);
        }
            else{
            if(i>=1200000 && i<1350000)
            {
         writer9.writeNext(line);
        }
            else{ 
            if(i>=1350000 && i<1500000)
            {
         writer10.writeNext(line);
        }
            else{
               if(i>=1500000 && i<1650000)
            {
         writer11.writeNext(line);
        }
               
            else{
               if(i>=1650000 && i<1800000)
            {
         writer12.writeNext(line);
        }
               else{
               if(i>=1800000 && i<1950000)
            {
         writer13.writeNext(line);
        }
               else{
               if(i>=1950000 && i<2100000)
            {
         writer14.writeNext(line);
        }else{
               if(i>=2100000 && i<2200000)
            {
         writer15.writeNext(line);
        }
            }
               }
            }
            }
            }
             }
             }
             }
             }
         }
         }
        }
        }
        }
        i++;
    }
    writer1.close();
    writer2.close();
    writer3.close();
    writer4.close();
    writer5.close();
    writer6.close();
    writer7.close();
    writer8.close();
    writer9.close();
    writer10.close();
    writer11.close();
    writer12.close();
    writer13.close();
    writer14.close();
    writer15.close();
    
    
}
}