package com.algobuddy.recursionalgos;

import java.util.ArrayList;

/**
 *
 * @author nebir, nazrul
 */
public class LCS {

    private ArrayList<ArrayList<Integer>> ind1 = new ArrayList<>();
    ;
   private int par1[], val1[], lev1[];
    int k;
    private String a, b;

    int length = 500;

    int lcs(int i1, int j1, int par) {

        int id = ind1.size();

        if (id >= 40) {
            return 0;
        }

        if (id >= 40) {
            return -1;
        }
        par1[id] = par;
        ArrayList<Integer> array = new ArrayList<>();
        array.add(i1);
        array.add(j1);

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

        if (i1 == a.length() || j1 == b.length()) {
            return val1[id] = 0;
        }
        if (a.toCharArray()[i1] == b.toCharArray()[j1]) {
            return val1[id] = (1 + lcs(i1 + 1, j1 + 1, id));
        }

        return val1[id] = Math.max(lcs(i1 + 1, j1, id), lcs(i1, j1 + 1, id));

    }

    public LCS(String a, String b, int i1, int j1) {
        this.a = a;
        this.b = b;
        ind1 = new ArrayList<>();
        par1 = new int[length];
        val1 = new int[length];
        lev1 = new int[length];
        for (int i = 0; i < length; i++) {
            par1[i] = -1;
            val1[i] = 0;
            lev1[i] = 0;
        }
        k = lcs(i1, j1, -1);
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
