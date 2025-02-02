import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите задание (1-5):");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                task1(scanner);
                break;
            case 2:
                task2(scanner);
                break;
            case 3:
                task3(scanner);
                break;
            case 4:
                task4(scanner);
                break;
            case 5:
                task5(scanner);
                break;
            default:
                System.out.println("Неверный выбор.");
        }
    }

    // 1
    private static void task1(Scanner scanner) throws IOException {
        System.out.println("Введите путь к первому файлу:");
        String file1 = scanner.nextLine();
        System.out.println("Введите путь ко второму файлу:");
        String file2 = scanner.nextLine();

        List<String> lines1 = Files.readAllLines(Paths.get(file1));
        List<String> lines2 = Files.readAllLines(Paths.get(file2));

        int maxLines = Math.max(lines1.size(), lines2.size());
        for (int i = 0; i < maxLines; i++) {
            String line1 = i < lines1.size() ? lines1.get(i) : "";
            String line2 = i < lines2.size() ? lines2.get(i) : "";
            if (!line1.equals(line2)) {
                System.out.println("Строка " + (i + 1) + ":");
                System.out.println("Файл 1: " + line1);
                System.out.println("Файл 2: " + line2);
            }
        }
    }

    // 2
    private static void task2(Scanner scanner) throws IOException {
        System.out.println("Введите путь к файлу:");
        String file = scanner.nextLine();
        List<String> lines = Files.readAllLines(Paths.get(file));
        String longest = lines.stream().max(Comparator.comparingInt(String::length)).orElse("");
        System.out.println("Длина самого длинного ряда: " + longest.length());
        System.out.println("Самый длинный ряд: " + longest);
    }

    // 3
    private static void task3(Scanner scanner) throws IOException {
        System.out.println("Введите путь к файлу:");
        String file = scanner.nextLine();
        List<String> lines = Files.readAllLines(Paths.get(file));
        int totalSum = 0;

        for (String line : lines) {
            int[] array = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
            int min = Arrays.stream(array).min().orElse(0);
            int max = Arrays.stream(array).max().orElse(0);
            int sum = Arrays.stream(array).sum();
            totalSum += sum;
            System.out.println("Массив: " + Arrays.toString(array));
            System.out.println("Мин: " + min + ", Макс: " + max + ", Сумма: " + sum);
        }
        System.out.println("Общая сумма всех элементов: " + totalSum);
    }

    // 4
    private static void task4(Scanner scanner) throws IOException {
        System.out.println("Введите путь к файлу:");
        String file = scanner.nextLine();
        System.out.println("Введите массив чисел через пробел:");
        int[] numbers = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        List<String> output = new ArrayList<>();
        output.add(Arrays.stream(numbers).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
        output.add(Arrays.stream(numbers).filter(n -> n % 2 == 0).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
        output.add(Arrays.stream(numbers).filter(n -> n % 2 != 0).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
        output.add(Arrays.stream(numbers).mapToObj(String::valueOf).collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
            Collections.reverse(list);
            return String.join(" ", list);
        })));
        Files.write(Paths.get(file), output);
    }

    // 5
    private static void task5(Scanner scanner) throws IOException {
        System.out.println("Введите путь к файлу сотрудников:");
        String file = scanner.nextLine();
        List<String> employees = new ArrayList<>(Files.readAllLines(Paths.get(file)));

        while (true) {
            System.out.println("1. Добавить сотрудника\n2. Редактировать\n3. Удалить\n4. Найти\n5. Вывести всех\n6. Сохранить и выйти");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Введите фамилию:");
                    String surname = scanner.nextLine();
                    System.out.println("Введите возраст:");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    employees.add(surname + " " + age);
                    break;
                case 2:
                    System.out.println("Введите индекс редактируемого сотрудника:");
                    int editIndex = scanner.nextInt();
                    scanner.nextLine();
                    if (editIndex >= 0 && editIndex < employees.size()) {
                        System.out.println("Введите новую фамилию:");
                        String newSurname = scanner.nextLine();
                        System.out.println("Введите новый возраст:");
                        int newAge = scanner.nextInt();
                        scanner.nextLine();
                        employees.set(editIndex, newSurname + " " + newAge);
                    }
                    break;
                case 3:
                    System.out.println("Введите индекс удаляемого сотрудника:");
                    int removeIndex = scanner.nextInt();
                    scanner.nextLine();
                    if (removeIndex >= 0 && removeIndex < employees.size()) {
                        employees.remove(removeIndex);
                    }
                    break;
                case 4:
                    System.out.println("1. Поиск по фамилии\n2. Поиск по возрасту\n3. Фильтр по первой букве фамилии");
                    int searchChoice = scanner.nextInt();
                    scanner.nextLine();
                    List<String> results = new ArrayList<>();
                    if (searchChoice == 1) {
                        System.out.println("Введите фамилию:");
                        String searchSurname = scanner.nextLine();
                        results = employees.stream().filter(emp -> emp.startsWith(searchSurname)).collect(Collectors.toList());
                    } else if (searchChoice == 2) {
                        System.out.println("Введите возраст:");
                        int searchAge = scanner.nextInt();
                        scanner.nextLine();
                        results = employees.stream().filter(emp -> emp.endsWith(" " + searchAge)).collect(Collectors.toList());
                    } else if (searchChoice == 3) {
                        System.out.println("Введите букву:");
                        String letter = scanner.nextLine();
                        results = employees.stream().filter(emp -> emp.startsWith(letter)).collect(Collectors.toList());
                    }
                    results.forEach(System.out::println);
                    System.out.println("Сохранить результаты? (да/нет)");
                    if (scanner.nextLine().equalsIgnoreCase("да")) {
                        Files.write(Paths.get("результат_поиска.txt"), results);
                    }
                    break;
                case 5:
                    employees.forEach(System.out::println);
                    break;
                case 6:
                    Files.write(Paths.get(file), employees);
                    return;
            }
        }
    }
}
