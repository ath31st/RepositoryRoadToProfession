package analyzer;

public class AlgorithmKMP {
    public static boolean KMPSearch(String pat, String txt)
    {
        int M = pat.length();
        int N = txt.length();

        int[] arr = new int[M];
        int j = 0; // for traversing through pattern

        computearrArray(pat, M, arr);

        int i = 0; // for traversing through text
        while (i < N) {
            if (pat.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
            }
            if (j == M) {
                //System.out.println("Found pattern "+ "at index " + (i - j));
                j = arr[j - 1];
                return true;
            }

            // mismatch occurs
            else if (i < N && pat.charAt(j) != txt.charAt(i)) {
                if (j != 0)
                    j = arr[j - 1];
                else
                    i = i + 1;
            }
        }
        return false;
    }

    static void computearrArray(String pat, int M, int[] arr)
    {
        int len = 0;
        int i = 1;
        arr[0] = 0; // arr[0] is always 0

        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                arr[i] = len;
                i++;
            }
            else // (pat[i] != pat[len])
            {
                if (len != 0) {
                    len = arr[len - 1];
                }
                else // if (len == 0)
                {
                    arr[i] = len;
                    i++;
                }
            }
        }
    }

//    public static void main(String args[])
//    {
//        String txt = "ABCXABCDABXABCDABCDABCY";
//        String pat = "ABCDABCY";
//        new AlgorithmKMP().KMPSearch(pat, txt);
//    }
}
