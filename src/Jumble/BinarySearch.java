
package Jumble;
/**
 * File Name: BinarySearch.java
 * Description: This class contains only a BinarySearch method in order to 
 * search through the dictionary file to find a word.
 * @author Branavan Nagendiram
 */
public class BinarySearch 
{
    public static final int NOT_THERE = -1;
    public static int BinarySearch(Comparable[] arr, Comparable searchValue
    , int low , int high)
    {
        if(high <= low)
        {
            return NOT_THERE;
        }
        int middleValue = (high + low) / 2;
        if(arr[middleValue].equals(searchValue))
        {
            return middleValue;
        }
        else if(arr[middleValue].compareTo(searchValue) > 0 )
        {
            return BinarySearch(arr, searchValue, low , middleValue);
        }
        else
            return BinarySearch(arr, searchValue, middleValue + 1 , high);
    }
}
