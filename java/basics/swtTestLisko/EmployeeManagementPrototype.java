package swtTestLisko;

import java.util.List;

public class EmployeeManagementPrototype implements IEmployeeManagement {

    @Override
    public List<String> generateInitialEmployeeList() {
        return List.of("Martin S. Pinnér", "Marvin Bertsch", "Max Power");
    }
}
