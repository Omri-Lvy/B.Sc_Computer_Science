public class Human {
    private String name;
    private Double age;
    private Double weight;

    public Human(String name, Double age, Double weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
