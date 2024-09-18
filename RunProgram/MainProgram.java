import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MainProgram{

    public static void main(String[] args) {

        Code_containtner.first_time();

    File file = new File("D:\\Com_Arch_Project\\RunProgram\\test4.txt");
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

        Code_containtner.print_hex();

        List<String> machineCode = Code_containtner.get_this();
        MachineCodeSimulator.runSimulate(machineCode);
        
    }



}
