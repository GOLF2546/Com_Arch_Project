import java.util.HashMap;

//class for generate machine code for O type instruction
public class O_type {

    //machine code of O type instruction since bit 0 to 21
    private static String bit0to21 = "0000000000000000000000";

    //hashmap to store the opcode of O type instruction
    private static HashMap<String, String> opcodes = new HashMap<>();

    /**
     * Generate machine code for the O type instruction.
     * @param code assembly code that is halt or noop
     */
    public static void gen_machine_code(String[] code)
    {
        if(opcodes.isEmpty()) 
        {
            opcodes.put("halt","110");
            opcodes.put("noop","111");
        }

        String opcode = opcodes.get(code[1]);

        String machine_code = Instruction31_25.getMSB() + opcode + bit0to21;

        Code_containtner.add_machine_code(machine_code);
        // System.out.println(machine_code);
    }
}
