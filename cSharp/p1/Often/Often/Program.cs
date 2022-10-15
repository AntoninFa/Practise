// See https://aka.ms/new-console-template for more information

using System.Collections;
using Microsoft.VisualBasic.CompilerServices;

namespace Often;
using System;

internal class Often
{
    private static void Main()
    {
        //TODO as user input
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
        
        countClass.acceptedRows(matrix, nxn);
        
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
    private List<Int32> rowsL;
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

    /*
     * how many 1's in one given row
     */
    public int howMTrue(bool[] row)
    {
        int num = 0;
        foreach (bool b in row)if (b) num++;
        return num;
    }

    public void acceptedRows(bool[,] m, int nxn)
    {
        //TODO Maybe calculate what's the expected num of accepted rows for init.
        List<int> rList = new List<int>();
        if (m.Length == (nxn * nxn))
        {
            for (int i = 0; i < 100; i++)
            {
                
            }
        }
        else
        {
            throw new ArgumentException("given matrix does not add up with the given size");
        }
    }

    /**
     * returns the matching rows
     */
    private int[] rowsA()
    {
        return null;
    }



}


