package hospital;

public class paciente {
    private int numero;
    private double peso;
    private double altura;

    public paciente(int numero, double peso, double altura) {
        this.numero = numero;
        this.peso = peso;
        this.altura = altura;
    }

    public int getNumero() {
        return numero;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public void listarPaciente() {
        System.out.println("Número: " + numero + ", Peso: " + peso + "kg, Altura: " + altura + "m");
    }

    public String toFileFormat() {
        return numero + "," + peso + "," + altura;
    }

    public static paciente fromFileFormat(String line) {
        String[] parts = line.split(",");
        return new paciente(Integer.parseInt(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
    }
}
