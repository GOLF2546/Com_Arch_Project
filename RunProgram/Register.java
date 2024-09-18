import java.util.ArrayList;

public class Register 
{
    public static ArrayList<String> reg = new ArrayList<>();

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
