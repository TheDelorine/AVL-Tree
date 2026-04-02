import java.io.*;
import java.util.*;

public class Loader {
    public static void main(String[] args) {

        String filename = "data/dataset_50.txt";
        PhoneBook phoneBook = new PhoneBook();

        List<String> names = new ArrayList<>();

        long startAdd = System.currentTimeMillis();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");

                String name = parts[0];
                String phone = parts[1];

                phoneBook.add(name, phone);
                names.add(name);
            }

        } catch (IOException e) {
            //
        }

        long endAdd = System.currentTimeMillis();

        long startFind = System.currentTimeMillis();

        for (String name : names) {
            phoneBook.find(name);
        }

        long endFind = System.currentTimeMillis();

        long startRemove = System.currentTimeMillis();

        for (String name : names) {
            phoneBook.delete(name);
        }

        long endRemove = System.currentTimeMillis();

        System.out.println(endAdd - startAdd); // Добавление
        System.out.println(endFind - startFind); // Поиск
        System.out.println(endRemove - startRemove); // Удаление
    }
}