package random;

public enum EnumTest {

    SMALL(100, "11111"), NORMAL(150, "2"), DOUBLE(300, "3");
    private final int size;
    private final String day;

    EnumTest(int size, String day) {
        this.size = size;
        this.day = day;
    }

    public int getValue() {  // or getSize to be same !!!
        return this.size;
    }

    public String getDay() {
        return this.day;
    }

}

class Test {
    public static void main(String[] args) {
        System.out.println(EnumTest.SMALL.getValue()); // 100
        System.out.println(EnumTest.SMALL.getDay()); // 1
    }
}


