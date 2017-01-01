package app.com.example.android.stresstest;

/**
 * Created by aaquib on 24-Dec-16.
 */
public class RootGame {
    private int num;
    private double sqRoot;
    private double cbroot;

    public RootGame(int integer, Double root_Sq, Double root_Cb){

        num = integer;
        sqRoot = root_Sq;
        cbroot = root_Cb;
    }

    public double getSqRoot(){
        return sqRoot;
    }

    public double getCbroot(){
        return cbroot;
    }
}
