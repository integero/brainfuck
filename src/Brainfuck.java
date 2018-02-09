import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by user on 18.02.2017.
 */
public class Brainfuck {
    public static HashMap<Integer, Integer> twoBrackets(String brainString) {
        // making brackets pair [ - ] & ] - [
        // open brackets
        ArrayList<Integer> oB = new ArrayList<>();
        // resulting pairs
        HashMap<Integer, Integer> result = new HashMap<>();
        int pOfoB = brainString.indexOf("[");
        // isn't open brackets
        if (pOfoB < 0) return null;
        oB.add(pOfoB);
        int clsBrPos=0;
        int opnBrPos=0;
        boolean clsBrSearch = true;
        while (true) {
            if (clsBrSearch) clsBrPos = brainString.indexOf("]", ++clsBrPos);
            // close bracket not found - break
            if (clsBrPos < 0) break;
            if (pOfoB >= 0) pOfoB = brainString.indexOf("[", ++pOfoB);
            // there isn't open brackets in brainString
            if (pOfoB < 0) {
                clsBrSearch = true;
                // adding pairs-positions [] & ][
                result.put(oB.get(oB.size() - 1), clsBrPos);
                result.put(clsBrPos, oB.get(oB.size() - 1));
                oB.remove(oB.size() - 1);
            } else if (pOfoB < clsBrPos) {
                clsBrSearch = false;
                oB.add(pOfoB);
            } else {
                // there is open bracket after close.
                clsBrSearch = true;
                opnBrPos = oB.get(oB.size() - 1);
                // adding pairs-positions with privious found [
                result.put(opnBrPos, clsBrPos);
                result.put(clsBrPos, opnBrPos);
                oB.remove(oB.size() - 1);
                oB.add(pOfoB);
            }
        }
        return result;
    }

    public static boolean pair(String brainString) {
        // check the correctness of the brackets
//        Pattern pattern = Pattern.compile("[^\\[\\]]");
//        Matcher matcher = pattern.matcher(brainString);
//        String result = Pattern.compile("[^\\[\\]]").matcher(brainString).replaceAll("");
//        String result = pattern.matcher(brainString).replaceAll("");
//        String result = matcher.replaceAll("");
//  retrieval all brackets for valid analyses
        char[] brck = Pattern.compile("[^\\[\\]]").matcher(brainString).replaceAll("").toCharArray();
//        char[] brck = result.toCharArray();
        int brCnt = 0;
        for (int i = 0; i < brck.length; i++) {
            if (((brck[i] == '[')?brCnt++:brCnt--)<0) break;
//            if ((brCnt=(brck[i] == '[')?brCnt++:brCnt--)<0) break;
//            if (brck[i] == '[') brCnt++;
//            else brCnt--;
//            if (brCnt < 0) break;
        }
        return brCnt == 0;
    }

    public static String brainfuck(String brainString) {
        String result = "";
        if (!pair(brainString)) return result;
        HashMap<Integer, Integer> bracketsPairs = twoBrackets(brainString);
        char[] brainChars = brainString.toCharArray();
        int[] jobArray = new int[200];
        int codPosition = 0, memoryPosition = 0;
        while (true) {
            switch (brainChars[codPosition]) {
                case '>':
                    memoryPosition++;
                    break;
                case '<':
                    memoryPosition--;
                    break;
                case '+':
                    jobArray[memoryPosition]++;
                    break;
                case '-':
                    jobArray[memoryPosition]--;
                    break;
                case '.':
                    result += Character.toString((char) jobArray[memoryPosition]);
                case ',':
                    break; // here must be input
                case '[':
                    if (jobArray[memoryPosition] == 0) codPosition = bracketsPairs.get(codPosition);
                    break;
                case ']':
                    if (jobArray[memoryPosition] != 0) codPosition = bracketsPairs.get(codPosition);
                    break;
                default:
            }
            codPosition++;
            if (codPosition >= brainChars.length) break;
        }
        return result;
    }

    public static void main(String[] args) {
        String st = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";
        if (pair(st)) System.out.println(brainfuck(st));

    }
}