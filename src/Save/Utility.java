package Save;

import java.util.ArrayList;

public class Utility {
    public static int[][] To2Dint(ArrayList<Integer> list,int sizey, int sizex){
        int[][] newarr = new int[sizey][sizex];
        for (int j = 0; j<newarr.length; j++){
            for (int i = 0; i<newarr[j].length; i++){
                int index = j*sizey+i;
                newarr[j][i]= list.get(index);
            }
        }
        return newarr;

    }

    public static int[] To1Dint(int[][] arr){
        int[] oneArr = new int[arr.length * arr[0].length];

        for (int j = 0; j<arr.length; j++){
            for (int i = 0; i<arr[j].length; i++){
                int index = j * arr.length+i;
                oneArr[index]= arr[j][i];
            }
        }
        return oneArr;
    }

    public static int GetHypotenus(float x1, float y1, float x2, float y2){
        float xDiff = Math.abs(x1-x2);
        float yDiff = Math.abs(y1-y2);

        return (int) Math.hypot(xDiff, yDiff);
    }
}
