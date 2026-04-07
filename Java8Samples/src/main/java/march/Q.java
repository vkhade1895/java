package march;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Q {

    public static void main(String[] args) {

        longestSubstring();
        ReplaceStringWithHash();
    }

    private static void ReplaceStringWithHash() {
        String dictionary = "The Codility coders test";
        String message = "Codility tests the coders codes";

        String[] dictionaryString = dictionary.split(" ");
        String[] messageSplit = message.split(" ");

        Set<String> collected = Arrays.stream(dictionaryString).map(String::toLowerCase).collect(Collectors.toSet());
        System.out.println("collected data::" + collected);
//        Arrays.stream(messageSplit).forEach(e -> System.out.println("messages::" + e));
        for (int i = 0 ; i < messageSplit.length ; i ++){
            String currentWord = messageSplit[i];

            if(!collected.contains(currentWord.toLowerCase())){
                System.out.println("current word:" + currentWord);
                messageSplit[i] = "###";
            }
        }

        String joined = String.join(" ", messageSplit);
        System.out.println("joined::" + joined);
    }

    private static void longestSubstring() {
        int left = 0, right = 0, maxlength = 0 ;
        String str = "pwwkewq"; //
        Set<Character> mySet = new HashSet<>();

        while(right < str.length()){
            if (!mySet.contains(str.charAt(right))){
                System.out.println("right::" + right);
                mySet.add(str.charAt(right));
                maxlength = Math.max(maxlength, right-left +1);
                System.out.println("maxlength::"+ maxlength);
                right++;
            }else {
                System.out.println("left ::" + left);
                System.out.println("Removing char ::::"+ str.charAt(left));
                mySet.remove(str.charAt(left));
                left ++;
            }
        }

        System.out.println("longest substring::" + maxlength);
        System.out.println("mySet::::"+ mySet);

    }
}
