package net.thumbtack.school.file;

import com.google.gson.Gson;
import net.thumbtack.school.colors.v3.ColorException;
import net.thumbtack.school.figures.v3.ColoredRectangle;
import net.thumbtack.school.figures.v3.Rectangle;
import net.thumbtack.school.ttschool.Trainee;
import net.thumbtack.school.ttschool.TrainingException;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileService {

    public static void  writeByteArrayToBinaryFile(String fileName, byte[] array) throws IOException{
        writeByteArrayToBinaryFile(new File(fileName),array);
    }

    public static void  writeByteArrayToBinaryFile(File file, byte[] array) throws IOException{
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)){
            fileOutputStream.write(array);
        }
    }

    public static byte[]  readByteArrayFromBinaryFile(String fileName) throws IOException{
        return readByteArrayFromBinaryFile(new File(fileName));
    }

    public static byte[]  readByteArrayFromBinaryFile(File file) throws IOException{
        byte[] byteArray = null;
        try (FileInputStream fileInputStream = new FileInputStream(file)){
            byteArray = new byte[(int) file.length()];
            fileInputStream.read(byteArray);
        }
        return byteArray;
    }

    public static byte[]  writeAndReadByteArrayUsingByteStream(byte[] array) throws IOException{
        byte[] byteArrayMemory = null;
        byte[] byteArray = null;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
            byteArrayOutputStream.write(array);
            byteArrayMemory = byteArrayOutputStream.toByteArray();
        }
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayMemory)) {
            int j = 0;
            byteArray = new byte[byteArrayMemory.length/2];
            for (int i = 0; i < byteArray.length; i++) {
                byteArray[i] = (byte) byteArrayInputStream.read();
                byteArrayInputStream.skip(1);
            }
        }
        return byteArray;
    }

    public static void  writeByteArrayToBinaryFileBuffered(String fileName, byte[] array) throws IOException{
        writeByteArrayToBinaryFileBuffered(new File(fileName), array);
    }

    public static void  writeByteArrayToBinaryFileBuffered(File file, byte[] array) throws IOException{
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file))){
            bufferedOutputStream.write(array);
        }
    }

    public static byte[] readByteArrayFromBinaryFileBuffered(String fileName) throws IOException{
        return readByteArrayFromBinaryFileBuffered(new File(fileName));
    }

    public static byte[] readByteArrayFromBinaryFileBuffered(File file)  throws IOException{
        byte[] byteArray = null;
        try (BufferedInputStream bufferedIntputStream = new BufferedInputStream (new FileInputStream(file))){
            byteArray = new byte[(int)file.length()];
            bufferedIntputStream.read(byteArray);
            } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return byteArray;
    }

    public static void  writeRectangleToBinaryFile(File file, Rectangle rect) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file))) {
            dataOutputStream.writeInt(rect.getTopLeft().getX());
            dataOutputStream.writeInt(rect.getTopLeft().getY());
            dataOutputStream.writeInt(rect.getBottomRight().getX());
            dataOutputStream.writeInt(rect.getBottomRight().getY());
        }
    }

    public static Rectangle  readRectangleFromBinaryFile(File file) throws IOException{
        Rectangle rect = null;
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file))){
            rect = new Rectangle(dataInputStream.readInt(),dataInputStream.readInt(),
                    dataInputStream.readInt(),dataInputStream.readInt());
        }
        return rect;
    }

    public static void  writeColoredRectangleToBinaryFile (File file, ColoredRectangle rect)  throws IOException{
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file))){
            dataOutputStream.writeInt(rect.getTopLeft().getX());
            dataOutputStream.writeInt(rect.getTopLeft().getY());
            dataOutputStream.writeInt(rect.getBottomRight().getX());
            dataOutputStream.writeInt(rect.getBottomRight().getY());
            dataOutputStream.writeUTF(rect.getColor().toString());
        }
    }

    public static ColoredRectangle  readColoredRectangleFromBinaryFile(File file) throws IOException, ColorException{
        ColoredRectangle rect = null;
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file))){
            rect = new ColoredRectangle(dataInputStream.readInt(),dataInputStream.readInt(),
                    dataInputStream.readInt(),dataInputStream.readInt(),dataInputStream.readUTF());
        }
        return rect;
    }

    public static void  writeRectangleArrayToBinaryFile(File file, Rectangle[] rects ) throws IOException{
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file))) {
            for (Rectangle rect:rects) {
                dataOutputStream.writeInt(rect.getTopLeft().getX());
                dataOutputStream.writeInt(rect.getTopLeft().getY());
                dataOutputStream.writeInt(rect.getBottomRight().getX());
                dataOutputStream.writeInt(rect.getBottomRight().getY());
            }
        }
    }

    public static Rectangle[]  readRectangleArrayFromBinaryFileReverse(File file) throws IOException{
        short n = (short) (file.length());
        short nr = (short) 16;
        Rectangle[] rects = new Rectangle[n/nr];
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r")){
            for (int i=0; i<n/nr;i++) {
                randomAccessFile.seek(n-(i+1)*nr);
                rects[i] = new Rectangle(randomAccessFile.readInt(),randomAccessFile.readInt(),
                        randomAccessFile.readInt(),randomAccessFile.readInt());
            }
        }
        return rects;
    }

    public static void  writeRectangleToTextFileOneLine(File file, Rectangle rect) throws IOException{
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)){
            String rectstr = rect.getTopLeft().getX() + " " + rect.getTopLeft().getY() + " " +
                    rect.getBottomRight().getX() + " " + rect.getBottomRight().getY();
            osw.write(rectstr);
        }
    }

    public static Rectangle  readRectangleFromTextFileOneLine(File file) throws IOException{
    	// REVU просто читайте одну строку с помощью BufferedReader и split
        // Да я понял, но можно сохранить как велосипедный образец
        Rectangle rectangle = null;
        try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)){
            char[] chars = new char[(int)file.length()];
            inputStreamReader.read(chars);
            int[] fields = new int[4];
            int j = 0;
            int k = 0;
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == ' ') {
                    fields[k] = Integer.parseInt(new String(chars,j,i-j));
                    j = i+1;
                    k++;
                }
                else if (i==chars.length-1) {
                    fields[k] = Integer.parseInt(new String(chars,j,i-j+1));
                }
            }
            rectangle = new Rectangle(fields[0],fields[1],fields[2],fields[3]);
        }
        return rectangle;
    }

    public static void  writeRectangleToTextFileFourLines(File file, Rectangle rect) throws IOException{
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))){
            bufferedWriter.write(String.valueOf(rect.getTopLeft().getX()));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(rect.getTopLeft().getY()));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(rect.getBottomRight().getX()));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(rect.getBottomRight().getY()));
        }
    }

    public static Rectangle  readRectangleFromTextFileFourLines(File file) throws IOException{
        Rectangle rectangle = null;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
            rectangle = new Rectangle(Integer.parseInt(bufferedReader.readLine()),Integer.parseInt(bufferedReader.readLine()),
                    Integer.parseInt(bufferedReader.readLine()),Integer.parseInt(bufferedReader.readLine()));
        }
        return rectangle;
    }

    public static void  writeTraineeToTextFileOneLine(File file, Trainee trainee) throws IOException{
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"))){
            bufferedWriter.write(trainee.getFullName());
            bufferedWriter.write(' ');
            bufferedWriter.write(String.valueOf(trainee.getRating()));
        }
    }

    public static Trainee  readTraineeFromTextFileOneLine(File file) throws IOException, TrainingException{
        Trainee trainee = null;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"))){
            String[] fields = bufferedReader.readLine().split(" ");
            trainee = new Trainee(fields[0],fields[1],Integer.parseInt(fields[2]));
        }
        return trainee;
    }

    public static void  writeTraineeToTextFileThreeLines(File file, Trainee trainee) throws IOException{
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"))){
            bufferedWriter.write(trainee.getFirstName());
            bufferedWriter.newLine();
            bufferedWriter.write(trainee.getLastName());
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(trainee.getRating()));
        }
    }

    public static Trainee  readTraineeFromTextFileThreeLines(File file) throws IOException, TrainingException{
        Trainee trainee = null;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),
                "UTF-8"))){
            trainee = new Trainee(bufferedReader.readLine(),bufferedReader.readLine(),
                    Integer.parseInt(bufferedReader.readLine()));
        }
        return trainee;
    }

    public static void  serializeTraineeToBinaryFile(File file, Trainee trainee) throws IOException{
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))){
            objectOutputStream.writeObject(trainee);
        }
    }

    public static Trainee  deserializeTraineeFromBinaryFile(File file) throws IOException, ClassNotFoundException{
        Trainee trainee = null;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))){
            trainee =(Trainee) objectInputStream.readObject();
        }
        return trainee;
    }

    public static String  serializeTraineeToJsonString(Trainee trainee) {
        Gson gson = new Gson();
        return gson.toJson(trainee);
    }

    public static Trainee  deserializeTraineeFromJsonString(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Trainee.class);
    }

    public static void  serializeTraineeToJsonFile (File file, Trainee trainee) throws IOException{
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            Gson gson = new Gson();
            gson.toJson(trainee, bw);
        }
    }

    public static Trainee  deserializeTraineeFromJsonFile (File file)  throws IOException{
        Trainee trainee = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            Gson gson = new Gson();
            trainee = gson.fromJson(br,Trainee.class);
        }
        return trainee;
    }

}
