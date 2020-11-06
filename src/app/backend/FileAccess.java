package app.backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class FileAccess {
    private BufferedReader fileIn;

    public FileAccess(String fileName) {
        this.fileIn = getFile(fileName);
    }

    public BufferedReader getFile(String fileName) {
        BufferedReader fileIn = null;
        try {
            fileIn = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileIn;
    }

    public KeyValue getNextPair() {
        KeyValue pair = null;
        try {
            StringTokenizer line = new StringTokenizer(fileIn.readLine());
            String key = line.nextToken();
            String value = line.nextToken();
            while(line.hasMoreTokens()) {
                value += line.nextToken("\n");
            }
            pair = new KeyValue(key, value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pair;
    }
}
