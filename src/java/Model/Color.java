/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author phuan
 */
public class Color {
    int color_id;
    String color_Name;

    public Color(){
     
     };

    public Color(int color_id, String color_Name) {
        this.color_id = color_id;
        this.color_Name = color_Name;
    }

    public int getColor_id() {
        return color_id;
    }

    public void setColor_id(int color_id) {
        this.color_id = color_id;
    }

    public String getColor_Name() {
        return color_Name;
    }

    public void setColor_Name(String color_Name) {
        this.color_Name = color_Name;
    }

    @Override
    public String toString() {
        return "Color{" + "color_id=" + color_id + ", color_Name=" + color_Name + '}';
    }
   
    
}
