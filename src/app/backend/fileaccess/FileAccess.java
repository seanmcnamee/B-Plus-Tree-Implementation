package app.backend.fileaccess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.StringTokenizer;

public class FileAccess {
    private BufferedReader fileIn;
    private PrintWriter fileOut;
    private String fileName;

    public FileAccess(String fileName) {
        this.fileName = fileName;
        this.fileIn = getFileReader(fileName);
    }

    private BufferedReader getFileReader(String fileName) {
        BufferedReader fileIn = null;
        try {
            fileIn = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileIn;
    }

    public PrintWriter getFileWriter(String fileName) {
        PrintWriter easyOut = null;
        try {
            Writer out = new BufferedWriter(new FileWriter(fileName));
            easyOut = new PrintWriter( out );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return easyOut;
    }

    /**
     * @return if you can get the next line, and if you can't closes it automatically
     */
    public boolean hasNext() {
        boolean canGetNext = false;
        try {
            canGetNext = fileIn.ready();
            if (!canGetNext) {
                fileIn.close();
                System.out.println("Closed file reader");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return canGetNext;
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

    /**
     * Write the data to the file. If toWrite is null, it closes the file.
     */
    public void write(String toWrite) {
        if (this.fileOut == null) {
            this.fileOut = getFileWriter(fileName);
        }
        if (toWrite == null) {
            this.fileOut.close();
        } else {
            this.fileOut.println(toWrite);
        }
    }
}
