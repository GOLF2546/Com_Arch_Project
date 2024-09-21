import java.util.ArrayList;

//class to store the register and convert it to binary
public class Register 
{
    //arraylist to store the no. of register in binary
    public static ArrayList<String> reg = new ArrayList<>();

    /**
     * Convert the register to binary
     * @param input register
     * @return binary of the register
     */
    public static String reg_to_binary(String input)
    {
        if(reg.isEmpty())
        {
            reg.add("000");
            reg.add("001");
            reg.add("010");
            reg.add("011");
            reg.add("100");
            reg.add("101");
            reg.add("110");
            reg.add("111");
        }

        int temp = Integer.parseInt(input);
        if(temp > 7 || temp < 0) throw new CustomException.RegisterNotFoundException();

        return reg.get(temp);
    }
}
