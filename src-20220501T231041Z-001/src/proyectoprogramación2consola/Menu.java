/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoprogramación2consola;

public class Menu {
    // declaración de arreglos menus, etc
    public String comidas[]= {"Hamburguesa de pollo", "Hamburguesa de carne", "Hamburguesa doble de pollo", "Hamburguesa doble angus"};
    public double preciosC[]={2000,2500,3000,4000};
    public String bebidas[]= {"Coca Cola", "Frutas", "Mojitos","Limonadas", "Te frio"};
    public double preciosB[]={1000,800,900,750,850};
    public double totalB=0;
    public double totalC=0;
    
    
    
    public void sacarPrecios(int seleccion, double precio[],int cant){
        int a= seleccion-1;
        double total=0;
        total= (this.totalC)+ (precio[a]*cant);
        this.totalC=total;
   
    }  
    
    
    
    
}
