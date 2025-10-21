package testers;

import java.util.HashMap;
import java.util.Map;

public class Test1 {

    public static void main(String[] args) {
        int a[] = {1,3,6,8,2};
        int two = a[0]; // assume 2nd highest array element
        int max = 0;

        for (int i = 1 ; i <a.length ; i++){
            if(a[i] > two){
                max = a[i];

            }
        }


        String test="This is is done DONE by VISHAL vishal";
        // op : is=2 , VISHAL=1 .....

        String[] strings = test.split(" ");
        Map<String, Integer> map = new HashMap<>();

        for (int i = 0 ; i< strings.length ; i ++){
            if (map.containsKey(strings[i])){

            }else{
                map.put(strings[i] , 1);
            }

        }
    }
}
