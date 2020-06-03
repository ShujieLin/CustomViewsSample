package com.example.customviewsample;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestMethod {
    private static final String TAG = TestMethod.class.getSimpleName();


    @Test
    public void removeDuplicates(){
       int[] nums = new int[]{0,0,1,1,1,2,2,3,3,4};
       for (int i = 0;i < nums.length -1;i ++){
           if (nums[i] != nums[i + 1]){
//               Log.d(TAG,"removeDuplicates()" + "num = " + nums[i]);
               System.out.print(nums[i]);


           }
       }

        //假如最后一位和倒数第二位不同，则添加最后一位
               int indexLast = nums.length -1;
               if (nums[indexLast] != nums[indexLast -1]){
//                   Log.d(TAG,"removeDuplicates()" + "num = " + nums[indexLast]);
                    System.out.print(nums[indexLast]);
               }
    }




}


