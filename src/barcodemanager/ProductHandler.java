package barcodemanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductHandler {
private ArrayList<Product> products;

    public ProductHandler() {
        this.products = new ArrayList<Product>();
        productStockUp();
    }
    public void listProducts(){
        System.out.println("Az adatbázisban jelenleg "+this.products.size()+" termék található.");
        if (this.products.size()>0) {
            for (int i = 0; i < this.products.size(); i++) {
                System.out.println(this.products.get(i).getBarcode()+" "+this.products.get(i).getName()+" "+this.products.get(i).getPrice());
            }
        }
    }
    public void addProduct(Product product){
        this.products.add(product);
        System.out.println(product.getName()+" nevű termék hozzáadva Ár: "+product.getPrice());
    }
    public void updateProduct(Product existingProduct, Product updatedProduct){
        this.products.set(findProduct(existingProduct), updatedProduct);
        System.out.println("Módosítás megtörént.\n"+
                           "Korábbi adatok:\t"+existingProduct.getBarcode()+" - Név: "+existingProduct.getName()+" Ár: "+existingProduct.getPrice()+
                           "\nÚj adatok\t"+updatedProduct.getBarcode()+" - Név: "+updatedProduct.getName()+" Ár: "+updatedProduct.getPrice());
    }
    public void removeProduct(Product product){
        this.products.remove(findProduct(product));        
        System.out.println("Név: "+product.getName()+" Ár: "+product.getPrice()+" - törölve az adatbázisból");
    }
    public Product queryProduct(String barcode){
        int position = findProduct(barcode);
        if (position>=0) {
            return this.products.get(position);     
        }
        return null;
    }
    private int findProduct(String barcode){
        for (int i = 0; i < this.products.size(); i++) {
            Product prod = this.products.get(i);
            if(prod.getBarcode().equals(barcode)){
                return i;
            }
        }
        return -1;
    }
    private int findProduct(Product product){
        return this.products.indexOf(product);
    }
    private void productStockUp(){
        File f = new File("src\\products.csv");
        try{ 
            Scanner input = new Scanner(f,"iso-8859-2");
            while(input.hasNextLine()){
                String line = input.nextLine();
                String[] datas = line.split(";");
                String barcode = datas[0];
                String name = datas[1];
                int price = Integer.parseInt(datas[2]);
                if(findProduct(barcode)<0){
                    products.add(Product.newProduct(barcode, name, price));
                }else{
                    System.out.println(barcode+" vonalkód már szerepel, "+name+" nem került felvételre az adatbázisba.");
                }
            }
        }catch(FileNotFoundException e){
            System.out.println("Hiba történt a beolvasáskor: " + e.getMessage()); 
            System.exit(1);
        }
    }
    private void outputProducts(){
        try{
        File f = new File("src\\products.csv");    
        FileOutputStream os = new FileOutputStream(f);
        OutputStreamWriter osw = new OutputStreamWriter(os,"iso-8859-2");
        for (int i = 0; i < this.products.size(); i++) {
            osw.write(this.products.get(i).getBarcode()+";");
            osw.write(this.products.get(i).getName()+";");
            osw.write(this.products.get(i).getPrice()+"\n");  
        }
        osw.flush();
        }catch (IOException e){
            System.out.println("Hiba történt a kiíráskor: " + e.getMessage()); 
            System.exit(1);
        }   
    }
    public void saveProducts(){
        outputProducts();
        System.out.println("\nAdatok sikeresen mentve");
    }
}
