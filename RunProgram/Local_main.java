import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//class for test part 1 that generate machine code for the assembly code
public class Local_main 
{
    //main function that run for the test
    public static void main(String[] args) 
    {
        Code_containtner.first_time();

        File file = new File("RunProgram/test1.txt");

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
        System.out.println();
        Code_containtner.print_hex();
    }
}
