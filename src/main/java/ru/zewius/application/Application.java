package ru.zewius.application;

import ru.zewius.application.data.Data;

import java.io.IOException;
import java.util.Scanner;

/**
 * Основной класс программы для работы с автозаполнением.
 */
public class Application {

    private static final String CSV_TABLE_PATH = "src/main/resources/data/airports.csv";

    public static void main(String[] args) {
        try {
            Data data = new Data();
            Scanner scanner = new Scanner(System.in);

            data.buildAutocomplete(CSV_TABLE_PATH, Integer.parseInt(args[0]));

            System.out.print("Введите строку: ");
            String answer = scanner.nextLine();

            while (!answer.equals("!quit")) {
                Long startSearchTime = System.currentTimeMillis();
                Iterable<String> matches = data.getKeysByPrefix(answer);
                Long finishSearchTime = System.currentTimeMillis();

                int keysCount = 0;

                for (String match : matches) {
                    System.out.println("<" + match + ">" + "[" + data.getValueByKey(match) + "]");
                    keysCount++;
                }

                System.out.println("Количество найденных строк: " + keysCount);
                System.out.println("Время, затраченное на поиск: " + (finishSearchTime - startSearchTime) + " мс");

                System.out.print("Введите строку: ");
                answer = scanner.nextLine();
            }
        }
        catch (IOException ex) {
            System.out.println("Ошибка при чтении файла:\n" + ex);
        }
        catch (NumberFormatException ex) {
            System.out.println("В качестве аргумента указан символ, а не число:\n" + ex);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Неверно указан индекс:\n" + ex);
        }
        catch (IllegalArgumentException ex) {
            System.out.println("Неверно введена строка для поиска:\n" + ex);
        }
        catch (Exception ex) {
            System.out.println("Ошибка приложения:\n" + ex);
        }
    }
}
