package inheritance;

public class Bicycle implements Vehicle {

    @Override
    public void driveSomewhere(int dir, int speed) {
        System.out.println("driveSomewhere");
    }

    @Override
    public void driveForeward(int speed) {
        String ha = "dfk";
        ha.equals("dfk");
        String fjd = new String ("driveForward");

        boolean xD = true;
        System.out.println("driveForeward");
        System.out.println(xD);
        System.out.println(new String ("driveForward"));

    }

    public boolean hallo(int zehn) {
        boolean xD = true;
        System.out.println("break");
        return true;

    }
    @Override
    public void useBreak() {
        System.out.println("break");

    }

    @Override
    public void honk() {
        System.out.println("Ring");
    }

    @Override
    public void tireChange() {
        System.out.println("pitStop");

    }

    public Bicycle returnThis() {
        return this;
    }
}
