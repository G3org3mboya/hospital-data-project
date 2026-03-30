abstract class HospitalMember extends Person {
    public HospitalMember(String name, int age) {
        super(name, age);
    }

    public abstract void duty();
}
