import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Local_main 
{
    public static void main(String[] args) 
    {
        Code_containtner.first_time();

        File file = new File("test_code");

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
