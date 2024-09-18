
import java.util.*;

public class InstructionLine 
{
    private static LinkedList<Pair<String, Integer>> code = new LinkedList<>();
    private static HashMap<String, String> labelValue = new HashMap<>();
    private static HashMap<String, String> labelAddress = new HashMap<>();
    
    public static void addInstruction(String instruction)
    {
        if (instruction.equals(""))
        {
            return;
            
        }
        code.add(new Pair<String, Integer>(instruction, code.size()));
    }

    public static HashMap<String, String> getLabelValue()
    {
        return labelValue;
    }

    public static HashMap<String, String> getLabelAddress()
    {
        return labelAddress;
    }

    
    public static void getLabelAddress(String label)
    {
        System.out.println(labelAddress.get(label));
    }

    public static void getInstruction(int index)
    {
        System.out.println(code.get(index).getFirst());
    }

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

    public static void print()
    {
        for (Pair<String, Integer> p : code)
        {
            System.out.println(p.getFirst() + " " + p.getSecond());
        }
    }

    
}