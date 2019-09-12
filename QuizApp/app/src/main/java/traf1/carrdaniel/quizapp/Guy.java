package traf1.carrdaniel.quizapp;

public class Guy{
    public int eyeInd;
    public int mouthInd;
    public int colorInd;

    public Guy(int ei, int mi, int ci) {
        eyeInd = ei;
        mouthInd = mi;
        colorInd = ci;
    }

    public boolean equals(Guy g) {
        return eyeInd == g.eyeInd && mouthInd == g.mouthInd && colorInd == g.colorInd;
    }
}
