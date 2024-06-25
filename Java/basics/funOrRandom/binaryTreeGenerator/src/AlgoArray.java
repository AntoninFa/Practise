import java.util.ArrayList;

public class AlgoArray extends ArrayList {


    @Override
    public Object get(int index) {
        return super.get(index +1);
    }

    @Override
    public Object set(int index, Object element) {
        return super.set(index + 1, element);
    }
}
