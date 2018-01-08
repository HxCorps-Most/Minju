using System;

namespace csharptest
{
    public static class Palindrome{

        public static bool IsPalindrome(string word){

        bool result;
        char [] arr = word.ToCharArray();
        Array.Reverse(arr);
        string reversed = new string(arr);

        result = word.Equals(reversed, StringComparison.OrdinalIgnoreCase);
        
        return result;
        }

        public static void Main(string[] args){
            Console.WriteLine(Palindrome.IsPalindrome("devLeveled"));
        }

    }
}
