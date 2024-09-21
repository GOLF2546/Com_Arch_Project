import java.util.HashMap;

//class for generate machine code for .fill instruction
public class Fill_Type 
{
    /**
     * Convert a decimal string to a signed 32-bit binary string.
     * @param decimalString decimal string
     * @return signed 32-bit binary string
     */
    public static String decimalToSignedBinary32(String decimalString) {
        int number = Integer.parseInt(decimalString);
        String binaryString = Integer.toBinaryString(number);
        while (binaryString.length() < 32) {
            binaryString = (number < 0 ? "1" : "0") + binaryString;
        }
        return binaryString;
    }

    /**
     * Generate machine code for the .fill instruction.
     * @param code assembly code
     */
    public static void gen_machine_code(String[] code)
    {
        try
        {
            Integer.parseInt(code[2]);

            String machine_code = decimalToSignedBinary32(code[2]);
            Code_containtner.add_machine_code(machine_code);
        }
        catch(NumberFormatException e)
        {
            HashMap<String, String> getLabelAddress = InstructionLine.getLabelAddress();

            String get_value = getLabelAddress.get(code[2]);

            if(get_value == null)
            {
                HashMap<String, String> get_LabelValue = InstructionLine.getLabelValue();
                
                get_value = get_LabelValue.get(code[2]);
            }

            String machine_code = decimalToSignedBinary32(get_value);
            Code_containtner.add_machine_code(machine_code);
        }
    }
}
