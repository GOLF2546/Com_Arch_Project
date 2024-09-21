import java.util.HashMap;

//class for generate machine code for I type instruction
public class I_type 
{
    //hashmap to store the opcode of I type instruction
    private static HashMap<String, String> opcodes = new HashMap<>();

    /**
     * two compliment with some java build in function
     * @param decimal num 
     * @return string of two complement
     */
    private static String signed16bit(String decimal)
    {
        int number = Integer.parseInt(decimal);
        String binaryString;
        
        if (number >= 0) {
            binaryString = String.format("%16s", Integer.toBinaryString(number)).replace(' ', '0');
        } else {
            binaryString = Integer.toBinaryString(number);
            binaryString = binaryString.substring(binaryString.length() - 16);
        }

        return binaryString;
    }

    /**
     * Convert a signed 16-bit binary string to a decimal string.
     * @param binaryString 16-bit binary string
     * @return decimal string
     */
    private static String btod(String binaryString) { //for_debug
        if (binaryString.length() != 16) {
            throw new IllegalArgumentException("Input must be a 16-bit binary string.");
        }
        
        int number = Integer.parseInt(binaryString, 2);
        if (binaryString.charAt(0) == '1') {
            number -= 1 << 16;
        }
        
        return Integer.toString(number);
    }
   
    /**
     * Generate machine code for the I type instruction.
     * @param code assembly code that is lw, sw, beq
     */
    public static void gen_machine_code(String[] code)
    {
        if(opcodes.isEmpty()) 
        {
            opcodes.put("lw","010");
            opcodes.put("sw","011");
            opcodes.put("beq","100");
        }

        String temp2 = "";
        try
        {
            int temp = Integer.parseInt(code[4]);
            if (temp > 32767|| temp < -32768) throw new CustomException.Signed16BitsOverFlow();
        }
        catch (NumberFormatException e)
        {
            HashMap<String, String> get_lavel_address = InstructionLine.getLabelAddress();

            if(code[1].equals("beq"))
            {
                int current = Code_containtner.current_line()+1;
                String go_back = get_lavel_address.get(code[4]);
                current = Integer.parseInt(go_back) - current;
                temp2 = Integer.toString(current);
            }
            else temp2 = get_lavel_address.get(code[4]);
        }

        String opcode = opcodes.get(code[1]);
        String regA = Register.reg_to_binary(code[2]);
        String regB = Register.reg_to_binary(code[3]);
        String offset = (temp2.equals(""))? code[4]: temp2;

        offset = signed16bit(offset);
        
        String machine_code = Instruction31_25.getMSB() + opcode+regA+regB+offset;

        Code_containtner.add_machine_code(machine_code);
    }

}
