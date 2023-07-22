package assignment2;
import java.util.*;
import java.io.*;

public class Standard_Huffman {

    ArrayList<Symbol> Symbols = new ArrayList<>();    
    ArrayList<Symbol> Symbols2 = new ArrayList<>();
    
    String data;
    String compressed = "";
    
    public void CountCalculator()
    {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter your data");
        data = input.nextLine();

        String temp = "";

        for (int i = 0; i < data.length(); i++)
            {
                temp += data.charAt(i);
                if (i == 0)
                {
                    Symbol s = new Symbol(temp);
                    Symbols.add(s);
                    Symbols.get(i).setCount(1);
                    temp = "";
                }

                else
                {
                    int arraySize = Symbols.size();
                    int j;
                    for (j = 0; j < arraySize; j++)
                    {
                        if (Symbols.get(j).getSymbol().equals(temp) )
                        {
                            Symbols.get(j).setCount(Symbols.get(j).getCount()+1);
                            temp = "";
                            break;
                        }
                    }  

                    if (j == arraySize)
                    {
                        Symbol s = new Symbol(temp);
                        Symbols.add(s);
                        Symbols.get(Symbols.size()-1).setCount(1);
                        temp = "";  
                    }
                }

            }
        System.out.println("*********CharCount********");
        for (int k = 0; k < Symbols.size(); k++)
        {
            System.out.println(Symbols.get(k).getSymbol() + " -> " + Symbols.get(k).getCount());
        }
    }
    
    public void Sort()
    {
        for (int i = 0; i < Symbols2.size()- 1; i++)
        {
            for (int j = i + 1; j < Symbols2.size(); j++)
            {
                if (Symbols2.get(i).getCount() > Symbols2.get(j).getCount()) 
                {
                    Symbol s = new Symbol("");
                    
                    s.setSymbol(Symbols2.get(j).getSymbol());
                    s.setCount(Symbols2.get(j).getCount());
                    s.right = Symbols2.get(j).right;
                    s.left = Symbols2.get(j).left;
                    
                    
                    Symbols2.get(j).setSymbol(Symbols2.get(i).getSymbol());
                    Symbols2.get(j).setCount(Symbols2.get(i).getCount());
                    Symbols2.get(j).right = Symbols2.get(i).right;
                    Symbols2.get(j).left = Symbols2.get(i).left;
                    
                    Symbols2.get(i).setSymbol(s.getSymbol());
                    Symbols2.get(i).setCount(s.getCount());
                    Symbols2.get(i).right = s.right;
                    Symbols2.get(i).left = s.left; 
                }
            }
        }
    }
        
    
    public void CodeAssign(Symbol root, String code)
    {
        if (root.right == null && root.left == null)
        {
            if (Symbols.size() == 1)
            {
                SetCodes(root,code+"0");
            }
            else
            {
                SetCodes(root,code); 
            }
        }
        else
        {
            CodeAssign(root.left, code+"1");
            CodeAssign(root.right, code+"0");
        }
    }
    
    public void SetCodes(Symbol s, String code)
    {
        for (int i = 0; i < Symbols.size(); i++)
        {
            if (Symbols.get(i).getSymbol().equals(s.getSymbol()))
            {
                Symbols.get(i).setCode(code);
            }
        }
    }
    
    public void PrintCompressed() throws IOException
    {
        String temp = "";
        for (int i = 0; i < data.length(); i++)
        {
            for  (int j = 0; j < Symbols.size(); j++)
            {
                temp += data.charAt(i);
                if (temp.equals(Symbols.get(j).getSymbol()))
                {
                    compressed += Symbols.get(j).getCode();
                }
                temp = "";
            }
        }
        File file2 = new File("Compressed String.txt");
        
        if (!file2.exists())
        {
            file2.createNewFile();
        }

        PrintWriter pw = new PrintWriter(file2);
        pw.println(compressed);
        pw.close();
    }
   
   public void BitsCalculator()
   {
       System.out.println("*******Calculations*******");
       int originalSize = data.length() * 8;
       int compressedSize=0;
       int sum;
       float ratio;
       
       for (int i=0; i < Symbols.size(); i++)
       {
           sum = Symbols.get(i).getCount() * Symbols.get(i).getCode().length();
           compressedSize+= sum;
       }
       
       ratio = (float) originalSize / (float) compressedSize;
       System.out.println("Original Size: " + originalSize + " bits");
       System.out.println("Compressed Size: " + compressedSize + " bits");
       System.out.println("Compression Ratio: " + ratio);
   }
    
    public void Compression() throws IOException
    {
    CountCalculator();
    
    for (int i = 0; i < Symbols.size(); i++)
    {
        Symbol s = new Symbol("");
        
        s.setSymbol(Symbols.get(i).getSymbol());
        s.setCount(Symbols.get(i).getCount());
                
        Symbols2.add(s);
    }
     
    Sort();
    
    Symbol root = new Symbol("");
    
    if (Symbols2.size() == 1)
    {
        root = Symbols2.get(0);
    }
    else
    {
       while (Symbols2.size() > 1)
       {
           Symbol x = Symbols2.get(0);
           Symbols2.remove(0);

           Symbol y = Symbols2.get(0);
           Symbols2.remove(0);

           Symbol z = new Symbol("---");
           z.setCount(x.getCount() + y.getCount());
           z.left = x;
           z.right = y;
           root = z;

           Symbols2.add(root);
           Sort();
       }   
    }
    
    CodeAssign(root,"");
    
    String dictionary = "";
    for (int i =0; i < Symbols.size(); i++)
        {
            dictionary+= Symbols.get(i).getSymbol()+ " " + Symbols.get(i).getCode() + ",";
        }
    
    File file = new File("dictionary.txt");
    
    if (!file.exists())
    {
        file.createNewFile();
    }
    
    PrintWriter pw = new PrintWriter(file);
    pw.println(dictionary);
    pw.close();
    
    PrintCompressed();
    BitsCalculator();
    }
    
    public void Decompression() throws IOException
    {
        for (int i = 0; i < Symbols.size(); i++)
        {
            Symbols.get(i).setCode("");
        }
        
        String dictionary = "";
        String message = "";
        String temp = "";
        String symbol;
        String code;
        String current = "";
        BufferedReader br1 = new BufferedReader(new FileReader("dictionary.txt"));
        BufferedReader br2 = new BufferedReader(new FileReader("Compressed String.txt"));

        dictionary = br1.readLine();
        
        for (int i = 0; i < dictionary.length(); i++)
        {
            temp += dictionary.charAt(i);
            current += dictionary.charAt(i);
            if (current.equals(","))
            {
                      symbol = temp.substring(0, 1);
                      code = temp.substring(2,temp.length()-1);
                      for (int j = 0; j < Symbols.size(); j++)
                      {
                          if (Symbols.get(j).getSymbol().equals(symbol))
                          {
                              Symbols.get(j).setCode(code);
                              break;
                          }
                      }
                      temp = "";
            }
            current = "";
        }


        
        message = br2.readLine();
        
        
        String temp2 = "";
        String decompressed = "";
        
        for(int i = 0; i < message.length(); i++)
        {
            temp2 += message.charAt(i);
            for (int j = 0; j < Symbols.size(); j++)
            {
                if (temp2.equals(Symbols.get(j).getCode()))
                {
                    decompressed += Symbols.get(j).getSymbol();
                    temp2 = "";
                }
            }  
        }
           
        File file = new File("Decompression.txt");
    
        if (!file.exists())
        {
            file.createNewFile();
        }

        PrintWriter pw = new PrintWriter(file);
        pw.println(decompressed);
        pw.close();
    }
    
 }
