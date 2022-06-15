package phonebook;


public class Main {
    public static void main(String[] args) {

        try {
            Application.run();
            Application.printResult();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
