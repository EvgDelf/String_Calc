
import java.util.Scanner;


public class Main {
    static String calcResult;
    static int operIndex=0;
    static int i;
    static Scanner sc = new Scanner(System.in);
    static String operation;
    static int operatorCount = 0;

    public static void main (String[]args) {
        calcResult = "";

        System.out.println("Пожалуйста, введите без пробелов выражение вида а+b или a-b, или a*b, или a/b, где " +
                "a и b являются только арабскими или только римскими числами от 1 до 10.");

        String input = sc.nextLine();
        System.out.println(Main.calc(input));
    }

    /** Метод принимает на вход строку, которую вводит пользователь. Проводится проверка
     * на наличие ошибок (количество операндов, содержание операндов, отличие результата от нуля,
     * допустимость операторов). При необходимости проводится перевод римских чисел в арабские.
     * После проведенных вычислений ответ выводится в консоль в виде строки. При использовании
     * римских чисел- ответ выводится в римских числах, при использовании арабских- в арабских.
     */
    public static String calc(String input) {


        // Проверка на количество операторов и операндов.
        for(Character c: input.toCharArray()){
            if(c=='-'||c=='+'||c=='*'||c=='/'){
                operation=Character.toString(c);
                operatorCount++;
                i=input.indexOf(operation);
                if (operatorCount > 1)
                    System.out.println("Формат математической операции не удовлетворяет условию:" +
                            "два операнда и один оператор (+, -, *, /)");
                if (operatorCount == 0)
                    System.out.println("Не является математической операцией");
                if(input.contains("+")){
                    operIndex=1;
                }if(input.contains("-")){
                    operIndex=2;
                }if(input.contains("*")){
                    operIndex=3;
                }if(input.contains("/")){
                    operIndex=4;
                }
            }

        }

        char [] ch=new char[input.length()];
        System.arraycopy(input.toCharArray(), 0, ch, 0, input.length());
        StringBuilder sb1=new StringBuilder();
        StringBuilder sb2=new StringBuilder();


        for(int g=0;g<i;g++){
            sb1.append(ch[g]);
        }

        for(int g=i+1;g<ch.length;g++){
                sb2.append(ch[g]);
        }

        String s2=new String(sb1);
        String s3=new String(sb2);

        // Если оба числа римские, то:
        if(Main.isRome(s2) & Main.isRome(s3)){
            if(Main.romeToArabian(s2)>0 & Main.romeToArabian(s2)<=10 &
                    Main.romeToArabian(s3)>0 & Main.romeToArabian(s3)<=10) {
                if (operIndex==1) {
                    i = Operations.PLUS.operation(Main.romeToArabian(s2), Main.romeToArabian(s3));
                    calcResult = Main.arabianToRome(i);
                }
                if (operIndex==2) {
                    i = Operations.MINUS.operation(Main.romeToArabian(s2), Main.romeToArabian(s3));
                    if(i<=0){
                        System.out.println("Не может быть отрицательного римского числа.");
                    }
                    calcResult = Main.arabianToRome(i);

                }
                if (operIndex==3) {
                    i = Operations.MULT.operation(Main.romeToArabian(s2), Main.romeToArabian(s3));
                    calcResult = Main.arabianToRome(i);
                }
                if (operIndex==4) {
                    i = Operations.DIV.operation(Main.romeToArabian(s2), Main.romeToArabian(s3));
                    calcResult = Main.arabianToRome(i);
                }
            }else{
                System.out.println("Недопустимое значение чисел.");
            }
            } else if(!Main.isRome(s2) & !Main.isRome(s3)){      //Если оба числа арабские

            int k=Integer.parseInt(s2);
            int l=Integer.parseInt(s3);
            if(k>0 & k<=10 &
                    l>0 & l<=10) {
                if (operIndex==1) {
                    i = Operations.PLUS.operation(k,l);
                    calcResult = Integer.toString(i);
                }
                if (operIndex == 2) {
                    i = Operations.MINUS.operation(k,l);
                    calcResult = Integer.toString(i);
                }if (operIndex == 3) {
                    i = Operations.MULT.operation(k,l);
                    calcResult = Integer.toString(i);
                }if (operIndex ==4) {
                    i = Operations.DIV.operation(k,l);
                    calcResult = Integer.toString(i);
                }
            }else{
                System.out.println("Недопустимое значение чисел.");
            }
        }else{
            System.out.println("Недопустимый формат чисел.");
        }


        return calcResult;
    }


    /**Перевод римских чисел в арабские.*/

    public static int romeToArabian(String rome) {
        int intResult = 0;
        int previousNumber;
        try {
            char[] romeNumber = rome.toUpperCase().toCharArray();
            previousNumber = romeToNumber(rome.charAt(0));
            int letterNumber;
            intResult +=romeToNumber(rome.charAt(0));
            if(romeNumber.length>1)
                for (int i = 1; i < romeNumber.length; i++) {
                    char c = romeNumber[i];
                    letterNumber = romeToNumber(c);
                    if (letterNumber <= previousNumber) {
                        intResult += letterNumber;
                        previousNumber=romeToNumber(c);
                    }
                    if (letterNumber > previousNumber) {
                        int j= 2*previousNumber;
                        letterNumber-=j;
                        intResult +=letterNumber;
                        previousNumber=romeToNumber(c);
                    }
                }
        } catch (Exception ex){
            System.out.println("Недопустимый формат ввода.");
        }
        return intResult;
    }

    /**"Словарь" для перевода римских чисел в арабские.*/
    public static int romeToNumber(char c) throws Exception {
        c=Character.toUpperCase(c);
        return switch (c) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            default -> throw new Exception("Недопустимый формат ввода.");
        };
    }


    /**Перевод арабских чисел в римские.
     */

    public static String arabianToRome(int number) {
        StringBuilder value = new StringBuilder();
        String[] romeNumber = new String[]{"C", "XC", "L", "XL", "X", "IX", "VIII",
                "VII", "VI", "V", "IV", "III", "II", "I"};
        int[] arabianNumber = new int[]{100, 90, 50, 40, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int iterat = 0;
        if (number > 0) {
            while (iterat < romeNumber.length) {
                while(number>=arabianNumber[iterat]) {
                    int i=number/arabianNumber[iterat];
                    number%=arabianNumber[iterat];
                    value.append((romeNumber[iterat]).repeat(i));
                }iterat++;
            }

            calcResult=value.toString().toUpperCase();
        }
        return calcResult;
    }


    /** Проверка числа на принадлежность к римским числам.
     */
    public static boolean isRome(String s){
        return s.chars().allMatch(Character::isLetter);
    }

    /**Возможные операции с числами.
     */
    enum Operations {
            PLUS {
                @Override
                int operation(int i, int j) {
                    return i + j;
                }
            }, MINUS {
                @Override
                int operation(int i, int j) {
                    return i - j;
                }
            }, MULT{
                @Override
                int operation(int i, int j) {
                    return i * j;
                }
            }, DIV {
                @Override
                int operation(int i, int j) {
                    if(i<j){
                        return 0;
                    }
                    return i / j;
                }
            };
            abstract int operation(int i, int j);
    }
}
