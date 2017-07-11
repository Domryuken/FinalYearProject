/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smallguymachine;

/**
 *
 * @author Ladon
 */
public class Registers
{
    private int programCounter;
    private int instruction;
    private int memoryAddress;
    private int number = 0;
    private int wholeInstruction = 0;
    
    public int getProgramCounter(){return programCounter;}
    public int getInstruction(){return instruction;}
    public int getMemoryAddress(){return memoryAddress;}
    public int getNumber(){return number;}
    public int getWholeInstruction(){return wholeInstruction;}
    
    public void setProgramCounter(int a){
        if(a > 0xff)
            programCounter = 0xff;
        else if(a < 0)
            programCounter = 0;
        else
            programCounter = a;
    }
    public void setInstruction(int a){instruction = a;}
    public void setMemoryAddress(int a){memoryAddress = a;}
    public void setNumber(int a){number = a;}
    public void setWholeInstruction(int a){wholeInstruction = a;}
    
    public void reset()
    {
        programCounter = 0;
        instruction = 0;
        memoryAddress = 0;
        number = 0;
        wholeInstruction = 0;
    }
}
