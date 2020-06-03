package com.example.javaproject;

import java.awt.SystemTray;
import java.lang.reflect.Array;
import java.util.ArrayList;

import sun.rmi.runtime.Log;

public class MyClass {
    public static void main(String[] args) {
//        System.out.print("helloworld");
//        System.out.print(2 + 3453);


        removeDuplicates2(new int[]{0,0,1,1,1,2,2,3,3,4,55,100});
//        removeDuplicates2(new int[]{0,0,1,2});
        replaceArrFirstNum();
    }




    /**
     *         给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
     *
     *         不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     *
     *         例如：
     *         nums = [0,0,1,1,1,2,2,3,3,4]
     *
     *         思路：
     *         n和n+1对比，假如相同，则去掉n，数组长度-1
     */
    private static void removeDuplicates2(int[] nums) {
//        0,0,1,2
        int len = nums.length;
        for (int i = 0;i < len - 1;i ++){
            if (nums[i] == nums[i + 1]){
                //重组数组
                len --;
                for (int j = 0;j < len;j ++){
                    nums[j] = nums[j + 1];
                    System.out.print("新的数组 = " + nums[j] + " len = " + len + "\n");
                }

            }

        }

//        for (int num : nums){
//            System.out.print("int[] nums = " + num + "\n");
//        }
//        System.out.print("len = " + len + "\n");


    }


    /**
     * 替换数组
     * 去掉第一个数
     */
    private static void replaceArrFirstNum(){
        int[] arr = new int[]{0,0,1,2};
        int len = arr.length;
        for (int i = 0;i < len;i ++){
            arr[i] = arr[i + 1];
            len --;
            i --;
        }
        for (int num : arr){
            System.out.print("replaceArrFirstNum()" + "num = " + num + "\n");
        }


    }


    private static void removeDuplicates(){
        int[] nums = new int[]{0,0,1,1,1,2,2,3,3,4,55,100};//1 111
        int length = 0;
        int[] flagIndex;
        for (int i = 0;i < nums.length - 1;i ++){
            if (nums[i] != nums[i + 1]){
                length ++;
            }else {
                //remove nums[i]

            }

        }
        System.out.print(length + 1);
    }








}
