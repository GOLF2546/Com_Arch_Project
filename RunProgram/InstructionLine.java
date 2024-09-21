
import java.util.*;

//class that covert the assembly code to operate on each type of instruction
public class InstructionLine 
{
    //linked list to store the assembly code
    private static LinkedList<Pair<String, Integer>> code = new LinkedList<>();

    //hashmap to store the label and its value
    private static HashMap<String, String> labelValue = new HashMap<>();

    //hashmap to store the label and its address
    private static HashMap<String, String> labelAddress = new HashMap<>();
    
    /**
     * Add the instruction and index to the linked list
     * @param instruction assembly code
     */
    public static void addInstruction(String instruction)
    {
        if (instruction.equals(""))
        {
            return;
            
        }
        code.add(new Pair<String, Integer>(instruction, code.size()));
    }

    /**
     * Get every label and its value
     * @return label and its value
     */
    public static HashMap<String, String> getLabelValue()
    {
        return labelValue;
    }

    /**
     * Get every label and its address
     * @param label label name
     */
    public static HashMap<String, String> getLabelAddress()
    {
        return labelAddress;
    }

    /**
     * Print the label and its value
     * @param label label name
     */
    public static void getLabelAddress(String label)
    {
        System.out.println(labelAddress.get(label));
    }

    /**
     * print the instruction at the index
     * @param index index of the instruction
     */
    public static void getInstruction(int index)
    {
        System.out.println(code.get(index).getFirst());
    }

    /**
    *create the machine code for the assembly code that sent assembly code to classes 
    that generate machine code for each type of instruction
     */
    public static void createMachineCode(){
        for(int i = 0; i < code.size(); i++)
            {
            Pair<String, Integer> p = code.get(i);
            String instruct = p.getFirst();
            String[] parts = instruct.split("\\s+");
            if(parts.length > 1){
                if(parts[1].equals(".fill")){
                labelValue.put(parts[0], parts[2]);
            }
            if (!parts[0].equals(""))
            {
                labelAddress.put(parts[0], String.valueOf(i));
                
            }
            }
            
            }
        for (Pair<String, Integer> p : code)
        {

            String instruct = p.getFirst();
            String[] parts = instruct.split("\\s+");
            switch (parts[1]) {
                case "add": R_type.gen_machine_code(parts);
                    break;
                case "nand": R_type.gen_machine_code(parts);
                    break;
                case "lw": I_type.gen_machine_code(parts);
                    break;
                case "sw": I_type.gen_machine_code(parts);
                    break;
                case "beq": I_type.gen_machine_code(parts);
                    break;
                case "jalr": J_type.gen_machine_code(parts); 
                    break;
                case "halt": O_type.gen_machine_code(parts);
                    break;
                case "noop": O_type.gen_machine_code(parts);
                    break;
                case ".fill": Fill_Type.gen_machine_code(parts);
                    break;
                default:
                    break;
            }
            
        }
        
    }

    /**
     * Print the assembly code
     */
    public static void printassembly()
    {
        for (Pair<String, Integer> p : code)
        {
            System.out.println(p.getFirst() + " " + p.getSecond());
        }
    }

    
}