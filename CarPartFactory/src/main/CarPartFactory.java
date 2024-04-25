package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import interfaces.List;
import interfaces.Map;
import interfaces.Stack;

public class CarPartFactory {
    private List<PartMachine> machines;	// Lista de maquinas de produccion de piezas de carro
    private List<Order> orders;		// Lista de pedidos de piezas
    private Map<Integer, CarPart> partCatalog;	// Catalogo de piezas
    private Map<Integer, List<CarPart>> inventory;	// Inventario de piezas
    private Map<Integer, Integer> defectives;	// Contador de piezas defectuosas
    private Stack<CarPart> productionBin;		// Bin de piezas producidas

    public CarPartFactory(String orderPath, String partsPath) throws IOException {
        machines = new ArrayList<>();	// Inicializa la lista de maquinas
        orders = new ArrayList<>();	// Inicializa la lista de pedidos
        partCatalog = new HashMap<>();	// Inicializa el catalogo de piezas
        inventory = new HashMap<>();	// Inicializa el inventario de piezas
        defectives = new HashMap<>();	// Inicializa el contador de piezas defectuosas
        productionBin = new Stack<>();	// Inicializa la pila de producción

        initializeFactory(orderPath, partsPath);
    }
    // Inicializa la fábrica con datos desde archivos
    private void initializeFactory(String orderPath, String partsPath) throws IOException {
        readOrdersFromFile(orderPath);	// Lee los pedidos desde un archivo
        readMachineDetailsFromFile(partsPath);
        preparePartCatalog();
        prepareInventory();
    }
 // Lee los pedidos desde un archivo
    private void readOrdersFromFile(String path) throws IOException {
    }

    private void readMachineDetailsFromFile(String path) throws IOException {

    }

    private void preparePartCatalog() {
        for (PartMachine machine : machines) {
            partCatalog.put(machine.getPart().getId(), machine.getPart());
        }
    }

    private void prepareInventory() {
        for (Integer partId : partCatalog.keySet()) {
            inventory.put(partId, new ArrayList<>());
            defectives.put(partId, 0);
        }
    }

    public void operateFactory(int days, int minutes) {
        for (int day = 0; day < days; day++) {
            conductDailyOperations(minutes);
        }
        finalizeOrders();
    }

    private void conductDailyOperations(int minutes) {
        for (int minute = 0; minute < minutes; minute++) {
            processProductionCycle();
        }
        transferProductionToInventory();
    }

    private void processProductionCycle() {
        for (PartMachine machine : machines) {
            CarPart part = machine.produceCarPart();
            if (part != null) {
                productionBin.push(part);
            }
        }
    }

    private void transferProductionToInventory() {
        while (!productionBin.isEmpty()) {
            CarPart part = productionBin.pop();
            int partId = part.getId();
            if (part.isDefective()) {
                defectives.put(partId, defectives.getOrDefault(partId, 0) + 1);
            } else {
                inventory.get(partId).add(part);
            }
        }
    }

    private void finalizeOrders() {
        for (Order order : orders) {
            if (canFulfillOrder(order)) {
                fulfillOrder(order);
            }
        }
    }

    private boolean canFulfillOrder(Order order) {
        // Check if all requested parts are available
        Map<Integer, Integer> requestedParts = order.getRequestedParts();
        for (Map.Entry<Integer, Integer> entry : requestedParts.entrySet()) {
            int partId = entry.getKey();
            int quantityNeeded = entry.getValue();
            List<CarPart> partsInInventory = inventory.getOrDefault(partId, new ArrayList<>());
            if (partsInInventory.size() < quantityNeeded) {
                return false;
            }
        }
        return true;
    }

    private void fulfillOrder(Order order) {
        Map<Integer, Integer> requestedParts = order.getRequestedParts();
        for (Map.Entry<Integer, Integer> entry : requestedParts.entrySet()) {
            int partId = entry.getKey();
            int quantityNeeded = entry.getValue();
            List<CarPart> partsInInventory = inventory.get(partId);
            for (int i = 0; i < quantityNeeded; i++) {
                partsInInventory.remove(0); // Remove parts from inventory
            }
        }
        order.setFulfilled(true);
    }

    public void generateFactoryReport() {
        String report = "Factory Report\n";
        report += "Produced Parts per Machine:\n";
        for (PartMachine machine : machines) {
            report += "Machine ID: " + machine.getId() + ", Parts Produced: " + machine.getTotalPartsProduced() + "\n";
        }
        report += "\nOrder Fulfillment Status:\n";
        for (Order order : orders) {
            report += "Order ID: " + order.getId() + ", Fulfilled: " + (order.isFulfilled() ? "Yes" : "No") + "\n";
        }
        System.out.println(report);
    }

}
