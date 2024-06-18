import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 3.1 - Inserir todos os funcionários
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("João", LocalDate.of(1988, 10, 15), new BigDecimal("4500.00"), "Analista"));
        funcionarios.add(new Funcionario("Maria", LocalDate.of(1985, 5, 23), new BigDecimal("6000.00"), "Gerente"));
        funcionarios.add(new Funcionario("Pedro", LocalDate.of(1990, 12, 7), new BigDecimal("3800.00"), "Desenvolvedor"));

        // 3.2 - Remover o funcionário "João" da lista
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // 3.3 - Imprimir todos os funcionários com formatação específica
        System.out.println("Funcionários:");
        funcionarios.forEach(funcionario -> System.out.println(funcionario));

        // 3.4 - Aplicar aumento de 10% no salário dos funcionários
        funcionarios.forEach(funcionario -> {
            BigDecimal aumento = funcionario.getSalario().multiply(new BigDecimal("0.10"));
            funcionario.setSalario(funcionario.getSalario().add(aumento));
        });

        // 3.5 - Agrupar os funcionários por função em um MAP
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();
        for (Funcionario funcionario : funcionarios) {
            String funcao = funcionario.getFuncao();
            if (!funcionariosPorFuncao.containsKey(funcao)) {
                funcionariosPorFuncao.put(funcao, new ArrayList<>());
            }
            funcionariosPorFuncao.get(funcao).add(funcionario);
        }

        // 3.6 - Imprimir os funcionários agrupados por função
        System.out.println("\nFuncionários agrupados por função:");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println(entry.getKey() + ":");
            for (Funcionario funcionario : entry.getValue()) {
                System.out.println("   " + funcionario);
            }
        }

        // 3.7 - Imprimir os funcionários que fazem aniversário nos meses 10 e 12
        System.out.println("\nFuncionários que fazem aniversário em outubro e dezembro:");
        for (Funcionario funcionario : funcionarios) {
            int mesAniversario = funcionario.getDataNascimento().getMonthValue();
            if (mesAniversario == 10 || mesAniversario == 12) {
                System.out.println(funcionario.getNome() + ": " +
                        funcionario.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        }

        // 3.8 - Imprimir o funcionário com maior idade
        System.out.println("\nFuncionário mais velho:");
        Funcionario maisVelho = Collections.min(funcionarios, Comparator.comparing(Funcionario::getDataNascimento));
        LocalDate hoje = LocalDate.now();
        long idadeMaisVelho = funcionarios.stream()
                .mapToLong(f -> f.getDataNascimento().until(hoje).getYears())
                .min()
                .orElseThrow(NoSuchElementException::new);
        System.out.println("Nome: " + maisVelho.getNome() + ", Idade: " + idadeMaisVelho);

        // 3.9 - Imprimir a lista de funcionários por ordem alfabética
        System.out.println("\nFuncionários em ordem alfabética:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(funcionario -> System.out.println(funcionario.getNome()));

        // 3.10 - Imprimir o total dos salários dos funcionários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal dos salários: " + totalSalarios.toString().replace('.', ','));

        // 3.11 - Imprimir quantos salários mínimos cada funcionário ganha
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\nSalários mínimos recebidos:");
        funcionarios.forEach(funcionario -> {
            BigDecimal salarioMinimoEquivalentes = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(funcionario.getNome() + ": " + salarioMinimoEquivalentes.setScale(2, BigDecimal.ROUND_HALF_UP));
        });
    }
}