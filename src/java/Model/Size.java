package Model;

public class Size {

    private int sizeID;
    private int categoryID;
    private String sizeName;

    public Size() {
    }

    public Size(int sizeID, String sizeName, int categoryID) {
        this.sizeID = sizeID;
        this.sizeName = sizeName;
        this.categoryID = categoryID;
    }

    public Size(int sizeID, String sizeName) {
        this.sizeID = sizeID;
        this.sizeName = sizeName;
    }

    public int getSizeID() {
        return sizeID;
    }

    public void setSizeID(int sizeID) {
        this.sizeID = sizeID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    @Override
    public String toString() {
        return sizeName;  
    }
}
