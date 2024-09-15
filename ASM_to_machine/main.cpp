#define print_debug(for_print) \
cout << for_print << endl; \

#include "program.h"

int main(int argc, char* argv[])
{
    if(argc > 3)
    {
        cout << "argc <= 2 only";
    }
    else if(argc == 1)
    {
        cout << "not have name of file";
    }
    else
    {
        string filename = argv[1];

        Program open;
        open.main_program(filename);
    }
}