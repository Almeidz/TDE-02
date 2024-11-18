package hospital;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciadorPacientes {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/pacientes.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Adicionar Paciente");
            System.out.println("2. Alterar Paciente");
            System.out.println("3. Excluir Paciente");
            System.out.println("4. Listar Pacientes");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1 -> adicionarPaciente(scanner);
                case 2 -> alterarPaciente(scanner);
                case 3 -> excluirPaciente(scanner);
                case 4 -> listarPacientes();
                case 5 -> {
                    System.out.println("Saindo...");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void adicionarPaciente(Scanner scanner) {
        System.out.print("Digite o número do paciente: ");
        int numero = scanner.nextInt();
        System.out.print("Digite o peso do paciente (kg): ");
        double peso = scanner.nextDouble();
        System.out.print("Digite a altura do paciente (m): ");
        double altura = scanner.nextDouble();

        paciente paciente = new paciente(numero, peso, altura);
        salvarPacienteNoArquivo(paciente);
        System.out.println("Paciente adicionado com sucesso!");
    }

    private static void alterarPaciente(Scanner scanner) {
        System.out.print("Digite o número do paciente a ser alterado: ");
        int numero = scanner.nextInt();

        List<paciente> pacientes = carregarPacientesDoArquivo();
        boolean encontrado = false;

        for (paciente p : pacientes) {
            if (p.getNumero() == numero) {
                System.out.print("Digite o novo peso do paciente (kg): ");
                double novoPeso = scanner.nextDouble();
                System.out.print("Digite a nova altura do paciente (m): ");
                double novaAltura = scanner.nextDouble();

                p.setPeso(novoPeso);
                p.setAltura(novaAltura);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            salvarTodosPacientesNoArquivo(pacientes);
            System.out.println("Paciente alterado com sucesso!");
        } else {
            System.out.println("Paciente não encontrado!");
        }
    }

    private static void excluirPaciente(Scanner scanner) {
        System.out.print("Digite o número do paciente a ser excluído: ");
        int numero = scanner.nextInt();

        List<paciente> pacientes = carregarPacientesDoArquivo();
        boolean encontrado = pacientes.removeIf(p -> p.getNumero() == numero);

        if (encontrado) {
            salvarTodosPacientesNoArquivo(pacientes);
            System.out.println("Paciente excluído com sucesso!");
        } else {
            System.out.println("Paciente não encontrado!");
        }
    }

    private static void listarPacientes() {
        List<paciente> pacientes = carregarPacientesDoArquivo();
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente encontrado!");
        } else {
            for (paciente p : pacientes) {
                p.listarPaciente();
            }
        }
    }

    private static void salvarPacienteNoArquivo(paciente paciente) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(paciente.toFileFormat());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao salvar paciente: " + e.getMessage());
        }
    }

    private static List<paciente> carregarPacientesDoArquivo() {
        List<paciente> pacientes = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo: " + e.getMessage());
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                pacientes.add(paciente.fromFileFormat(line));
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar pacientes: " + e.getMessage());
        }
        return pacientes;
    }

    private static void salvarTodosPacientesNoArquivo(List<paciente> pacientes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (paciente p : pacientes) {
                writer.write(p.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar pacientes: " + e.getMessage());
        }
    }
}