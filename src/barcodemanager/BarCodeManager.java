package barcodemanager;

import java.util.Scanner;

public class BarCodeManager {
    
    private static Scanner scanner= new Scanner(System.in);
    private static ProductHandler ph = new ProductHandler();
    
    public static void main(String[] args) {
        boolean quit = false;
        welcomeMessage();
        printOptions();
        while(!quit){
            System.out.println("\nVálasszon az opciók közül: (7 - Opciók listázása)");
            int action  = scanner.nextInt();
            scanner.nextLine();
            switch(action){
               case 0:
                   System.out.println("\nA program sikeresen leállt.");
                   quit=true;
                   break;
               case 1:
                   System.out.println("\nTerméklista: ");
                   ph.listProducts();
                   break;
               case 2:
                   addProduct();
                   break;
               case 3:
                   updateProduct();
                   break;
               case 4:
                   removeProduct();
                   break;
               case 5:
                   checkProduct();
                   break;
               case 6:
                   ph.saveProducts();
                   break;
               case 7:
                   printOptions();
                   break;
               default:
                   System.out.println("Érvénytelen opciót választott.");
           }
        }
    }
    private static void printOptions(){
        System.out.println("\nMenü:");
        System.out.println("0 - Program leállítása\n"+
                           "1 - Jelenlegi termékek listázása\n"+
                           "2 - Termék hozzáadás\n"+
                           "3 - Termék módosítás\n"+
                           "4 - Termék törlése\n"+
                           "5 - Termék lekérdezése vonalkód alapján\n"+
                           "6 - Terméklista mentése\n"+
                           "7 - Opciók listázása\n");
    }
    private static void welcomeMessage(){
        System.out.println("Üdvözli önt a termékmanager szoftver\n"+
                           "Írta: Bosskay Kristóf");
    }
    private static void addProduct(){
        System.out.println("\nAdja meg a vonalkódot:");
        String barcode=scanner.nextLine();
        if (ph.queryProduct(barcode)!=null) {
            System.out.println("Az adott vonalkód már a szerepel az adatbázisban.");
            return;
        }
        System.out.println("Adja meg a termék nevét:");
        String name=scanner.nextLine();
        System.out.println("Adja meg a termék árát:");
        int price= Integer.parseInt(scanner.nextLine());
        Product product=Product.newProduct(barcode, name, price);
        ph.addProduct(product);
    }
    private static void updateProduct(){
        System.out.println("\nAdja meg a módosítani kívánt termék vonalkódját:");
        String barcode=scanner.nextLine();
        Product existingProduct = ph.queryProduct(barcode);
        if (existingProduct==null) {
            System.out.println("A megadott vonalkód nem létezik.");
            return;
        }
        System.out.println("Adja meg a termék új nevét:");
        String name=scanner.nextLine();
        System.out.println("Adja meg a termék új árát:");
        int price=Integer.parseInt(scanner.nextLine());
        Product modifiedProduct = Product.newProduct(existingProduct.getBarcode(), name, price);
        ph.updateProduct(existingProduct, modifiedProduct);
    }
    private static void removeProduct(){
        System.out.println("\nAdja meg a törölni kívánt termék vonalkódját:");
        String barcode=scanner.nextLine();
        Product product = ph.queryProduct(barcode);
        if (product==null) {
            System.out.println("A megadott vonalkód nem létezik.");
            return;
        }
        ph.removeProduct(product);
    }
    private static void checkProduct(){
        System.out.println("\nAdja meg a keresett termék vonalkódját:");
        String barcode=scanner.nextLine();
        Product product = ph.queryProduct(barcode);
        if (product==null) {
            System.out.println("A megadott vonalkód nem létezik.");
            return;
        }
        System.out.println("\nA termék adatai\nNév: "+product.getName()+" Ár:"+product.getPrice());
    }
}
