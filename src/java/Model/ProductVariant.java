package Model;

public class ProductVariant {
    private int variantID;
    private Product product;
    private Size size;
    private Color color;
    private int stock;
    private String imageURL;

<<<<<<< HEAD
    int VariantID;
    Product product;
    Size size;
    Color color;
    int stock;
    String image;

    public ProductVariant() {
    };

    public ProductVariant(int VariantID, Product product, Size size, Color color, int stock, String image) {
        this.VariantID = VariantID;
=======
    public ProductVariant() {}

    public ProductVariant(int variantID, Product product, Size size, Color color, int stock, String imageURL) {
        this.variantID = variantID;
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
        this.product = product;
        this.size = size;
        this.color = color;
        this.stock = stock;
        this.imageURL = imageURL;
    }
    public ProductVariant(Product product, Size size, Color color, int stock, String imageURL) {
        this.product = product;
        this.size = size;
        this.color = color;
        this.stock = stock;
        this.imageURL = imageURL;
    }

    public int getVariantID() {
        return variantID;
    }

    public void setVariantID(int variantID) {
        this.variantID = variantID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
<<<<<<< HEAD

    @Override
    public String toString() {
        return "ProductVariant{" + "VariantID=" + VariantID + ", product=" + product + ", size=" + size + ", color=" + color + ", stock=" + stock + ", image=" + image + '}';
    }

=======
>>>>>>> 612670468b8e97480829caa20b45e30aafe3dc05
}
