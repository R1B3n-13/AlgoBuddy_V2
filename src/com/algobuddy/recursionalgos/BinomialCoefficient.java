package com.algobuddy.recursionalgos;

import java.util.ArrayList;

/**
 *
 * @author nebir, nazrul
 */
public class BinomialCoefficient {

    private ArrayList<ArrayList<Integer>> ind1 = new ArrayList<>();
    ;
   private int par1[], val1[], lev1[];
    int k;

    int length = 500;

    int BC(int n, int k, int par) {
        int id = ind1.size();

        if (id >= 40) {
            return 0;
        }

        if (id >= 40) {
            return -1;
        }
        par1[id] = par;
        ArrayList<Integer> array = new ArrayList<>();
        array.add(n);
        array.add(k);

        if (par > -1) {
            lev1[id] = lev1[par] + 1;
        }
        int ii = 0;
        for (ArrayList<Integer> ar : ind1) {
            boolean f = true;
            for (int idx = 0; idx < ar.size(); idx++) {
                if (ar.get(idx) != array.get(idx)) {
                    f = false;
                    break;
                }
            }
            if (f) {
                ind1.add(array);
                return val1[id] = val1[ii];
            }
            ii++;
        }

        ind1.add(array);

        if (k == 0 || n == k) {
            return val1[id] = 1;
        }

        return val1[id] = BC(n - 1, k - 1, id) + BC(n - 1, k, id);

    }

    public BinomialCoefficient(int n, int k) {
        ind1 = new ArrayList<>();
        par1 = new int[length];
        val1 = new int[length];
        lev1 = new int[length];
        for (int i = 0; i < length; i++) {
            par1[i] = -1;
            val1[i] = 0;
            lev1[i] = 0;
        }
        k = BC(n, k, -1);
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
