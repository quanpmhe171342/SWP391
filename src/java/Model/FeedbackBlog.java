/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class FeedbackBlog {

    Blog b;
    Feedback fb;

    public FeedbackBlog(Blog b, Feedback fb) {
        this.b = b;
        this.fb = fb;
    }

    public Blog getB() {
        return b;
    }

    public void setB(Blog b) {
        this.b = b;
    }

    public Feedback getFb() {
        return fb;
    }

    public void setFb(Feedback fb) {
        this.fb = fb;
    }

    @Override
    public String toString() {
        return "FeedbackBlog{" + "b=" + b + ", fb=" + fb + '}';
    }

}
