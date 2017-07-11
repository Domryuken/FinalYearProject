/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smallguymachine;



/**
 *s
 * @author ladon
 */
public class SmallGuyMachine{
    
    
    private Memory memory;
    private CPU cpu;
    
    SmallGuyMachine(){
        memory = new Memory();
        cpu = new CPU(memory);
    }
    
//    public void runCPU()
//    {
//        int instruction = cpu.fetchInstruction();
//        instruction = cpu.decodeInstruction(instruction);
//        cpu.executeInstruction();
//    }
    
    public Registers getRegisters()
    {
        return cpu.getRegister();
    }
    
    public void fetch()
    {
//        System.out.println(cpu.fetchInstruction());
        cpu.fetchInstruction();
    }
    
    public void decode()
    {
        cpu.decodeInstruction(cpu.getRegister().getWholeInstruction());
    }
    
    public int execute(int givenInt, int input)
    {
        return cpu.executeInstruction(input);
    }
    
    public void reset()
    {
        cpu.reset();
        memory.resetMemory();
    }
    
    public void lowCompile(String givenString)throws Exception
    {
        LowCompiler lowCompiler = new LowCompiler(givenString);
        lowCompiler.compile();
        for(int i=0;i<lowCompiler.size();i++)
        {
            memory.setAddress(i,lowCompiler.getInstruction(i));
        }
    }
    
    public void highCompile()
    {
        //High level compiler goes here
    }
    
    public Integer[] getMemory()
    {
        return memory.getMemory();
    }
    
    public void commitMemory(int address, int number)
    {
        memory.setAddress(address,number);
    }
    
    
}
















































//        highButton.addActionListener(new ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//                //Action goes here
//            }
//        });
//        
//        
//        lowButton.addActionListener(new ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//                LowCompiler lowCompiler = new LowCompiler(lowArea.getText());
//                lowCompiler.compile();
//                for(int i=0;i<lowCompiler.size();i++)
//                {
//                    memory.setAddress(i,lowCompiler.getInstruction(i));
//                }
////                table = new JTable(model);
////                for(int i=0;i<16;i++)
////                {
////                    for(int j=0;j<16;j++)
////                    {
//////                        table.getColumn(i).
////                    }
////                }
//            }
//        });
//        
//        runButton.addActionListener(new ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//                while(!cpu.getHalt())
//                {
//                    cpu.decodeInstruction(cpu.fetchInstruction());
//                    cpu.executeInstruction();
//                }  
//            }
//        });
        



//        frame.getContentPane().setLayout(null);
//        JScrollPane scroll = new JScrollPane (highArea,
//                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        panel1.setLayout(new GridLayout(1,3));
        
//        frame.setLayout(new GridLayout(4,2));
//
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        table.getColumnModel().getColumn(0).setPreferredWidth(25);
//        table.getColumnModel().getColumn(1).setPreferredWidth(25);
//        table.getColumnModel().getColumn(2).setPreferredWidth(25);
//        table.getColumnModel().getColumn(3).setPreferredWidth(25);
//        table.getColumnModel().getColumn(4).setPreferredWidth(25);
//        table.getColumnModel().getColumn(5).setPreferredWidth(25);
//        table.getColumnModel().getColumn(6).setPreferredWidth(25);
//        table.getColumnModel().getColumn(7).setPreferredWidth(25);
//        table.getColumnModel().getColumn(8).setPreferredWidth(25);
//        table.getColumnModel().getColumn(9).setPreferredWidth(25);
//        table.getColumnModel().getColumn(10).setPreferredWidth(25);
//        table.getColumnModel().getColumn(11).setPreferredWidth(25);
//        table.getColumnModel().getColumn(12).setPreferredWidth(25);
//        table.getColumnModel().getColumn(13).setPreferredWidth(25);
//        table.getColumnModel().getColumn(14).setPreferredWidth(25);
//        table.getColumnModel().getColumn(15).setPreferredWidth(25);
//        
//        frame.setResizable(true);
//        lowArea.setEditable(true);
//        
//        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
//        frame.add(highArea);
//        frame.add(highButton);
//        frame.add(lowArea);
//        frame.add(lowButton);
//        frame.add(table);
//        frame.add(runButton);
//        frame.add(input);
//        frame.add(output);
////        frame.add(panel1);
////        frame.add(panel2);
////        frame.add(panel3);
//        frame.setVisible(true);