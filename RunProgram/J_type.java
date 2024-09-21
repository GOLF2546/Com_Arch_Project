//class for generate machine code for J type instructions
public class J_type 
{

    //machine code of J type instruction since bit 0 to 15
    private static String bit0to15 = "0000000000000000";

    //opcode of J type instruction that is jalr command
    private static String opcode = "101";

    /**
     * Generate machine code for the J type instruction.
     * @param code assembly code
     */
    public static void gen_machine_code(String[] code)
    {
        String regA = Register.reg_to_binary(code[2]);
        String regB = Register.reg_to_binary(code[3]);

        String m_code = Instruction31_25.getMSB() + opcode + regA + regB + bit0to15;
        Code_containtner.add_machine_code(m_code);
    }
}
