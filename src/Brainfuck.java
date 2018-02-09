import java.util.*;
import java.util.regex.Pattern;

public class Brainfuck {
//  preparing pairs-position for paired brackets [] & ][
    private static Map<Integer, Integer> newTwo(String brainString) {
//      source validation for allowable operand
        if (Pattern.compile("[^\\[\\]+-><.,]").matcher(brainString).replaceAll("").length()!=brainString.length())
            throw new IllegalArgumentException("Wrong symbols in source cod");

//      retrieval all brackets from brainString for valid analyses
        char[] brackets = Pattern.compile("[^\\[\\]]").matcher(brainString).replaceAll("").toCharArray();
        int brCnt = 0;
//      current brCnt must be non negative & at the end must bye = 0
        for (int i = 0; i < brackets.length; i++)
            if (((brackets[i] == '[') ? brCnt++ : brCnt--) < 0) break;

        if (brCnt < 0) throw new IllegalArgumentException("Wrong set of brackets");

        Map<Integer, Integer> result = new HashMap<>();
        if (brackets.length==0) return result;      //  empty result
        Stack<Integer> opnBr = new Stack<>();
        int brcktsPos=-1;
        int tmp;
//      brackets sequence is well here. It is possible make pairs [] & }{
        for (int i = 0; i < brackets.length; i++) {
            brcktsPos = brainString.indexOf(brackets[i], ++brcktsPos);
            if (brackets[i] == '[')
                opnBr.push(brcktsPos);
            else {
                tmp = opnBr.pop();
                result.put(tmp, brcktsPos);     //  [] positions
                result.put(brcktsPos,tmp );     //  ][ positions
            }
        }
        return result;
    }

    public static String brainfuck(String brainString) {
        String result = "";
//        Map<Integer, Integer> bracketsPairs = twoBrackets(brainString);
        Map<Integer, Integer> bracketsPairs = newTwo(brainString);
        char[] brainChars = brainString.toCharArray();
        int[] jobArray = new int[200];
        int codPos = 0;
        int memPos = 0;
        while (true) {
            switch (brainChars[codPos]) {
                case '>':
                    memPos++;
                    break;
                case '<':
                    memPos--;
                    break;
                case '+':
                    jobArray[memPos]++;
                    break;
                case '-':
                    jobArray[memPos]--;
                    break;
                case '.':
                    result += Character.toString((char) jobArray[memPos]);
                case ',':
                    break; // here must be input
                default:{
                    if (((brainChars[codPos] == '[') && (jobArray[memPos] == 0))||((brainChars[codPos] == ']') && (jobArray[memPos] != 0)))
                        codPos = bracketsPairs.get(codPos);
                }
            }
            codPos++;
            if (codPos >= brainChars.length) break;
        }
        return result;
    }

    public static void main(String[] args) {
        String st = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";
        System.out.println(brainfuck(st));
    }
}