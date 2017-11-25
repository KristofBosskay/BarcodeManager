package barcodemanager;

public class Product {
    private String barcode;
    private String name;
    private int price;

    public Product(String barcode, String name, int price) {
        this.barcode = barcode;
        this.name = name;
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
    
    public static Product newProduct(String barcode,String name,int price){
        return new Product(barcode,name,price);
    }
}
