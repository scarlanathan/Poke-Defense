package Save;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import object.PathPoint;

public class LoadSave {
    public static BufferedImage getSpritesMinions(){
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("Image/spritepokemons.png");
        try{
            img = ImageIO.read(is);
        }catch(IOException e){
            e.printStackTrace();
        }
    return img;
    }


    //Create File.txt
    public static void CreateFile(){
        File txtFile = new File("src/Image/testTextFile.txt");

        try{
            txtFile.createNewFile();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void WriteFile(File f, int[] arr,PathPoint start,PathPoint end){

        try{
            PrintWriter pw = new PrintWriter(f);
            for(Integer i : arr){
                pw.println(i);
            }
            pw.println(start.getxCord());
            pw.println(start.getyCord());
            pw.println(end.getxCord());
            pw.println(end.getyCord());
            pw.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private static ArrayList<Integer> ReadFile(File file){
        ArrayList<Integer> list = new ArrayList<>();
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                list.add(Integer.parseInt(sc.nextLine()));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void newLevel(String name, int[] arr){
        File newLevel = new File("src/Image/"+name+".txt");
        if(newLevel.exists()){
            System.out.println("File "+name+" already exist");
        }else{
            try{
                newLevel.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            WriteFile(newLevel, arr,new PathPoint(0, 0),new PathPoint(0, 0));
        }
    }

    public static void saveLevel(String name, int[][] idArr,PathPoint start,PathPoint end){
        File levelFile = new File("src/Image/"+name+".txt");
        if(levelFile.exists()){
            WriteFile(levelFile,Utility.To1Dint(idArr),start,end);
        }else{
            System.out.println("File "+name+" doesn't exist.");
            return;
        }
    }

    public static ArrayList<PathPoint> GetLevelPathPoints(String name){
        File lvlFile = new File("src/Image/"+name+".txt");

        if(lvlFile.exists()){
            ArrayList<Integer> list = ReadFile(lvlFile);
            ArrayList<PathPoint> points = new ArrayList<>();

            points.add(new PathPoint(list.get(400), list.get(401)));
            points.add(new PathPoint(list.get(402), list.get(403)));

            return points;
        }else{
            System.out.println("File "+name+"doesn't exist");
            return null;
        }
    }

    public static int[][] GetLvlData(String name){
        File lvlFile = new File("src/Image/"+name+".txt");
        if(lvlFile.exists()){
            ArrayList<Integer> list = ReadFile(lvlFile);
            return Utility.To2Dint(list, 20, 20);
        }else{
            System.out.println("File "+name+"doesn't exist");
            return null;
        }
    }
}
