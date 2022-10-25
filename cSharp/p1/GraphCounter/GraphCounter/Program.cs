using System;

public class Haupt
{
    static void Main()
    {
        int[] vertices = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
        string edges = "{[1,3],[1,5],[3,4],[4,2],[4,5],[4,6],[5,4],[5,6],[6,2]}";
        char[] edgesArray = edges.ToCharArray();

        bool second = false;
        int counter = 0;

        while (edges[counter] != null)
        {
            if (edges[counter] == '[')
            {
                String t2 = "";
                while (edges[counter] != ',')
                {
                    t2.Append(edges[counter]);
                    counter++;
                }

                Int32.TryParse(t2);
            }
            else
            {
                counter++;
            }
    }

}

public class Logik
{
    
}


public class Tupel
{
    public int A;
    public int B;

    Tupel(int a, int b)
    {
        this.A = a;
        this.B = b;
    }
}