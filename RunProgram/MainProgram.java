import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MainProgram{

    public static void main(String[] args) {

        Code_containtner.first_time();

        File file = new File("D:\\Comarch_Pj\\Com_Arch_Project-1\\RunProgram\\test_code.txt");

        try
        {
            Scanner read_file = new Scanner(file);

            while(read_file.hasNext()) InstructionLine.addInstruction(read_file.nextLine());

            InstructionLine.createMachineCode();

            read_file.close();
        }
        catch (FileNotFoundException e)
        {
            throw new CustomException.FileNotFoundException();
        }

        Code_containtner.print();

        List<String> machineCode = Code_containtner.get_this();
        MachineCodeSimulator.runSimulate(machineCode);

        // List<String> inputArray = Code_containtner.get_this();
        // BinaryStringToDecimal(inputArray);
        // for(int i = 0; i < inputArray.size(); i++){
        //     System.out.println(inputArray.get(i));
        // }

        
    }

//     public static void BinaryStringToDecimal(List<String> inputArray) { 
//         for(int i = 0; i < inputArray.size(); i++){
            
//             inputArray.set(i, binaryToDecimal(inputArray.get(i)));
//         }    
// }

// public static String binaryToDecimal(String binaryStr)
// {
//         if (binaryStr.length() != 32) {
//             throw new IllegalArgumentException("Binary string must be exactly 32 bits long");
//         }
//         if (binaryStr.charAt(0) == '1') {
//             long unsignedValue = Long.parseUnsignedLong(binaryStr, 2);
//             int signedValue = (int)(unsignedValue & 0xFFFFFFFF);
//             return Integer.toString(signedValue);
//         } else {
//             // For positive numbers, directly convert to hexadecimal
//             long decimalValue = Long.parseLong(binaryStr, 2);
//             return Long.toString(decimalValue);
//         }
   
// }


}
