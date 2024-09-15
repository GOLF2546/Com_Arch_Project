#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>

using namespace std;

#include "Custom_exception.h"
#include "Opcodes.h"

class Program
{
    public:
        /**
         * enter this file
         * @param reafile ifstream that already readfile
         */
        void main_program(string filename)
        {
            ifstream readfile(filename);

            if(is_file_exit(readfile)) cout << "file not found" << endl;
            else
            {
                try
                {
                    generate_machine_code_I(readfile);
                }
                catch(SystaxErrorException e) //alway close ifstream
                {
                    readfile.close();
                    throw e;
                }
                catch(exception e) //alway close ifstream
                {
                    readfile.close();
                    throw e;
                }
            }

            readfile.close();
        }

        private:
            Opcodes opcode_program;

            /**
             * check if file exit?
             * @param input filename
             */
            bool is_file_exit(ifstream& file) {return !((bool) file);};

            /**
             * read code and generate machine code then write machinecode file by call another function
             * @param readfile ifstream that already read file
             */
            void generate_machine_code_I(ifstream& readfile)
            {
                string text;
                int line = 1;

                vector<string> machine_code;

                while(getline(readfile, text))
                {
                    machine_code.push_back(generate_machine_code_II(text, line));
                    line += 1;
                }

                ofstream write("machine_code.txt");
                for(string i: machine_code) write << i << endl;
                write.close();
            }

            string generate_machine_code_II(string text, int line)
            {
                vector<string> instruction = split_with_space(text);
                
                try
                {
                    string opcode = opcode_program.get_opcode(instruction[0]);
                    
                    return opcode; //now opcode only
                }
                catch(SystaxErrorException e)
                {
                    throw SystaxErrorException(text, line);
                }
            }

            /**
             * split word with space
             * @param code current line of code
             */
            vector<string> split_with_space(string code)
            {
                stringstream ss(code);
                vector<string> for_return;
                string  instruction;

                while(ss >> instruction)
                {
                    for_return.push_back(instruction);
                }

                return for_return;
            }
};