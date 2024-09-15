class Register
{
    private:
        unordered_map<string, string> reg;

    public:
        Register()
        {
            reg["X0"] = "00000000";
            reg["X1"] = "00000001";
            reg["X2"] = "00000010";
            reg["X3"] = "00000011";
            reg["X4"] = "00000100";
            reg["X5"] = "00000101";
            reg["X6"] = "00000110";
            reg["X7"] = "00000111";
            reg["X8"] = "00001000";
            reg["X9"] = "00001001";
            reg["X10"] = "00001010";
            reg["X11"] = "00001011";
            reg["X12"] = "00001100";
            reg["X13"] = "00001101";
            reg["X14"] = "00001110";
            reg["X15"] = "00001111";
            reg["X16"] = "00010000";
            reg["X17"] = "00010001";
            reg["X18"] = "00010010";
            reg["X19"] = "00010011";
            reg["X20"] = "00010100";
            reg["X21"] = "00010101";
            reg["X22"] = "00010110";
            reg["X23"] = "00010111";
            reg["X24"] = "00011000";
            reg["X25"] = "00011001";
            reg["X26"] = "00011010";
            reg["X27"] = "00011011";
            reg["X28"] = "00011100";
            reg["X29"] = "00011101";
            reg["X30"] = "00011110";
            reg["X31"] = "00011111";
        }
};

/**
 * for int -> binary 8 bits
 */
// #include "program.h"
// #include <bitset>
// int main()
// {
//     ofstream write("binary_number.txt");

//     for(int i = 0; i<32; i++) 
//         write <<"reg"<< "[\"X" << i << "\"]" << " = "<<'\"'<< bitset<8>(i).to_string() << '\"'<< ';'<< endl;

//     write.close();
// }