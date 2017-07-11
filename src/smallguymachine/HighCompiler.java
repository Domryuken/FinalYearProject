/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smallguymachine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ladon
 */
public class HighCompiler {
    
    int count =0;
    String original;
    int ifCount = 0;
    Pattern readPat = Pattern.compile("read\\([A-z]+\\);");
    Pattern writePat = Pattern.compile("write\\([A-z]+\\);");
    Pattern ifPat = Pattern.compile("if\\([A-z]+[>=<][>=<]?[A-z]\\)\\{"
            + "");
    Pattern whilePat = Pattern.compile("while");
    Pattern calcPat = Pattern.compile("[A-z]+[=][A-z]+[-+*/%][A-z]+");
    
    HighCompiler(String givenString)
    {
       original = givenString.replaceAll("\\n", "").replaceAll("\\s", "");
    }
    
    private String prepRun()throws LowCodeException
    {
        String returnString;
        Pattern valPattern = Pattern.compile("run\\{(.*?\\}+)+");
        Matcher valMatcher = valPattern.matcher(original);
        String runString;
        if(valMatcher.find()){
            runString = valMatcher.group();
        }else{
            throw new LowCodeException();
        }
        runString = runString.substring(4, runString.length()-1);
        returnString =  prepStatement(runString);
        return returnString;
    }
    
    private String prepStatement(String stateString)throws LowCodeException
    {
        count++;
//        System.out.println("state string is: "+stateString);
        String returnString = "";
        if(stateString.startsWith("read"))
        {
            returnString = returnString + prepRead(stateString.split(";",2)[0]);
            stateString = stateString.split(";",2)[1];
        
        }else if(stateString.startsWith("write"))
        {
            returnString = returnString + prepWrite(stateString.split(";",2)[0]);
            stateString = stateString.split(";",2)[1];
        
        }else if(stateString.startsWith("if"))
        {
            String[] Strings = prepIf(stateString);
            returnString = returnString + Strings[0];
            stateString = Strings[1];
        
        }else if(stateString.startsWith("while"))
        {
            String[] Strings = prepWhile(stateString);
            returnString = returnString + Strings[0];
            stateString = Strings[1];
            
        }else if(stateString.startsWith("calc"))
        {
            returnString = returnString + prepCalc(stateString.split(";",2)[0]);
            stateString = stateString.split(";",2)[1];
        }
        else if(stateString.startsWith("stop();"))
        {
            returnString = returnString + "STP 0\n";
            stateString = stateString.split(";",2)[1];
        }else
        {
            throw new LowCodeException();
        }
        if(!stateString.isEmpty())
            returnString = returnString + prepStatement(stateString);
        return returnString;
    }
    
    private String prepCalc(String calcString) throws LowCodeException
    {
        calcString = calcString.replaceAll("calc\\(","").replaceAll("\\)", "");
        String calcType;
        if(calcString.indexOf('+')>=0)
            calcType="ADD";
        else if(calcString.indexOf('-')>=0)
            calcType="SUB";
        else if(calcString.indexOf('*')>=0)
            calcType="MLT";
        else if(calcString.indexOf('/')>=0)
            calcType="DIV";
        else if(calcString.indexOf('%')>=0)
            calcType="MOD";
        else
            throw new LowCodeException();
        String val1 = calcString.split("=",2)[1].split("[-+*/%]",2)[0];
        String val2 = calcString.split("=",2)[1].split("[-+*/%]",2)[1];
        String equals = calcString.split("=",2)[0];
        calcString = "LOD #"+val1+"\n"+calcType+" #"+val2+"\nSAV #"+equals+"\n";
        return calcString;
    }
    
