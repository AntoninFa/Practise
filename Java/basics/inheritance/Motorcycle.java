package inheritance;

public class Motorcycle extends Bicycle {

    int sdlkfj;

    public Motorcycle(int fünf, int sechs) {
        sdlkfj = fünf;
    }
    int hallo = 50000;
    @Override
    public void honk() {
        System.out.println("tooooot");
        System.out.println(sdlkfj);
    }

    @Override
    public void useBreak() {
        System.out.println("starkeBremse");
    }
}
