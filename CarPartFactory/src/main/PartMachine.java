package main;

import java.util.LinkedList;
import data_structures.ListQueue;
import interfaces.Queue;

public class PartMachine {
    private int id;		//id unico de la maquina
    private CarPart part;	//pieza de la maquina
    private int period;		//periodo de la mquina
    private double weightError;		//error del peso de la maquina
    private int chanceOfDefective;		//probabilidad de piezas defectuosas
    private Queue<Integer> timer;		//temporizador de la maquina
    private Queue<CarPart> conveyorBelt;	//gestionar las piezas producidas por la máquina
    private int totalPartsProduced;		//numero total de piezas producidas por la maquina

    public PartMachine() {
    }
    // constructor de la máquina de piezas con parámetros iniciales
    public PartMachine(int id, CarPart part, int period, double weightError, int chanceOfDefective) {
        this.id = id;		// inicializa el identificador
        this.part = part;		//inicializa la pieza
        this.period = period;		//inicializa el periodo
        this.weightError = weightError;		//inicializa el error de peso
        this.chanceOfDefective = chanceOfDefective;		//inicializa la probabilidad de piezas defectuosas

        this.timer = new ListQueue<>();		// inicializa la cola de tiempo
        this.conveyorBelt = new ListQueue<>();		//// inicializa el queue de cinta transportadora

        initializeMachine(); 	//// llama a la función para configurar la máquina
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CarPart getPart() {
        return part;
    }

    public void setPart(CarPart part) {
        this.part = part;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public double getWeightError() {
        return weightError;
    }

    public void setWeightError(double weightError) {
        this.weightError = weightError;
    }

    public int getChanceOfDefective() {
        return chanceOfDefective;
    }

    public void setChanceOfDefective(int chanceOfDefective) {
        this.chanceOfDefective = chanceOfDefective;
    }

    public Queue<Integer> getTimer() {
        return timer;
    }

    public void setTimer(Queue<Integer> timer) {
        this.timer = timer;
    }

    public Queue<CarPart> getConveyorBelt() {
        return conveyorBelt;
    }

    public void setConveyorBelt(Queue<CarPart> conveyorBelt) {
        this.conveyorBelt = conveyorBelt;
    }

    public int getTotalPartsProduced() {
        return totalPartsProduced;
    }

    public void setTotalPartsProduced(int totalPartsProduced) {
        this.totalPartsProduced = totalPartsProduced;
    }
    
    // Método para inicializar la máquina con valores iniciales
    private void initializeMachine() {
        for (int i = 0; i < period; i++) {
            timer.enqueue(i);		//inicializa el queue de tiempo con valores iniciales
        }
        for (int i = 0; i < 10; i++) {
            conveyorBelt.enqueue(null);		//inicializa la cinta transportadora con espacios nulos
        }
        totalPartsProduced = 0;		//inicializa el contador de piezas producidas
    }

    //metodo para operar la maquina y avanzar en el tiempo
    public int operateMachine() {
        int time = timer.dequeue();		//obtiene el tiempo actual del queue de tiempo
        timer.enqueue(time);		//vuelve a poner el tiempo al final del queue
        processPartProduction(time);		//procesa la produccion de piezas basado en el tiempo
        return time;
    }

    // metodo para procesar la produccion de piezas segun el tiempo
    private void processPartProduction(int time) {
        if (time == 0) {
            CarPart newCarPart = createPart();		//crea una nueva pieza
            conveyorBelt.enqueue(newCarPart);		//agrega la pieza a la cinta transportadora
        } else {
            conveyorBelt.enqueue(null);		//agrega un espacio nulo a la cinta transportadora
        }
        conveyorBelt.dequeue();		//elimina el primer elemento de la cinta transportadora
    }

    private CarPart createPart() {
        double computedWeight = part.getWeight() + (Math.random() * 2 - 1) * weightError;
        boolean defective = isPartDefective();
        totalPartsProduced++;
        return new CarPart(part.getId(), part.getName(), computedWeight, defective);
    }

    private boolean isPartDefective() {
        return totalPartsProduced % chanceOfDefective == 0;
    }

    public LinkedList<CarPart> unloadConveyorBelt() {
        LinkedList<CarPart> unloadedParts = new LinkedList<>();
        while (!conveyorBelt.isEmpty()) {
            CarPart part = conveyorBelt.dequeue();
            if (part != null) {
                unloadedParts.add(part);
            }
        }
        return unloadedParts;
    }

    @Override
    public String toString() {
        return String.format("Machine ID: %d, Part Produced: %s, Total Produced: %d", 
                             id, part.getName(), totalPartsProduced);
        //devuelve una representacion de cadena de la informacion de la maquina y las piezas producidas
    }

    public void displayConveyorBelt() {
        StringBuilder beltDisplay = new StringBuilder();
        for (CarPart part : unloadConveyorBelt()) {
            beltDisplay.append(part != null ? "|P|" : "_");
            //construye una representacion de la cinta transportadora con "P" para piezas y "_" para espacios vacios
        }
        System.out.println("Machine [" + id + "] Conveyor Belt: " + beltDisplay);
     //muestra la representacion de la cinta transportadora en la consola
    }
}
