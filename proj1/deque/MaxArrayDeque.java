package deque;
import java.util.Comparator;
public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private Comparator<T> cmp;
    public MaxArrayDeque(Comparator<T> c){
        super();
        cmp = c;
    }
    public T max(){
        if(isEmpty()) {
            return null;
        }
        T max = this.get(0);
        for(T i : this){
            if(cmp.compare(i, max) > 0){
                max = i;
            }
        }
        return max;
    }
    public T max(Comparator<T> c){
        if(isEmpty()) {
            return null;
        }
        T max = this.get(0);
        for(T i : this){
            if(c.compare(i, max) > 0){
                max = i;
            }
        }
        return max;
    }

}
