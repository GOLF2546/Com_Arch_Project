public class J_type 
{
    private static String bit0to15 = "0000000000000000";
    private static String opcode = "101";

    public static void gen_machine_code(String[] code)
    {
        String regA = Register.reg_to_binary(code[2]);
        String regB = Register.reg_to_binary(code[3]);

        String m_code = Instruction31_25.getMSB() + opcode + regA + regB + bit0to15;
        Code_containtner.add_machine_code(m_code);
    }
}
