package bai6;

public class C extends B {
    public int x = 30;

    public void setXA(int val) {
        ((A) this).x = val;
    }

    public int getXA() {
        return ((A) this).x;
    }
}
