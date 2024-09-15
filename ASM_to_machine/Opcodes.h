#include <unordered_map>

class Opcodes
{
    private:
        unordered_map<string, string> opcode;

    public:
        Opcodes()
        {
            //dest = regA + RegB
            opcode["add"] = "000";
            //dest = regA nand regB
            opcode["nand"] = "001";
            //mem = regA + offserField 
            //b <- mem
            opcode["lw"] = "010";
            //mem = regA + offserField 
            //b -> mem 
            opcode["sw"] = "011";
            //PC = (regA == regB)? PC+1+offsetField: PC+1;
            //PC is beg instruction
            opcode["beg"] = "100";
            /*
            regB = PC + 1
            PC = regA
            if regA and regB is the same reg 
                regB = PC + 1 and PC = regA
            PC is jalr instruction
            */
            opcode["jalr"] = "101";
            //simulation that halted occur
            opcode["halt"] = "110";
            //do nothing
            opcode["noop"] = "111";
        }

    string get_opcode(string input)
    {
        return opcode[input];
    }
};