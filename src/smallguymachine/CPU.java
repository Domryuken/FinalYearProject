/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smallguymachine;

import java.util.Scanner;

/**
 *
 * @author Ladon
 */
public class CPU
{
    private Memory memory;
    private Registers registers = new Registers();
    private boolean halt = false;
    
    CPU(Memory givenMemory)
    {
        memory = givenMemory;
    }
    
    public void reset()
    {
        registers.reset();
    }
    
    public Registers getRegister()
    {
        return registers;
    }
    
    public boolean getHalt()
    {
        return halt;
    }
    
    public void fetchInstruction()
    {
//        System.out.println("PC = " + registers.getProgramCounter());
        registers.setWholeInstruction(memory.getAddress(registers.getProgramCounter()));
//        int instruction = memory.getAddress(registers.getProgramCounter());
        registers.setProgramCounter(ALU.add(registers.getProgramCounter()));
//        return instruction;
    }
    
    public void decodeInstruction(int instruction)
    {
        registers.setInstruction(registers.getWholeInstruction()/0x100);
        registers.setMemoryAddress(registers.getWholeInstruction()%0x100);
//        registers.setInstruction(instruction/0x100);
//        registers.setMemoryAddress(instruction%0x100);
//        return instruction/0x100;
    }
    
    public int executeInstruction(int input)
    {
        int instruction = registers.getInstruction();
        int address = registers.getMemoryAddress();
        int returnValue = 0;
        switch(instruction)
        {
            case 0x0://stop
                System.out.println("STOP");
                halt = true;
                break;
                
            case 0x1://input 
                System.out.println("RECIEVE");
//                int givenNumber = scan.nextInt();
//                int givenNumber = GUI.;
                registers.setNumber(input);
                break;
                
            case 0x2://output
                System.out.println("SEND");
                returnValue = registers.getNumber();
                break;
                
            case 0x3://save
                System.out.println("SAVE");
                memory.setAddress(address, registers.getNumber());
                break;
                
            case 0x4://load
                System.out.println("LOAD");
                registers.setNumber(memory.getAddress(address));
                break;
                
            case 0x5://add
                System.out.println("ADD");
                registers.setNumber(registers.getNumber()+memory.getAddress(address));
                break;
                
            case 0x6://subtract
                System.out.println("SUBTRACT");
                registers.setNumber(registers.getNumber()-memory.getAddress(address));
                break;
                
            case 0x7://branch always
                System.out.println("BRANCH ALWAYS");
                registers.setProgramCounter(address);
                break;
                
            case 0x8://branch if zero
                System.out.println("BRANCH ZERO");
                if(registers.getNumber()==0)
                    registers.setProgramCounter(address);
                break;
                
            case 0x9://branch if positive
                System.out.println("BRANCH POSITIVE");
                if(registers.getNumber()>=0)
                    registers.setProgramCounter(address);
                break;
                
                
                
                
            //cheating instructions
            case 0xa://multiply
                System.out.println("MULTIPLY");
                registers.setNumber(registers.getNumber()*memory.getAddress(address));
                break;
                
            case 0xb://divide
                System.out.println("DIVIDE");
                registers.setNumber(registers.getNumber()/memory.getAddress(address));
                break;
                
            case 0xc://remainder
                System.out.println("REMAINDER");
                registers.setNumber(registers.getNumber()%memory.getAddress(address));
                break;

                
        }
        return returnValue;

    }
    
    public void setHalt()
    {
        halt = true;
    }
    public void setStart()
    {
        halt = false;
    }
}


//435 + 300 + 50 + 300