    //the same as prepIf but it adds an extra branch always at the end that will
    //loop back up to the top creating a while loop
    private String[] prepWhile(String ifString)throws LowCodeException
    {
        String leftover = ifString.split("\\)",2)[1];
        ifString = ifString.split("\\{",2)[0];
        ifString = ifString.replaceFirst("while\\(","").replaceFirst("\\)", "");
        String[] returnStrings = new String[2];
        
        if(ifString.indexOf(">=")>=0){
            returnStrings[0] = "LOD #"+ifString.split(">=",2)[0]+"\n"
                    + "SUB #"+ifString.split(">=",2)[1]+"\n"
                    + "BRP #ifa"+ifCount+"\n"
                    + "BRA #ifb"+ifCount+"\n"
                    + "#ifa"+ifCount+" ";
        }else if(ifString.indexOf("<=")>=0){
            returnStrings[0] = "LOD #"+ifString.split("<=",2)[1]+"\n"
                    + "SUB #"+ifString.split("<=",2)[0]+"\n"
                    + "BRP #ifa"+ifCount+"\n"
                    + "BRA #ifb"+ifCount+"\n"
                    + "#ifa"+ifCount+" ";
        }else if(ifString.indexOf("=")>=0){
            returnStrings[0] = "LOD #"+ifString.split("=",2)[1]+"\n"
                    + "SUB #"+ifString.split("=",2)[0]+"\n"
                    + "BRZ #ifa"+ifCount+"\n"
                    + "BRA #ifb"+ifCount+"\n"
                    + "#ifa"+ifCount+" ";
        }else if(ifString.indexOf(">")>=0){
            returnStrings[0] = "LOD #"+ifString.split(">",2)[0]+"\n"
                    + "SUB #"+ifString.split(">",2)[1]+"\n"
                    + "SUB #Sys1\n"
                    + "BRP #ifa"+ifCount+"\n"
                    + "BRA #ifb"+ifCount+"\n"
                    + "#ifa"+ifCount+" ";
        }else if(ifString.indexOf("<")>=0){
            returnStrings[0] = "LOD #"+ifString.split("<",2)[1]+"\n"
                    + "SUB #"+ifString.split("<",2)[0]+"\n"
                    + "SUB #Sys1\n"
                    + "BRP #ifa"+ifCount+"\n"
                    + "BRA #ifb"+ifCount+"\n"
                    + "#ifa"+ifCount+" ";

        }else{
            throw new LowCodeException();
        }
        String ifEnd = "\n#ifb"+ifCount+" ";
        ifCount++;
        String loopEnd = "BRA #bra"+ifCount;
        String loopStart = "#bra"+ifCount+" ";
        String[] innerOuter = splitInner(leftover);
        returnStrings[0]=loopStart+returnStrings[0]+prepStatement(innerOuter[0])+loopEnd+ifEnd;
        returnStrings[1]=innerOuter[1];
        return returnStrings;
    }
    
    private String[] prepIf(String ifString) throws LowCodeException
    {
        String leftover = ifString.split("\\)",2)[1];
        ifString = ifString.split("\\{",2)[0];
        ifString = ifString.replaceFirst("if\\(","").replaceFirst("\\)", "");
        String[] returnStrings = new String[2];
        
        if(ifString.indexOf(">=")>=0){
            returnStrings[0] = "LOD #"+ifString.split(">=",2)[0]+"\n"
                    + "SUB #"+ifString.split(">=",2)[1]+"\n"
                    + "BRP #ifa"+ifCount+"\n"
                    + "BRA #ifb"+ifCount+"\n"
                    + "#ifa"+ifCount+" ";
        }else if(ifString.indexOf("<=")>=0){
            returnStrings[0] = "LOD #"+ifString.split("<=",2)[1]+"\n"
                    + "SUB #"+ifString.split("<=",2)[0]+"\n"
                    + "BRP #ifa"+ifCount+"\n"
                    + "BRA #ifb"+ifCount+"\n"
                    + "#ifa"+ifCount+" ";
        }else if(ifString.indexOf("=")>=0){
            returnStrings[0] = "LOD #"+ifString.split("=",2)[1]+"\n"
                    + "SUB #"+ifString.split("=",2)[0]+"\n"
                    + "BRZ #ifa"+ifCount+"\n"
                    + "BRA #ifb"+ifCount+"\n"
                    + "#ifa"+ifCount+" ";
        }else if(ifString.indexOf(">")>=0){
            returnStrings[0] = "LOD #"+ifString.split(">",2)[0]+"\n"
                    + "SUB #"+ifString.split(">",2)[1]+"\n"
                    + "SUB #Sys1\n"
                    + "BRP #ifa"+ifCount+"\n"
                    + "BRA #ifb"+ifCount+"\n"
                    + "#ifa"+ifCount+" ";
        }else if(ifString.indexOf("<")>=0){
            returnStrings[0] = "LOD #"+ifString.split("<",2)[1]+"\n"
                    + "SUB #"+ifString.split("<",2)[0]+"\n"
                    + "SUB #Sys1\n"
                    + "BRP #ifa"+ifCount+"\n"
                    + "BRA #ifb"+ifCount+"\n"
                    + "#ifa"+ifCount+" ";

        }else{
            throw new LowCodeException();
        }
        String ifEnd = "#ifb"+ifCount+" ";
        ifCount++;
        String[] innerOuter = splitInner(leftover);
        returnStrings[0]=returnStrings[0]+prepStatement(innerOuter[0])+ifEnd;
        returnStrings[1]=innerOuter[1];
        return returnStrings;
    }
    
