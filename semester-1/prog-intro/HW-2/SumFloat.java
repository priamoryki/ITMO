public class SumFloat {
    public static void main(String[] args) {
        float res = 0f;
        for (int i = 0; i < args.length; i++) {
            args[i] += " ";
            int st = 0;
            for (int j = 0; j < args[i].length(); j++) {
                if (Character.isWhitespace(args[i].charAt(j))) {
                    if (j > st) {
                        res += Float.parseFloat(args[i].substring(st, j));
                    }
                    st = j + 1;
                }
            }
        }
        System.out.println(res);
    }
}