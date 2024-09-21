//class for pair of objects
class Pair<T1, T2>
{
    //first and second object that will be stored in the pair
    T1 first;
    T2 second;

    //constructor for the pair
    Pair(T1 first, T2 second)
    {
        this.first = first;
        this.second = second;
    }

    /*
     * Get the first object
     */
    public T1 getFirst()
    {
        return first;
    }

    /*
     * Get the second object
     */
    public T2 getSecond()
    {
        return second;
    }

    /*
     * Set the first object
     */
    public void setFirst(T1 first)
    {
        this.first = first;
    }

    /*
     * Set the second object
     */
    public void setSecond(T2 second)
    {
        this.second = second;
    }

    /*
     * Check if the pair is equal to the other pair
     */
    public String toString()
    {
        return "(" + first + ", " + second + ")";
    }


}