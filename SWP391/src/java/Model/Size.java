/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author phuan
 */
public class Size {
    int Size_id;
    String Size_name;

    public Size(int Size_id, String Size_name) {
        this.Size_id = Size_id;
        this.Size_name = Size_name;
    }

    public int getSize_id() {
        return Size_id;
    }

    public void setSize_id(int Size_id) {
        this.Size_id = Size_id;
    }

    public String getSize_name() {
        return Size_name;
    }

    public void setSize_name(String Size_name) {
        this.Size_name = Size_name;
    }
    
}
