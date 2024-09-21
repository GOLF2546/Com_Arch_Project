import java.util.HashMap;

//class for generate machine code for R type instruction
public class R_type 
{
    //machine code of R type instruction since bit 3 to 15
    private static String bit3to15 = "0000000000000";

    //hashmap to store the opcode of R type instruction
    private static HashMap<String, String> opcodes = new HashMap<>();

    /**
     * Generate machine code for the R type instruction.
     * @param code assembly code that is add or nand
     */
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
