package gh2;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;
import deque.ArrayDeque;
/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class GuitarHero {
    public static final double CONCERT_A = 440.0;
    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        ArrayDeque<GuitarString> keys = new ArrayDeque<>();
        for(int i = 0; i < keyboard.length(); i++){
            double tmp = CONCERT_A * Math.pow(2, (i - 24) / 12.0);
            GuitarString string = new GuitarString(tmp);
            keys.addLast(string);
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                if (index >= 0 && index <37) {
                    keys.get(index).pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0;
            for(int i = 0;i < keyboard.length(); i++){
                sample += keys.get(i).sample();
            }
            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for(int i = 0;i < keyboard.length(); i++){
                keys.get(i).tic();
            }
        }
    }
}