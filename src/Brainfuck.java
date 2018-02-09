import java.util.*;
import java.util.regex.Pattern;


/**
 * Created by user on 18.02.2017.
 */
public class Brainfuck {
    public static HashMap<Integer, Integer> twoBrackets(String brainString) {
        // making brackets isBracketsWell [ - ] for jump forward & ] - [ for jump backward
        // open brackets
        ArrayList<Integer> opnB = new ArrayList<>();
        // resulting pairs
        HashMap<Integer, Integer> result = new HashMap<>();
        int posOfoB = brainString.indexOf("[");
        // there isn't open brackets
        if (posOfoB < 0) return null;
        opnB.add(posOfoB);
        int opnBrPos = 0;
        int clsBrPos = 0;
        boolean clsBrSearch = true;

        while (true) {
            if (clsBrSearch) clsBrPos = brainString.indexOf("]", ++clsBrPos);
            // close bracket not found - break
            if (clsBrPos < 0) break;

            if (posOfoB >= 0) posOfoB = brainString.indexOf("[", ++posOfoB);
            // there isn't open brackets in brainString
            if (posOfoB < 0) {
                clsBrSearch = true;
                // adding pairs-positions [] & ][
                result.put(opnB.get(opnB.size() - 1), clsBrPos);
                result.put(clsBrPos, opnB.get(opnB.size() - 1));
                opnB.remove(opnB.size() - 1);
            } else if (posOfoB < clsBrPos) {
                clsBrSearch = false;
                opnB.add(posOfoB);
            } else {
                // there is open bracket after close.
                clsBrSearch = true;
                opnBrPos = opnB.get(opnB.size() - 1);
                // adding pairs-positions with privious found [
                result.put(opnBrPos, clsBrPos);
                result.put(clsBrPos, opnBrPos);
                opnB.remove(opnB.size() - 1);
                opnB.add(posOfoB);
            }
        }
        return result;
    }

    private static Map<Integer, Integer> newTwo(String brainString) {
//  retrieval all brackets from brainString for valid analyses
        char[] brackets = Pattern.compile("[^\\[\\]]").matcher(brainString).replaceAll("").toCharArray();
        int brCnt = 0;
//      current brCnt must be non negative & at the end must be = 0
        for (int i = 0; i < brackets.length; i++)
            if (((brackets[i] == '[') ? brCnt++ : brCnt--) < 0) break;

        if (brCnt < 0) throw new IllegalArgumentException("Wrong set of brackets");

        Map<Integer, Integer> result = new HashMap<>();
        if (brackets.length==0) return result;      //  empty result
        Stack<Integer> opnBr = new Stack<>();
        int brcktsPos=-1;
        int tmp;
//      brackets sequance is well here. It is possible make pairs [] & }{
        for (int i = 0; i < brackets.length; i++) {
            brcktsPos = brainString.indexOf(brackets[i], ++brcktsPos);
            if (brackets[i] == '[')
                opnBr.push(brcktsPos);
            else {
                tmp = opnBr.pop();
                result.put(tmp, brcktsPos);
                result.put(brcktsPos,tmp );
            }
        }
        return result;
    }

    private static boolean isBracketsWell(String brainString) {
//  retrieval all brackets from brainString for valid analyses
        char[] brck = Pattern.compile("[^\\[\\]]").matcher(brainString).replaceAll("").toCharArray();
        int brCnt = 0;
        for (int i = 0; i < brck.length; i++)
//          current brCnt must be non negative & at the end must be = 0
            if (((brck[i] == '[') ? brCnt++ : brCnt--) < 0) break;
        return brCnt == 0;
    }

    public static String brainfuck(String brainString) {
        String result = "";
        if (!isBracketsWell(brainString)) return result;

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
                case '[':
                    if (jobArray[memPos] == 0) codPos = bracketsPairs.get(codPos);
                    break;
                case ']':
                    if (jobArray[memPos] != 0) codPos = bracketsPairs.get(codPos);
                    break;
                default:
            }
            codPos++;
            if (codPos >= brainChars.length) break;
        }
        return result;
    }

    public static void main(String[] args) {
        String st = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";
        if (isBracketsWell(st)) System.out.println(brainfuck(st));

    }
}