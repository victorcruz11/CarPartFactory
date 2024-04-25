package main;

public class CarPart {
    
    private int id;		// Identificador unico de la pieza de carro
    private String name;		// Nombre de la pieza de carro
    private double weight;		 // Peso de la pieza
    private boolean isDefective; // Indica si la pieza es defectuosa o no
    
    public CarPart() {
        this.isDefective = false; // Default value		// Valor predeterminado, la pieza no es defectuosa por defecto
    }

    public CarPart(int id, String name, double weight, boolean isDefective) {
        verifyAndSetDetails(id, name, weight);
        this.isDefective = isDefective;		// Establece si la pieza es defectuosa o no
    }

    private void verifyAndSetDetails(int id, String name, double weight) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        verifyName(name);
        this.name = name;
    }
    
    private void verifyName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
    }

    public double getWeight() {
        return weight;
    }
 // Setter para establecer el peso de la pieza con validacion
    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        this.weight = weight;
    }
    
 // Getter para verificar si la pieza es defectuosa o no
    public boolean isDefective() {
        return isDefective;
    }

    public void setDefective(boolean isDefective) {
        this.isDefective = isDefective;
    }

    @Override
    public String toString() {
        return "CarPart: ID=" + id + ", Name='" + name + "', Weight=" + weight + ", Defective=" + isDefective;
    }
}
