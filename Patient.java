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

    public String toCSV() {
        return getName() + "," + getAge() + "," + bill + "," + treatment;
    }

    public static Patient fromCSV(String csv) {
        String[] parts = csv.split(",", -1);
        Patient p = new Patient(parts[0], Integer.parseInt(parts[1]), Double.parseDouble(parts[2]));
        if (parts.length > 3 && !parts[3].isEmpty()) {
            p.assignTreatment(parts[3]);
        }
        return p;
    }
}
