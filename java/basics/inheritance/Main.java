package inheritance;

public class Main {


    public static void main(String[] args) {

        Student student = new Student(22, true, "Felix");
        System.out.println(student.getAlter());
        System.out.println(student.getAlter());
        System.out.println(student.getAlter());
        System.out.println(student.getAlter());
        student.setAlter(-44);
        System.out.println(student.getAlter());

    }
    

        /**
        Vehicle erstes = new Motorcycle(6, 3);
        erstes.honk();
        Bicycle snd = new Motorcycle(5,5);
        Motorcycle thd = new Motorcycle(4, 5);
        Bicycle bike = new Bicycle();
        erstes.useBreak();
        System.out.println(snd.returnThis().getClass());
        System.out.println(thd.returnThis().getClass());
        System.out.println(bike.returnThis().getClass());

         */
    }