    private String prepRead(String readString)
    {
        readString = readString.replaceAll("read\\(","").replaceAll("\\)", "");
        readString = "RCV 0\nSAV #" + readString + "\n";
        return readString;
    }
    
    private String prepWrite(String writeString)
    {        
        writeString = writeString.replaceAll("write\\(","").replaceAll("\\)", "");
        writeString = "LOD #" + writeString + "\nSND 0\n";
        return writeString;
    }
    
    private String prepVals() throws LowCodeException
    {
        String[] vals;
        Pattern valPattern = Pattern.compile("values\\{.*?\\}");
        Matcher valMatcher = valPattern.matcher(original);
        String valsString = "";
        if(valMatcher.find()){
            vals = valMatcher.group().replaceAll("values\\{", "").replaceAll("\\}", "").trim().split(";");
            for(int i=0;i<vals.length;i++)
            {
                if(vals[i].split("=").length!=2)
                    throw new LowCodeException();
                
                valsString = valsString + "#" + vals[i].split("=")[0]
                        +" VAL "+ vals[i].split("=")[1] + "\n";
            }
            valsString = valsString + "#Sys1 VAL 1\n";
            return valsString;
        }
        else
        {
            throw new LowCodeException();
        }
    }
    
    private String[] splitInner(String input)throws LowCodeException
    {
        if(input.charAt(0)!='{')
            throw new LowCodeException();
        String[] returnStrings = new String[2];
        int splitOn = 0;
        int open = 0;
        int close = 0;
        for(int i=0;i<input.length();i++)
        {
            if(input.charAt(i)=='{')
            {
                open++;
            }
            else if(input.charAt(i)=='}')
            {
                close++;
                
            }
            if(open==close)
            {
                splitOn=i+1;
                i=input.length();
            }
//            else
//            {
//            }
        }
        returnStrings[0]=input.substring(+1,splitOn-1);
        returnStrings[1]=input.substring(splitOn);
        return returnStrings;
    }
    
    private String branchCorrection(String input)
    {
        Pattern pattern = Pattern.compile("(#[A-z][A-z][A-z][0-9]+\\s)(#[A-z][A-z][A-z][0-9]+\\s)+");
        String[] lines = input.split("\n");
        String replacing = "";
        for(int i=0;i<lines.length;i++)
        {
            Matcher matcher = pattern.matcher(lines[i]);
            if(matcher.find())
            {
                String[] replaced = matcher.group().split(" ");
                replacing = replaced[0];
                for(int j=0;j<replaced.length;j++)
                {
                    if(replaced[j].charAt(0)=='#')
                    {
                        input = input.replaceAll(replaced[j],replacing);
                    }
                    else
                    {
                        j=replaced.length;
                    }
                }
                
                CharSequence repeats = replacing + " " + replacing;
                while(input.contains(repeats))
                    input = input.replaceAll(repeats.toString(), replacing);
                
            }
        }
        
        
        
        
        
        
        return input;
    }
    
    public String compile() throws LowCodeException
    {
        String compiled = prepRun() +"\n"+ prepVals();
        compiled = branchCorrection(compiled);
        return compiled;
    }
}
