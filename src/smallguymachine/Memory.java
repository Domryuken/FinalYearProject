/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smallguymachine;

/**
 *
 * @author ladon
 */
public class Memory {
    
//    private int [] address = new int [0x100];
    private Integer[] address = new Integer [0x100];
    
    Memory(){
        for(int i=0;i<0x100;i++)
            address[i]=0;
    }
    
    public Integer[] getMemory()
    {
        return address;
    }
    
    public int getAddress(int givenAddress)
    {
        return address[givenAddress];
    }
    
    public void setAddress(int givenAddress, int givenNumber)
    {
        address[givenAddress]=givenNumber;
    }
    
//    public void setAddress(int column, int row, int givenNumber)
//    {
//        address[column][row]=givenNumber;
//    }
    
    public void resetMemory()
    {
        for(int i=0;i<0x100;i++)
        {
            address[i]=0;
        }
    }
    

    
    
    
}
