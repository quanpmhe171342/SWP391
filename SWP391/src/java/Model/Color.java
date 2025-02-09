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
    int Color_id;
    String Color_Name;

    public Color(int Color_id, String Color_Name) {
        this.Color_id = Color_id;
        this.Color_Name = Color_Name;
    }

    public int getColor_id() {
        return Color_id;
    }

    public void setColor_id(int Color_id) {
        this.Color_id = Color_id;
    }

    public String getColor_Name() {
        return Color_Name;
    }

    public void setColor_Name(String Color_Name) {
        this.Color_Name = Color_Name;
    }
    
}
