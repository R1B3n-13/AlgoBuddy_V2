package com.algobuddy.recursionalgos;

import java.util.ArrayList;

/**
 *
 * @author nebir, nazrul
 */
public class Fibonacci {

    int m;
    private ArrayList<ArrayList<Integer>> ind1 = new ArrayList<>();
    ;
   private int par1[], val1[], lev1[];

    int length = 500;

    int fibo(int n, int par) {
        int id = ind1.size();

        if (id >= 40) {
            return -1;
        }

        par1[id] = par;
        ArrayList<Integer> array = new ArrayList<>();
        array.add(n);

        if (par > -1) {
            lev1[id] = lev1[par] + 1;
        }
        int ii = 0;
        for (ArrayList<Integer> ar : ind1) {

            for (int idx = 0; idx < ar.size(); idx++) {
                if (ar.get(idx) == array.get(idx)) {
                    ind1.add(array);
                    return val1[id] = val1[ii];
                }
            }
            ii++;
        }

        ind1.add(array);

        if (n < 2) {
            return val1[id] = n;
        }
        return val1[id] = fibo(n - 1, id) + fibo(n - 2, id);

    }
    int k;

    public Fibonacci(int n) {
        m = n + 5;
        ind1 = new ArrayList<>();
        par1 = new int[length];
        val1 = new int[length];
        lev1 = new int[length];
        for (int i = 0; i < length; i++) {
            par1[i] = -1;
            val1[i] = 0;
            lev1[i] = 0;
        }
        k = fibo(n, -1);
    }

    public ArrayList<ArrayList<Integer>> getId1() {
        return ind1;
    }

    public int[] getPa1() {
        return par1;
    }

    public int[] getLev1() {
        return lev1;
    }

    public int[] getV1() {
        return val1;
    }
}
