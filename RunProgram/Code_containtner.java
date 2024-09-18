import java.util.LinkedList;

public class Code_containtner 
{
    private static LinkedList<String> m_code;

    public static LinkedList<String> get_this()
    {
        return m_code;
    }

    public static int current_line()
    {
        return m_code.size();
    }

    public static void first_time()
    {
        m_code = new LinkedList<>();
    }

    public static void add_machine_code(String input)
    {
        m_code.add(input);
    }

    public static void print()
    {
        for(int i = 0; i < m_code.size(); i++)
        {
            System.out.println(i+1 + " " + m_code.get(i));
        }
    }

    public static void printbit22to24()
    {
        for(int i = 0; i < m_code.size(); i++)
        {
            System.out.println(i+1 + " " + m_code.get(i).substring(7, 10));
        }
        
    }

    public static void print_hex()
    {
        for(int i = 0; i < m_code.size(); i++)
        {
            System.out.println(i+1 + " " +binaryToHex(m_code.get(i)));
        }
    }

    // private static String binaryToHexString(String binary) {
    //     int decimal = Integer.parseInt(binary, 2);
    //     return Integer.toHexString(decimal).toUpperCase();
    // }
     public static String binaryToHex(String binaryStr) {
        // Ensure the binary string is exactly 32 bits long
        if (binaryStr.length() != 32) {
            throw new IllegalArgumentException("Binary string must be exactly 32 bits long");
        }

        // If the binary string represents a negative number (i.e., starts with '1' in two's complement)
        if (binaryStr.charAt(0) == '1') {
            // Convert to a negative two's complement 32-bit number
            // In Java, we can use parseUnsignedLong to handle the conversion correctly
            long unsignedValue = Long.parseUnsignedLong(binaryStr, 2);

            // For a 32-bit number, we mask with 0xFFFFFFFF and cast it to int to handle overflow
            int signedValue = (int)(unsignedValue & 0xFFFFFFFF);

            // Convert the signed value to a hexadecimal string
            return Integer.toHexString(signedValue);
        } else {
            // For positive numbers, directly convert to hexadecimal
            long decimalValue = Long.parseLong(binaryStr, 2);
            return Long.toHexString(decimalValue);
        }
    }


    

   
   
}
