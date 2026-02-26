
abstract class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    public abstract String getRole();
}

abstract class HospitalMember extends Person {
    public HospitalMember(String name, int age) {
        super(name, age);
    }

    public abstract void duty();
}


interface Treatable {
    void assignTreatment(String treatment);
}

interface Billable {
    double billAmount();
}

class Patient extends Person implements Treatable, Billable {
    private String treatment;
    private double bill;

    public Patient(String name, int age, double bill) {
        super(name, age);
        this.bill = bill;
        treatment = "None";
    }

    @Override
    public String getRole() {
        return "Patient";
    }

    @Override
    public void assignTreatment(String t) {
        treatment = t;
        System.out.println(getName() + " assigned treatment: " + t);
    }

    @Override
    public double billAmount() {
        return bill;
    }
}

class Doctor extends HospitalMember implements Treatable {
    private String department;
    private String treatment;

    public Doctor(String name, int age, String department) {
        super(name, age);
        this.department = department;
        treatment = "None";
    }

    @Override
    public String getRole() {
        return "Doctor";
    }

    @Override
    public void duty() {
        System.out.println(getName() + " treating patients with " 
                           + treatment + " in " + department + " department");
    }

    @Override
    public void assignTreatment(String t) {
        treatment = t;
        System.out.println(getName() + " assigned treatment: " + t);
    }
}

public class Main {
    public static void main(String[] args) {

        Patient p1 = new Patient("George", 19, 3000);
        Patient p2 = new Patient("Shem", 20, 4500);
        Doctor d1 = new Doctor("Dr. Oduor", 55, "Cardiology");

        System.out.println("=== HOSPITAL SYSTEM ===");
        System.out.println("Welcome " + p1.getName() + " (" + p1.getRole() + ")");
        System.out.println("Welcome " + p2.getName() + " (" + p2.getRole() + ")");
        System.out.println("Welcome " + d1.getName() + " (" + d1.getRole() + ")");

        System.out.println("\n=== HOSPITAL ACTIONS ===");
        p1.assignTreatment("Checkup");
        p2.assignTreatment("X-Ray");

        System.out.println(p1.getName() + " bill: $" + p1.billAmount());
        System.out.println(p2.getName() + " bill: $" + p2.billAmount());

        d1.assignTreatment("Heart Surgery");
        d1.duty();
    }
}