
package smallguymachine;

import java.util.HashMap;
import java.util.Map;

public class LowCompiler
{
    private String[] type;
//    private String[] names;
    Map<String, Integer> names = new HashMap<String, Integer>();
    private String[] location;
    private int[] instructions;
    
    LowCompiler(){}
    
    LowCompiler(String givenString)throws LowCodeException
    {
        try{
            String[] lines = givenString.split("[\\r\\n]+");
            type = new String[lines.length];
//            names = new String[lines.length];
            location = new String[lines.length];
            instructions = new int[lines.length];
            
            /*
            for every line split into separate words and check how many words there
            are. if there are only two then save the first word as the instruction type.
            if 3 then save the first word as the name and save the second word as the type.
            */
            for(int i=0;i<lines.length;i++)
            {
                String[] words = lines[i].split(" +");
                if(words.length>3||words.length<2)
                    throw new LowCodeException();
                if(words.length==3)
                {
                    if(words[0].charAt(0)=='#')
                        names.put(words[0], i);
                }
            }
            
            for(int i=0;i<lines.length;i++)
            {
                String[] words = lines[i].split(" +");
                if(words.length==2)
                {
                    type[i]=words[0];
                }else if(words.length==3)
                {
                    type[i]=words[1];
                }
            }
            
            for(int i=0;i<lines.length;i++)
            {
                String[] words = lines[i].split(" +");
                if(words.length==2)
                {
                    if(words[1].charAt(0)=='#')
                        location[i]=String.valueOf(names.get(words[1]));
                    else
                        location[i]=words[1];
                }
                else
                {
                    if(words[2].charAt(0)=='#')
                        location[i]=String.valueOf(names.get(words[2]));
                    else
                        location[i]=words[2];
                }
                
            }
        }catch(Exception e){
            throw new LowCodeException();
        }
    }
    
    public void compile() throws Exception
    {
        for(int i=0;i<instructions.length;i++)
        {
            try{
                switch(type[i])
                {
                    case "STP":
                        instructions[i] = 0x000;
                        break;
                    case "RCV":
                        instructions[i] = 0x100;
                        break;
                    case "SND":
                        instructions[i] = 0x200;
                        break;
                    case "SAV":
                        instructions[i] = 0x300;
                        break;
                    case "LOD":
                        instructions[i] = 0x400;
                        break;
                    case "ADD":
                        instructions[i] = 0x500;
                        break;
                    case "SUB":
                        instructions[i] = 0x600;
                        break;
                    case "BRA":
                        instructions[i] = 0x700;
                        break;
                    case "BRZ":
                        instructions[i] = 0x800;
                        break;
                    case "BRP":
                        instructions[i] = 0x900;
                        break;
                    case "MLT":
                        instructions[i] = 0xa00;
                        break;
                    case "DIV":
                        instructions[i] = 0xb00;
                        break;
                    case "MOD":
                        instructions[i] = 0xc00;
                        break;
                    default:
                        if(!type[i].equals("VAL"))
                        {
                            throw new LowCodeException("Low Compile Failed");
                        }
                }
                if(type[i].equals("VAL"))
                {
                    if(Integer.parseInt(location[i])>0xfff)
                    {
                        throw new LowCodeException();
                    }
                    instructions[i]=Integer.parseInt(location[i]);
                }
                else if(Integer.parseInt(location[i])>0xff)
                {
                    throw new LowCodeException();
                }
                else
                {
                    instructions[i]+=Integer.parseInt(location[i]);
                }
            }catch(Exception e)
            {
                throw new LowCodeException();
            }
                

        }
    }
    
    public void print()
    {
        for(int i=0;i<instructions.length;i++)
            System.out.println(Integer.toHexString(instructions[i]));
    }
    
    public int size()
    {
        return instructions.length;
    }
    
    public int getInstruction(int givenInt)
    {
        return instructions[givenInt];
    }
    
    public String getType(int givenInt)
    {
        return type[givenInt];
    }
    
    public int getLocation(int givenInt)
    {
        return Integer.parseInt(location[givenInt]);
    }
}
