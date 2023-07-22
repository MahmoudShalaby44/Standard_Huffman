/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

/**
 *
 * @author Mohamed
 */
public class Symbol {
    private String symbol;
    private String code;
    private int count;
    public Symbol right;
    public Symbol left;
    
   public Symbol(String s)
    {
        symbol = s;
        code = "";
        count = 0;
        right = null;
        left = null;
    }    
        
    public void setSymbol(String s)
    {
       symbol = s;
    }
    
    
    public void setCount(int c)
    {
        count = c;
    }
    
    public String getSymbol()
    {
       return symbol;
    }
    
    
    public int getCount()
    {
       return count;
    }

    public void setCode(String c)
    {
        code = c;
    }

    public String getCode()
    {
       return code;
    }
}
