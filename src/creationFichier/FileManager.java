package creationFichier;

import java.io.*;
import java.util.Vector;

public class FileManager {
  public FileManager() {
  }

  public static void creation_fichier(String path, String content,
                                      String encoding) {
    if (encoding.length() == 0) {
      encoding = "ISO-8859-1";
    }
    File f = new File(path);
    if (!f.exists()) {

      try {
        f.createNewFile();
      }
      catch (IOException ex) {
      }

    }
    try {

      FileOutputStream FS = new FileOutputStream(f);

      FS.write(content.getBytes(encoding));
      FS.close();
    }
    catch (java.io.IOException e) {
    }

    ////////////////////////////////////////////////////////////////
  }

  public static void creation_fichier(String path, Vector content,
                                      String encoding) {
    if (encoding.length() == 0) {
      encoding = "ISO-8859-1";
    }
    File f = new File(path);
    if (!f.exists()) {

      try {
        f.createNewFile();
      }
      catch (IOException ex) {
      }

    }
    try {

      FileOutputStream FS = new FileOutputStream(f);
      for (int i = 0; i < content.size(); i++) {
        FS.write( ( (String) content.elementAt(i)+"\n").getBytes(encoding));
      }
      FS.close();
    }
    catch (java.io.IOException e) {
    }

    ////////////////////////////////////////////////////////////////
  }

  public static void creation_fichier_iso_utf(String path, String content) {

    try {
      byte bytes[] = content.getBytes("ISO-8859-1");
      String s;
      byte b;
      s = new String(bytes);

      FileManager.creation_fichier(path, s, "utf-8");

    }
    catch (IOException ex) {
    }

    ////////////////////////////////////////////////////////////////
  }

  public static void creation_fichier_utf_iso(String path, String content) {
    try {
      DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new
          FileOutputStream(path)));
      try {
        byte bytes[] = content.getBytes("utf-8");
        String s;
        byte b;
        s = new String(bytes);

        FileManager.creation_fichier(path, s, "ISO-8859-1");

      }
      catch (IOException ex) {
      }
    }
    catch (FileNotFoundException ex1) {
    }
    ////////////////////////////////////////////////////////////////
  }

  public static String chargement_fichier(String path) {
    String chaine_chargement = "";
    File f1 = new File(path);

    try {
      RandomAccessFile raf = new RandomAccessFile(f1, "r");
      String ligne;
      while ( (ligne = raf.readLine()) != null) {
        chaine_chargement += (ligne + "\n");
      }
      raf.close();
    }
    catch (IOException e1) {

    }
    return chaine_chargement;

  }

  public static Vector fileToVector(String path,String encoding) {
    Vector ret=new Vector();
    String chaine_chargement = "";
     File f1 = new File(path);

     try {
       RandomAccessFile raf = new RandomAccessFile(f1, "r");
       byte bytes[] = new byte[ (int) raf.length()];
       raf.read(bytes);
       chaine_chargement = new String(bytes, encoding).replaceAll("\r","");
       String tab[]=chaine_chargement.split("\\n");
       for(int i=0;i<tab.length;i++)
         ret.add(tab[i]);
       raf.close();

     }
     catch (IOException e1) {
       System.out.println(e1.getMessage());
     }
     return ret;

   }


  public static String chargement_fichier_utf(String path) {
    String chaine_chargement = "";
    File f1 = new File(path);

    try {
      RandomAccessFile raf = new RandomAccessFile(f1, "r");
      byte bytes[] = new byte[ (int) raf.length()];
      raf.read(bytes);
      chaine_chargement = new String(bytes, "utf-8");
      raf.close();

    }
    catch (IOException e1) {
      System.out.println(e1.getMessage());
    }
    return chaine_chargement;

  }

  public static String chargement_fichier_iso(String path) {
    String chaine_chargement = "";
    File f1 = new File(path);

    try {
      RandomAccessFile raf = new RandomAccessFile(f1, "r");
      byte bytes[] = new byte[ (int) raf.length()];
      raf.read(bytes);
      chaine_chargement = new String(bytes, "CP1256");
      raf.close();

    }
    catch (IOException e1) {
      System.out.println(e1.getMessage());
    }
    return chaine_chargement;

  }

  public static void deleteFile(String path) {
    File f = new File(path);
    if (f.exists()) {
      f.delete();

    }
  }
  public static String XMLStringToTree(String res) {
     String tmp[] = res.split("<");
     int nbSpaces = 0;
     for (int i = 1; i < tmp.length; i++) {
       String t = "<" + tmp[i];

       if (t.indexOf("/") > 0) {
         nbSpaces--;
         for (int j = 0; j < nbSpaces; j++) {
           t = "   " + t;
         }

       }
       else {
         for (int j = 0; j < nbSpaces; j++) {
           t = "   " + t;
         }

         nbSpaces++;
       }

       t = t + "\n";
       tmp[i] = t;
     }
     res = tmp[0];
     for (int i = 1; i < tmp.length; i++) {
       res += tmp[i];
     }
     res = res.replaceAll("\n\n", "\n");
     res = res.replaceAll("\n\n", "\n");
     res = res.replaceAll("\n\n", "\n");

     return res;
   }

}
