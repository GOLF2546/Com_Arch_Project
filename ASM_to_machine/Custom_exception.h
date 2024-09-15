/**
 * @param msg code that occur error
 * @param line line of code that error
 */
class SystaxErrorException : public exception
{
    private:
        int line;
        string message;
        string for_print;

    public:
        /**
         * @param msg code that occur error
         * @param line line of code that error
         */
        SystaxErrorException(string msg, int line)
        {
            message = msg;
            this->line = line;
            for_print = "Expected : " + message +" at " + to_string(line) + "\n";
        }

        SystaxErrorException()
        {
            for_print = "";
        }

        const char* what() const throw()
        {
            return for_print.c_str();
        }
};