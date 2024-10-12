import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//class that combine every part of the program and run the program for showing the result of Memory and Register in every cycle
public class MainProgram{

    //main function that run the program
    public static void main(String[] args) {

        Code_containtner.first_time();

    File file = new File("RunProgram/test6.txt");
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

        List<String> machineCode = Code_containtner.get_this();
        MachineCodeSimulator.runSimulate(machineCode);
        
    }



}
