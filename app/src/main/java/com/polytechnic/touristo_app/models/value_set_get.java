package com.polytechnic.touristo_app.models;

public class value_set_get {
    int pic;
    String txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9;

    public value_set_get(int pic, String txt1, String txt2) {
        this.pic = pic;
        this.txt1 = txt1;
        this.txt2 = txt2;

    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getTxt1() {
        return txt1;
    }

    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }

    public String getTxt2() {
        return txt2;
    }

    public void setTxt2(String txt2) {
        this.txt2 = txt2;
    }


}
