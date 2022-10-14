// See https://aka.ms/new-console-template for more information

using Microsoft.VisualBasic.CompilerServices;

namespace Often;
using System;

internal class Often
{
    private static void Main()
    {
        // n is the number of rows and collums our matrix will have
        int nxn = 10;
        
        
        
        Count countClass = new Count();
        bool[,] matrix = new bool[nxn, nxn];
        // filling the matrix with test values
        for (int i = 0; i < nxn; i++)
        {
            for (int j = 0; j < nxn; j++)
            {
                matrix[i, j] = countClass.oneOrNone();
            }
        }
        
        //printout
        for (int i = 0; i < nxn; i++)
        {
            for (int j = 0; j < nxn; j++)
            {
                if (matrix[i, j])
                {
                    Console.Write("1");
                }
                else
                {
                    Console.Write("0"); 
                }
                
            }
            Console.WriteLine();
        }
        
    }
    
    
}

internal class Count
{
    public bool oneOrNone()
    {
        Random random = new Random();
        int i = random.Next(0, 2);
        if (i < 1)
        {
            return false;
        }

        return true;
        //int i = Random.Next(1);

    }
    
}


