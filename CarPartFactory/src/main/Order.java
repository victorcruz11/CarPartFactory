package main;

import interfaces.Map;

public class Order {
    private int id;		// Identificador único de la orden
    private String customerName;		// Nombre del cliente que hizo la orden
    private Map<Integer, Integer> requestedParts;		// Mapa que contiene las partes solicitadas y sus cantidades
    private boolean fulfilled;		// Indica si el orden ha sido cumplido o no

    public Order() {}

 //constructor de la orden con parámetros iniciales
    public Order(int id, String customerName, Map<Integer, Integer> requestedParts, boolean fulfilled) {
        configureOrder(id, customerName, requestedParts, fulfilled);
    }

 //Metodo privado para configurar la orden con valores iniciales
    private void configureOrder(int id, String customerName, Map<Integer, Integer> requestedParts, boolean fulfilled) {
        this.id = id;		//inicializa el identificador de la orden
        validateCustomerName(customerName);		//valida y establece el nombre del cliente
        this.requestedParts = requestedParts;	//establece las partes solicitadas y sus cantidades
        this.fulfilled = fulfilled;		//establece el estado de cumplimiento de la orden
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public Map<Integer, Integer> getRequestedParts() {
        return requestedParts;
    }

    //Setter para establecer las partes solicitadas y sus cantidades
    public void setRequestedParts(Map<Integer, Integer> requestedParts) {
        if (requestedParts == null || requestedParts.isEmpty()) {
            throw new IllegalArgumentException("Requested parts cannot be null or empty");
        }
        this.requestedParts = requestedParts;
    }

    // Getter para obtener el nombre del cliente
    public String getCustomerName() {
        return customerName;
    }

    private void validateCustomerName(String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }
        this.customerName = customerName;
    }

 // Método para obtener una representacion en cadena de la orden
    @Override
    public String toString() {
        return "Order{" +
               "ID=" + id +
               ", Customer='" + customerName + '\'' +
               ", Requested Parts=" + requestedParts.size() +
               ", Status=" + (fulfilled ? "FULFILLED" : "PENDING") +
               '}';
    }
}
