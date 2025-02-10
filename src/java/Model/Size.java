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
    int size_id;
    String size_name;
    public Size(){
        
    }

    public Size(int size_id, String size_name) {
        this.size_id = size_id;
        this.size_name = size_name;
    }

    public int getSize_id() {
        return size_id;
    }

    public void setSize_id(int size_id) {
        this.size_id = size_id;
    }

    public String getSize_name() {
        return size_name;
    }

    public void setSize_name(String size_name) {
        this.size_name = size_name;
    }

    @Override
    public String toString() {
        return "Size{" + "size_id=" + size_id + ", size_name=" + size_name + '}';
    }
    
    
}
