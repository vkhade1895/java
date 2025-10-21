package testers;

import java.util.HashMap;
import java.util.Map;

public class Aumni {

    public static void main(String[] args) {
//        Input: nums = [2,7,11,15], target = 9
//        Output: [0,1]
//        Explanation: Because nums[0] + nums[1] == 9, we return [0, 1]
        int[] numArray = {2,7,11,15};
        int target = 9;
        Map<Integer, Integer> map= new HashMap<>();

        for (int i=0; i<= numArray.length ; i++){
         map.put(numArray[i], i);
        }

        for (int i = 0; i <= map.size() ; i++){
            if (map.get(i) + map.get(i+1) == target ){

            }
        }


    }
}
