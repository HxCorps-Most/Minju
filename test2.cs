using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    public class TextInput
    {

        protected string s = "";

        public virtual void Add(char c)
        {
        
            string a = Convert.ToString(c);

            this.s = String.Concat(this.s,a);

        }

        public string GetValue()
        {

            return this.s;

        }


    }

    public class NumericInput : TextInput
    {

        public override void Add(char c)
        {

            if (Char.IsNumber(c))
            {                 
                string a = Convert.ToString(c);

                this.s = String.Concat(this.s, a);


            }

            else return;
        }





    }

    public class UserInput
    {
        public static void Main(string[] args)
        {
            TextInput input = new NumericInput();
            input.Add('1');
            input.Add('a');
            input.Add('0');
            Console.WriteLine(input.GetValue());

        }

    }
}