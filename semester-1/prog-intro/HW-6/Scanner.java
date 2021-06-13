import java.io.*;

public class Scanner {
    private BufferedReader reader;
    private char[] buffer = new char[1024];
    private int id = 0;
    private int inp = 0;
    public int numOfLines = 0;

    public Scanner(InputStream stream) {
        this.reader = new BufferedReader(new InputStreamReader(stream));
    }

    public Scanner(File file) {
        try {
            this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void close() {
        try {
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void read() {
        try {
            inp = reader.read(buffer);
            id = 0;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean hasNext() {
        while (id >= inp && inp != -1) {
            read();
        }
        return (inp > id);
    }

    public String next() {
        hasNext();
        StringBuilder next = new StringBuilder();
        while (id < inp) {
            if (Character.isLetter(buffer[id]) || Character.getType(buffer[id]) == Character.DASH_PUNCTUATION || buffer[id] == '\'') {
                next.append(buffer[id++]);
                if (id >= inp) {
                    read();
                }
            } else if (next.length() != 0) {
                break;
            } else {
                if (buffer[id] == '\n') {
                    numOfLines++;
                }
                id++;
                if (id >= inp && !hasNext()) {
                    break;
                }
            }
        }
        return next.toString();
    }
}