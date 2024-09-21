public class CustomException 
{
    /**
     * throw when fine not found
     */
    public static class FileNotFoundException extends RuntimeException
    {
        public FileNotFoundException() { super(); }
    }

    /**
     * throw when syntax error occur
     */
    public static class SyntaxErrorException extends RuntimeException
    {
        /**
         * throw with out tell error line
         */
        public SyntaxErrorException() { super(); }

        /**
         * throw with error line
         * @param code code that occur error
         * @param line line of error code
         * @param mess cause
         */
        public SyntaxErrorException(String code, int line, String mess) { super(String.format("SyntaxError : %s at line %d | %s", code, line, mess)); }
    }

    /**
     * throw when register is not in range 0-7
     */
    public static class RegisterNotFoundException extends RuntimeException
    {
        public RegisterNotFoundException() { super(); }
    }

    /**
     * throw when bit more than 16 bit
     */
    public static class Signed16BitsOverFlow extends RuntimeException
    {
        public Signed16BitsOverFlow() { super(); }   
    }

    /**
     * throw when have the same label in one file
     */
    public static class LabelAlreadyExitException extends RuntimeException
    {
        public LabelAlreadyExitException(String label) { super(label); }   
    }

    /**
     * throw when Label not found
     */
    public static class LabelNotRegconizeException extends RuntimeException
    {
        public LabelNotRegconizeException(String label) { super(label); }   
    }

    /**
     * throw when label lenght more than 6 character
     */
    public static class LabelLenghtMoreThan6Exception extends RuntimeException
    {
        public LabelLenghtMoreThan6Exception(String label) { super(label); }   
    }
}
