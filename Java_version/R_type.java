import java.util.HashMap;

public class R_type 
{
    private static String bit3to15 = "0000000000000";

    private static HashMap<String, String> opcodes = new HashMap<>();

    public static void gen_machine_code(String[] code)
    {
        if(opcodes.isEmpty()) 
        {
            opcodes.put("add","000");
            opcodes.put("nand","001");
        }

        String opcode = opcodes.get(code[1]);
        String regA = Register.reg_to_binary(code[2]);
        String regB = Register.reg_to_binary(code[3]);
        String regD = Register.reg_to_binary(code[4]);

        String machine_code = Instruction31_25.getMSB() + opcode+regA+regB+bit3to15+regD;

        Code_containtner.add_machine_code(machine_code);
        // System.out.println(machine_code);
    }
}
