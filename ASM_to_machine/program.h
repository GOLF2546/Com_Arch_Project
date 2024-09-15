#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

#include "Opcodes.h"

class SystaxErrorException : public exception
{
    private:
        int line;
        string message;
        string for_print;

    public:
        SystaxErrorException(const char* msg, int line)
        {
            message = msg;
            this->line = line;
            for_print = "Expected : " + message +" at " + to_string(line) + "\n";
        }

        const char* what() const throw()
        {
            return for_print.c_str();
        }
};

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
                    machine_code.push_back(generate_machine_code_II(text));
                }
            }

            string generate_machine_code_II(string text)
            {
                throw SystaxErrorException("test", 1);
            }
};