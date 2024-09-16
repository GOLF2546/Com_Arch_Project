import java.util.HashMap;


public class O_type {


    private static String bit0to21 = "0000000000000000000000";

    private static HashMap<String, String> opcodes = new HashMap<>();

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
