public class CustomException 
{
    public static class FileNotFoundException extends RuntimeException
    {
        public FileNotFoundException() { super(); }
    }

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
         */
        public SyntaxErrorException(String code, int line) { super(); }
    }

    public static class RegisterNotFoundException extends RuntimeException
    {
        public RegisterNotFoundException() { super(); }
    }

    public static class Signed16BitsOverFlow extends RuntimeException
    {
        public Signed16BitsOverFlow() { super(); }   
    }
}
