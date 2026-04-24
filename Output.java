package presentation;
import java.util.List;
public final class Output implements OutputService {
    public void display1(String s) {
        System.out.println(s);
    }
    public void display(List<String> list) {
        for (String s : list) {
            System.out.println(s);
        }
    }
}