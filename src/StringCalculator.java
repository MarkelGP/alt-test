import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение: ");
        String input = scanner.nextLine();

        try {
            System.out.println("Результат: " + '"'+calculate(input)+'"');
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calculate(String input) throws Exception {
        // Регулярное выражение для обработки операций
        String regex = "\"([^\"]{1,10})\"\\s*([+\\-*/])\\s*(\"([^\"]{1,10})\"|\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            throw new Exception("Неверный формат выражения");
        }

        String first = matcher.group(1);  // первая строка
        String operation = matcher.group(2);    // операция
        String secondOperand = matcher.group(3); // вторая
        String secondString = matcher.group(4); // вторая строка

        if (operation.equals("+")) {
            if (secondOperand.charAt(0) != '"') {
                throw new Exception("При сложении вторым аргументом должна быть строка");
            }
            return limit(first + secondString);

        } else if (operation.equals("-")) {
            if (secondOperand.charAt(0) != '"') {
                throw new Exception("При вычитании вторым аргументом должна быть строка");
            }
            return limit(first.replace(secondString, ""));

        } else if (operation.equals("*")) {
            int multiplier = Integer.parseInt(secondOperand);
            if (multiplier < 1 || multiplier > 10) {
                throw new Exception("Число должно быть от 1 до 10");
            }
            return limit(first.repeat(multiplier));

        } else if (operation.equals("/")) {
            int parseInt = Integer.parseInt(secondOperand);
            if (parseInt < 1 || parseInt > 10) {
                throw new Exception("Число должно быть от 1 до 10");
            }
            int newLength = first.length() / parseInt;
            return limit(first.substring(0, newLength));

        } else {
            throw new Exception("Неподдерживаемая операция");
        }
    }

    // Метод для ограничения длины строки до 40 символов
    public static String limit(String result) {
        if (result.length() > 40) {
            return result.substring(0, 40) + "...";
        }
        return result;
    }
}
