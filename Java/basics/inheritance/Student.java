package inheritance;

public class Student {
    private int alter;
    boolean male;
    String name;
    public Student(int uberalter, boolean male, String name) {
        this.alter = uberalter;
        this.male = male;
        this.name = name;
    }

    public boolean isMale() {
        return male;
    }

    public String getName() {
        return name;
    }

    public int getAlter() {
        return alter;
    }

    public void setAlter(int alter) {
        if (alter > 0) {
            this.alter = alter;
        } else {
            throw new IllegalArgumentException("dsfsd");
        }
    }


    public void setName(String name) {
        this.name = name;
    }
}
