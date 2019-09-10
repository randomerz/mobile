package traf1.carrdaniel.quizapp;

public class Guy{
    public int eyeInd;
    public int mouthInd;

    public Guy(int ei, int mi) {
        eyeInd = ei;
        mouthInd = mi;
    }

    public boolean equals(Guy g) {
        return eyeInd == g.eyeInd && mouthInd == g.mouthInd;
    }
}
