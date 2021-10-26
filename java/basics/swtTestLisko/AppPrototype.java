package swtTestLisko;

import java.util.List;

public class AppPrototype {
    public static void main(String[] args) {
        EmployeeManagementPrototype eP = new EmployeeManagementPrototype();

        List<String> el = new EmployeeManagementPrototype().generateInitialEmployeeList();
        //List<String> el = eP.generateInitialEmployeeList();
        el.add("Martina Mustermann");
        System.out.println(el);
        }
}
