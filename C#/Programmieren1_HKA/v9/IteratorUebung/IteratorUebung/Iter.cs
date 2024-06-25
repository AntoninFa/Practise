
using System.Collections;
namespace IteratorUebung

{
    
    public class MyList : IEnumerable<MyList.IListNode>
    {
        IListNode? start;


        public class IListNode
        {
            public int Data;
            public IListNode? Nachfolger;

            public IListNode(int dt, IListNode? nf = null)
            {
                Data = dt;
                Nachfolger = nf;
            }
        }

        public IEnumerator<IListNode> GetEnumerator()
        {

            var c = start ?? null;
            while (c != null)
            {
                yield return c;
                c = c.Nachfolger ?? null;
            }
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }
    
        
        public MyList()
        {
            start = null;
        }
    
        public void Add(int dt)
        {
            start = new IListNode(dt, start);
        }
        
    }
    
    
    public class BinBaum {
        BinBaumNode? start;
	
	
        public class BinBaumNode {
            public int Data;
            public BinBaumNode? Links, Rechts;
            public BinBaumNode(int dt, BinBaumNode? lks = null, BinBaumNode? rchts=null) {
                Data = dt;Links = lks;Rechts = rchts;
            }
        }

        public void Add(int dt)
        {
            if (start== null)
            {
                start = new BinBaumNode(dt);
            }
            else
            {
                Insert(dt, start);
            }
            
        }

        private void Insert(int dt, BinBaumNode n)
        {
            // Nach Links
            if (dt < n.Data)
            {
                // Links noch nichts
                if (n.Links == null)
                {
                    n.Links = new BinBaumNode(dt);
                }
                else
                {
                    // Links schon ne Node
                    Insert(dt,n.Links);
                }
            }
            else
            {
                // dt ist größer gleich n.Data
                if (n.Rechts == null)
                {
                    n.Rechts = new BinBaumNode(dt);
                }
                else
                {
                    // Links schon ne Node
                    Insert(dt,n.Rechts);
                }
            }
            
        }
	
        
	
        public BinBaum() {
            start = null;
        }
	
    }
}