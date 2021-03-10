/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author TOSHIBA
 */

    import java.awt.FlowLayout;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;



import java.util.*;
import java.io.FileWriter;
import java.io.BufferedReader; //to can read data from file 
import java.io.BufferedWriter; //to can read data from file 
//import java.io.FileInputStream;
//import java.io.IOException;
import java.io.FileReader;
import java.io.File;
//import java.nio.file.Path;
//import java.nio.file.Files;




//import java.nio.charset.StandardCharsets;

public class MultimediaLZ78FILE 
{
    public static class TAGLZ78FILE
    {
         int Pointer;
         char NextChar;
                public void setPointer(int p)
                {Pointer = p ;}
                public void setNextChar(char n)
                {NextChar = n ;}
                public int getPointer()
                {return Pointer;}
                public char getNextChar()
                {return NextChar;}
                public TAGLZ78FILE (int P , char N)
                {Pointer = P;
                 NextChar = N;
                }
                public TAGLZ78FILE ()
                {
                }
        
    }
     public static void compression()
        {
               String Text;
              
               int index=0;    //index in dictionary
               int J=0;        //use in search
               String TestWord="";
               ArrayList<String>Dictionary= new ArrayList<String>();
               ArrayList<TAGLZ78FILE>Tags= new ArrayList<TAGLZ78FILE>();
               Dictionary.add(null);
               

               Scanner input = new Scanner(System.in);
               System.out.println("Please Enter Text :");
               Text= input.next();

               try    //Put Data In File "Data.Txt"
               {
                   FileWriter FOut =new FileWriter("Data.txt");
                   FOut.write(Text);
                   FOut.close();
               }
               catch (Exception ex)
               {
                   System.out.println("Error Message");
               }

               int j=0;
               for(int i=0; i<Text.length() ;i++)
               {
                   //System.out.println(Text.charAt(i));
                       TestWord+=Text.charAt(i);
                       for (  j=0 ; j<Dictionary.size(); j++)
                       {

                           if(TestWord.equals(Dictionary.get(j)))
                           {
                               
                               //System.out.println("testword"+TestWord);
                               index =j;
                               //System.out.println("Index   ---  "+index);
                               J=0;
                               break;


                           }
                           else 
                           {
                               //System.out.println("Index else   ---  "+index);
                               J=1;
                           }
                       }
                       //System.out.println("J:"+J+"Dic size: "+(Dictionary.size()-1));
                       if(i+1==(Text.length()))//to check last char
                       {
                           if((TestWord.length())-1==j)
                               {
                                     TAGLZ78FILE T=new TAGLZ78FILE();
                                     T.setPointer(0);
                                     T.setNextChar(TestWord.charAt(TestWord.length()-1));
                                     Tags.add(T);
                               }
                            else
                                {
                                  TAGLZ78FILE T=new TAGLZ78FILE();
                                  T.setPointer(index);
                                  T.setNextChar(' ');
                                  Tags.add(T);
                                  TestWord="";

                                }
                               
                        }
                       else if(J==1)
                       {
                            //System.out.println("Dic size: "+(Dictionary.size()-1));
                             TAGLZ78FILE T=new TAGLZ78FILE();
                             Dictionary.add(TestWord);
                             //System.out.println("Index else BAL  ---  "+index);
                             T.setPointer(index);
                             T.setNextChar(TestWord.charAt(TestWord.length()-1));
                             Tags.add(T);
                             TestWord="";

                       }

               }
               System.out.println("***************Tages****************");
               for(int i=0; i<Tags.size() ;i++)
               {

                   System.out.print("<"+Tags.get(i).Pointer+",");
                   System.out.println(Tags.get(i).NextChar+">");
               }


               try    //Put Tags In File "Tags.Txt"
               {
                   FileWriter FOut =new FileWriter("Tags.txt");
                   BufferedWriter bufferedWriter =new BufferedWriter(FOut);
                   for(int i=0; i<Tags.size() ;i++)
                   {
                       String PointerS=""+(Tags.get(i).Pointer);
                       bufferedWriter.write(PointerS);
                       bufferedWriter.write("_");
                       bufferedWriter.write(Tags.get(i).NextChar);
                       bufferedWriter.write("\n");
                   }
                   bufferedWriter.close();
               }
               catch (Exception ex)
               {
                   System.out.println("Error Message");
               }

               System.out.println("*************Dictionary compression***********");
               for(int p=0 ; p<Dictionary.size(); p++)
               {

                   System.out.println(p+"----->"+Dictionary.get(p));
               }
        }
    /**********************************Decompression********************************************/
      public static void Decompression()
      {
          
             ArrayList<String>Dictionary2= new ArrayList<String>();
             ArrayList<String>Compressed= new ArrayList<String>();
             ArrayList<TAGLZ78FILE>Tags= new ArrayList<TAGLZ78FILE>();
             Tags= new ArrayList<TAGLZ78FILE>();
             Dictionary2.add(null);

              try    //Put Tags In File "Tags.Txt"
              {
                  //int counter=0;
                  String line=null;
                  int pointer=0;
                  char nextChar=' ';

                  String po="";          //to can split string and get pointer and next char
                  File fileName =new File("Tags.txt");
                  FileReader fileReader =  new FileReader(fileName);

                  BufferedReader bufferedReader =  new BufferedReader(fileReader);

                  while((line = bufferedReader.readLine()) != null) 
                  {
                      for(int t=0; t<line.length();t++)
                      {

                          if(line.charAt(t)=='_')
                          {
                              pointer= Integer.parseInt(po);
                              po="";

                          }
                          else
                          {
                               po+=line.charAt(t);
                          }


                      }
                         nextChar=po.charAt(0);
                         po="";

                         TAGLZ78FILE T=new TAGLZ78FILE(pointer , nextChar);
                         Tags.add(T);

                      //System.out.println("pointer: "+pointer +" NextChar: "+nextChar);
                      //System.out.println(line);

                  }

              }
              catch (Exception ex)
              {
                  System.out.println("*-*Error Message");
              }

          for(int j=0; j<Tags.size();j++)
          {
              String s;    //Variable to test if word i dictionary or not
              if(Tags.get(j).Pointer==0)
              { 
                  if(j==Tags.size()-1)
                  {
                      s=""+Tags.get(j).NextChar;
                      Compressed.add(s);
                  }
                  else
                  {
                      s=""+Tags.get(j).NextChar;
                      Compressed.add(s);
                      Dictionary2.add(s);
                  }
              }
              else
              {
                  s=""+Dictionary2.get(Tags.get(j).Pointer)+Tags.get(j).NextChar;
                  Compressed.add(s);
                  Dictionary2.add(s);

              }

          }
              System.out.println("*****************Dictionary Decompression********************");
              for(int j=0 ; j<Dictionary2.size(); j++)
              {

                  System.out.println(j+"----->"+Dictionary2.get(j));
              }
                System.out.println("********************Data After Decompression****************");
              for(int i=0; i<Compressed.size() ;i++)
              {

                  System.out.print(Compressed.get(i));

              }    
              System.out.print("\n");
      }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
      
             LZ78 k=new LZ78();
                  
             k.setVisible(true) ;
            //compression();
            //Decompression();
        
 
    
        
    }
}
    

