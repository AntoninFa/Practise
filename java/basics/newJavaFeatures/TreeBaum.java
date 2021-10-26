package firstTask;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TreeBaum {
    private final List<String> stringList;
    private final int paramNum;

    public TreeBaum() {
        stringList = List.of("Java SE 8", "Java SE 9", "Java SE 10", "Java SE 11",
                "Java SE 12", "Java SE 13", "Java SE 14");
        paramNum = stringList.size();
        String joined = stringList.stream().map(Object::toString).collect(Collectors.joining("\n"));
        System.out.println(joined);
        int index;
        int acht = 0;
        int neun = 0;
        int z = 0;
        int elf = 0;
        int zw = 0;
        int dre = 0;
        int vie = 0;
        int defaultInt = 0;

        //for (int n = 0; n < 10; n++) {
            index = (int) (Math.random() * stringList.size());
            switch (index + 8) {
                case 8 -> acht++;
                case 9 -> neun++;
                case 10 -> z++;
                case 11 -> elf++;
                case 12 -> zw++;
                case 13 -> dre++;
                case 14 -> vie++;
                default -> defaultInt++;
            }
        switch (index) {
            case 6 -> System.out.println("Keeping updated");
            case 0, 1, 2, 3, 4, 5 -> System.out.println("Running late");
            default -> System.out.println("rip");
        }

        /*}
        System.out.println(acht);
        System.out.println(neun);
        System.out.println(z);
        System.out.println(elf);
        System.out.println(zw);
        System.out.println(dre);
        System.out.println(vie);
        System.out.println(defaultInt);
        System.out.println("---------------------------");
        System.out.println(stringList.get(0));
        System.out.println(stringList.get(6));
         */

        System.out.println(index);
    }

    public int getParamNum() {
        return paramNum;
    }
}
