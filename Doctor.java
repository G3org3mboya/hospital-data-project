class Doctor extends HospitalMember implements Treatable {
    private String department;
    private String treatment;

    public Doctor(String name, int age, String department) {
        super(name, age);
        this.department = department;
        this.treatment = "None";
    }

    @Override
    public String getRole() {
        return "Doctor";
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public void duty() {
        System.out.println(getName() + " treating patients with " + treatment + " in " + department + " department");
    }

    @Override
    public void assignTreatment(String t) {
        treatment = t;
        System.out.println(getName() + " assigned treatment: " + t);
    }

    public String toCSV() {
        return getName() + "," + getAge() + "," + department + "," + treatment;
    }

    public static Doctor fromCSV(String csv) {
        String[] parts = csv.split(",", -1);
        Doctor d = new Doctor(parts[0], Integer.parseInt(parts[1]), parts[2]);
        if (parts.length > 3 && !parts[3].isEmpty()) {
            d.assignTreatment(parts[3]);
        }
        return d;
    }
}
