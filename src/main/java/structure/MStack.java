package structure;

import java.util.Arrays;

/**
 * 栈的使用: 回文
 */
public class MStack<T> {

    public int top = 0;
    public Object[] datas;


    public MStack(int length) {
        datas = new Object[length];
    }

    @Override
    public String toString() {
        return "MStack{" +
                "top=" + top +
                ", datas=" + Arrays.toString(datas) +
                '}';
    }

    public void push(T element) {
        if (top < datas.length - 1) {
            top++;
            datas[top] = element;
        }
    }
}
