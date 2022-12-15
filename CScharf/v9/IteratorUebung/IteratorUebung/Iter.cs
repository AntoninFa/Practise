

namespace IteratorUebung
{
    public class IList
    {


        public class IListNode
        {
            public int Data;
            public IListNode Nachfolger;

            public IListNode(int dt, IListNode nf = null)
            {
                Data = dt;
                Nachfolger = nf;
            }
        }

        IListNode start;

        public IList()
        {
            start = null;
        }

        public void Add(int dt)
        {
            start = new IListNode(dt, start);
        }
    }
}