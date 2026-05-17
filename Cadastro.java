import java.util.Scanner;

class Paciente {
    public int id;
    public String nome;
    public String cpf;
    public int idade;
    public String telefone;
    public String convenio;
    public boolean ativo;

    public Paciente(int id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.ativo = true;
    }

    public Paciente(int id, String nome, String cpf, int idade, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.telefone = telefone;
        this.ativo = true;
    }

    public Paciente(int id, String nome, String cpf, int idade, String telefone, String convenio) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.telefone = telefone;
        this.convenio = convenio;
        this.ativo = true;
    }

    public Paciente(Paciente outro) {
        this.id = outro.id;
        this.nome = outro.nome;
        this.cpf = outro.cpf;
        this.idade = outro.idade;
        this.telefone = outro.telefone;
        this.convenio = outro.convenio;
        this.ativo = outro.ativo;
    }

    public void ativar() {
        this.ativo = true;
        System.out.println("Paciente " + nome + " ativado.");
    }

    public void desativar() {
        this.ativo = false;
        System.out.println("Paciente " + nome + " desativado.");
    }

    public void complementar(int idade, String telefone) {
        this.idade = idade;
        this.telefone = telefone;
    }

    public void complementar(int idade, String telefone, String convenio) {
        this.idade = idade;
        this.telefone = telefone;
        this.convenio = convenio;
    }

    public void exibir() {
        System.out.println("ID: " + id
                + " | Nome: " + nome
                + " | CPF: " + cpf
                + " | Idade: " + (idade > 0 ? idade : "-")
                + " | Tel: " + (telefone != null ? telefone : "-")
                + " | Convenio: " + (convenio != null ? convenio : "Nenhum")
                + " | Status: " + (ativo ? "ATIVO" : "INATIVO"));
    }
}

class Profissional {
    public int id;
    public String nome;
    public String especialidade;
    public String registro;
    public double valorConsulta;
    public String[] diasDisponiveis = new String[7];
    public int totalDias;
    public boolean ativo;

    static String[] ESPECIALIDADES_VALIDAS = {"clinica geral", "fisioterapia", "psicologia", "nutricao"};

    public Profissional(int id, String nome, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.ativo = true;
        if (validarEspecialidade(especialidade)) {
            this.especialidade = especialidade;
        } else {
            System.out.println("Especialidade invalida. Use: clinica geral, fisioterapia, psicologia ou nutricao.");
            this.ativo = false;
        }
    }

    public Profissional(int id, String nome, String especialidade, String registro, double valor) {
        this.id = id;
        this.nome = nome;
        this.registro = registro;
        this.valorConsulta = valor;
        this.ativo = true;
        if (validarEspecialidade(especialidade)) {
            this.especialidade = especialidade;
        } else {
            System.out.println("Especialidade invalida.");
            this.ativo = false;
        }
    }

    public Profissional(int id, String nome, String especialidade, String registro, double valor, String[] dias) {
        this.id = id;
        this.nome = nome;
        this.registro = registro;
        this.valorConsulta = valor;
        this.ativo = true;
        if (validarEspecialidade(especialidade)) {
            this.especialidade = especialidade;
        } else {
            System.out.println("Especialidade invalida.");
            this.ativo = false;
        }
        for (int i = 0; i < dias.length && i < 7; i++) {
            this.diasDisponiveis[i] = dias[i];
            this.totalDias++;
        }
    }

    public Profissional(Profissional outro) {
        this.id = outro.id;
        this.nome = outro.nome;
        this.especialidade = outro.especialidade;
        this.registro = outro.registro;
        this.valorConsulta = outro.valorConsulta;
        this.ativo = outro.ativo;
        this.totalDias = outro.totalDias;
        for (int i = 0; i < outro.totalDias; i++) this.diasDisponiveis[i] = outro.diasDisponiveis[i];
    }

    public boolean validarEspecialidade(String esp) {
        for (String e : ESPECIALIDADES_VALIDAS) if (e.equalsIgnoreCase(esp)) return true;
        return false;
    }

    public void atualizar(String registro, double valor) {
        this.registro = registro;
        this.valorConsulta = valor;
    }

    public void atualizar(String registro, double valor, String[] dias) {
        this.registro = registro;
        this.valorConsulta = valor;
        this.totalDias = 0;
        for (int i = 0; i < dias.length && i < 7; i++) {
            this.diasDisponiveis[i] = dias[i];
            this.totalDias++;
        }
    }

    public boolean atendeDia(String dia) {
        for (int i = 0; i < totalDias; i++)
            if (diasDisponiveis[i].equalsIgnoreCase(dia)) return true;
        return false;
    }

    public void exibir() {
        System.out.print("ID: " + id
                + " | Dr(a). " + nome
                + " | " + especialidade
                + " | Reg: " + (registro != null ? registro : "-")
                + " | R$" + String.format("%.2f", valorConsulta)
                + " | Dias: ");
        if (totalDias == 0) System.out.print("-");
        else for (int i = 0; i < totalDias; i++) System.out.print(diasDisponiveis[i] + (i < totalDias - 1 ? ", " : ""));
        System.out.println(" | " + (ativo ? "ATIVO" : "INATIVO"));
    }
}

public class Cadastro {
    public static Paciente[] pacientes = new Paciente[100];
    public static int totalPacientes = 0;

    public static Profissional[] profissionais = new Profissional[50];
    public static int totalProfissionais = 0;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n========== CLINICA VIDAPLENA ==========");
            System.out.println("1 - Pacientes");
            System.out.println("2 - Profissionais");
            System.out.println("0 - Sair");
            System.out.print("Opcao: ");
            opcao = Integer.parseInt(sc.nextLine().trim());
            if (opcao == 1) menuPacientes();
            else if (opcao == 2) menuProfissionais();
        } while (opcao != 0);
        System.out.println("Sistema encerrado.");
    }

    static void menuPacientes() {
        int op;
        do {
            System.out.println("\n--- PACIENTES ---");
            System.out.println("1 - Cadastrar (nome + CPF)");
            System.out.println("2 - Cadastrar (nome + CPF + idade + telefone)");
            System.out.println("3 - Cadastrar completo (+ convenio)");
            System.out.println("4 - Complementar: idade + telefone");
            System.out.println("5 - Complementar: idade + telefone + convenio");
            System.out.println("6 - Listar todos");
            System.out.println("7 - Buscar por CPF");
            System.out.println("8 - Desativar paciente");
            System.out.println("9 - Ativar paciente");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine().trim());
            if (op == 1) cadastrarPacienteMinimo();
            else if (op == 2) cadastrarPacienteParcial();
            else if (op == 3) cadastrarPacienteCompleto();
            else if (op == 4) complementarSemConvenio();
            else if (op == 5) complementarComConvenio();
            else if (op == 6) listarPacientes();
            else if (op == 7) buscarPacienteCPF();
            else if (op == 8) desativarPaciente();
            else if (op == 9) ativarPaciente();
        } while (op != 0);
    }

    static void cadastrarPacienteMinimo() {
        System.out.print("Nome: "); String nome = sc.nextLine();
        System.out.print("CPF: "); String cpf = sc.nextLine();
        if (cpfExiste(cpf)) { System.out.println("ERRO: CPF ja cadastrado."); return; }
        pacientes[totalPacientes] = new Paciente(totalPacientes + 1, nome, cpf);
        totalPacientes++;
        System.out.println("Paciente cadastrado. ID: " + totalPacientes);
    }

    static void cadastrarPacienteParcial() {
        System.out.print("Nome: "); String nome = sc.nextLine();
        System.out.print("CPF: "); String cpf = sc.nextLine();
        if (cpfExiste(cpf)) { System.out.println("ERRO: CPF ja cadastrado."); return; }
        System.out.print("Idade: "); int idade = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Telefone: "); String tel = sc.nextLine();
        pacientes[totalPacientes] = new Paciente(totalPacientes + 1, nome, cpf, idade, tel);
        totalPacientes++;
        System.out.println("Paciente cadastrado. ID: " + totalPacientes);
    }

    static void cadastrarPacienteCompleto() {
        System.out.print("Nome: "); String nome = sc.nextLine();
        System.out.print("CPF: "); String cpf = sc.nextLine();
        if (cpfExiste(cpf)) { System.out.println("ERRO: CPF ja cadastrado."); return; }
        System.out.print("Idade: "); int idade = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Telefone: "); String tel = sc.nextLine();
        System.out.print("Convenio: "); String conv = sc.nextLine();
        pacientes[totalPacientes] = new Paciente(totalPacientes + 1, nome, cpf, idade, tel, conv);
        totalPacientes++;
        System.out.println("Paciente cadastrado. ID: " + totalPacientes);
    }

    static void complementarSemConvenio() {
        System.out.print("CPF do paciente: "); String cpf = sc.nextLine();
        Paciente p = buscarPorCPF(cpf);
        if (p == null) { System.out.println("Paciente nao encontrado."); return; }
        System.out.print("Idade: "); int idade = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Telefone: "); String tel = sc.nextLine();
        p.complementar(idade, tel);
        System.out.println("Cadastro complementado.");
    }

    static void complementarComConvenio() {
        System.out.print("CPF do paciente: "); String cpf = sc.nextLine();
        Paciente p = buscarPorCPF(cpf);
        if (p == null) { System.out.println("Paciente nao encontrado."); return; }
        System.out.print("Idade: "); int idade = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Telefone: "); String tel = sc.nextLine();
        System.out.print("Convenio: "); String conv = sc.nextLine();
        p.complementar(idade, tel, conv);
        System.out.println("Cadastro complementado com convenio.");
    }

    static void listarPacientes() {
        if (totalPacientes == 0) { System.out.println("Nenhum paciente cadastrado."); return; }
        for (int i = 0; i < totalPacientes; i++) pacientes[i].exibir();
    }

    static void buscarPacienteCPF() {
        System.out.print("CPF: "); String cpf = sc.nextLine();
        Paciente p = buscarPorCPF(cpf);
        if (p == null) System.out.println("Nao encontrado.");
        else p.exibir();
    }

    static void desativarPaciente() {
        System.out.print("CPF: "); String cpf = sc.nextLine();
        Paciente p = buscarPorCPF(cpf);
        if (p == null) System.out.println("Nao encontrado.");
        else p.desativar();
    }

    static void ativarPaciente() {
        System.out.print("CPF: "); String cpf = sc.nextLine();
        Paciente p = buscarPorCPF(cpf);
        if (p == null) System.out.println("Nao encontrado.");
        else p.ativar();
    }

    static void menuProfissionais() {
        int op;
        do {
            System.out.println("\n--- PROFISSIONAIS ---");
            System.out.println("1 - Cadastrar (nome + especialidade)");
            System.out.println("2 - Cadastrar (nome + esp + registro + valor + dias)");
            System.out.println("3 - Atualizar (registro + valor)");
            System.out.println("4 - Atualizar (registro + valor + dias)");
            System.out.println("5 - Listar todos");
            System.out.println("6 - Filtrar por especialidade");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine().trim());
            if (op == 1) cadastrarProfMinimo();
            else if (op == 2) cadastrarProfCompleto();
            else if (op == 3) atualizarProfSemDias();
            else if (op == 4) atualizarProfComDias();
            else if (op == 5) listarProfissionais();
            else if (op == 6) filtrarPorEspecialidade();
        } while (op != 0);
    }

    static void cadastrarProfMinimo() {
        System.out.print("Nome: "); String nome = sc.nextLine();
        System.out.print("Especialidade (clinica geral/fisioterapia/psicologia/nutricao): "); String esp = sc.nextLine();
        Profissional p = new Profissional(totalProfissionais + 1, nome, esp);
        if (p.ativo) { profissionais[totalProfissionais++] = p; System.out.println("Cadastrado. ID: " + p.id); }
    }

    static void cadastrarProfCompleto() {
        System.out.print("Nome: "); String nome = sc.nextLine();
        System.out.print("Especialidade: "); String esp = sc.nextLine();
        System.out.print("Registro profissional: "); String reg = sc.nextLine();
        System.out.print("Valor da consulta: "); double val = Double.parseDouble(sc.nextLine().trim());
        System.out.print("Quantos dias disponiveis? "); int qtd = Integer.parseInt(sc.nextLine().trim());
        String[] dias = new String[qtd];
        for (int i = 0; i < qtd; i++) { System.out.print("Dia " + (i + 1) + ": "); dias[i] = sc.nextLine(); }
        Profissional p = new Profissional(totalProfissionais + 1, nome, esp, reg, val, dias);
        if (p.ativo) { profissionais[totalProfissionais++] = p; System.out.println("Cadastrado. ID: " + p.id); }
    }

    static void atualizarProfSemDias() {
        System.out.print("ID do profissional: "); int id = Integer.parseInt(sc.nextLine().trim());
        Profissional p = buscarProfissional(id);
        if (p == null) { System.out.println("Nao encontrado."); return; }
        System.out.print("Registro: "); String reg = sc.nextLine();
        System.out.print("Valor consulta: "); double val = Double.parseDouble(sc.nextLine().trim());
        p.atualizar(reg, val);
        System.out.println("Atualizado.");
    }

    static void atualizarProfComDias() {
        System.out.print("ID do profissional: "); int id = Integer.parseInt(sc.nextLine().trim());
        Profissional p = buscarProfissional(id);
        if (p == null) { System.out.println("Nao encontrado."); return; }
        System.out.print("Registro: "); String reg = sc.nextLine();
        System.out.print("Valor consulta: "); double val = Double.parseDouble(sc.nextLine().trim());
        System.out.print("Quantos dias? "); int qtd = Integer.parseInt(sc.nextLine().trim());
        String[] dias = new String[qtd];
        for (int i = 0; i < qtd; i++) { System.out.print("Dia " + (i + 1) + ": "); dias[i] = sc.nextLine(); }
        p.atualizar(reg, val, dias);
        System.out.println("Atualizado com dias.");
    }

    static void listarProfissionais() {
        if (totalProfissionais == 0) { System.out.println("Nenhum profissional cadastrado."); return; }
        for (int i = 0; i < totalProfissionais; i++) profissionais[i].exibir();
    }

    static void filtrarPorEspecialidade() {
        System.out.print("Especialidade: "); String esp = sc.nextLine();
        boolean achou = false;
        for (int i = 0; i < totalProfissionais; i++) {
            if (profissionais[i].especialidade.equalsIgnoreCase(esp)) { profissionais[i].exibir(); achou = true; }
        }
        if (!achou) System.out.println("Nenhum profissional para essa especialidade.");
    }

    public static boolean cpfExiste(String cpf) {
        for (int i = 0; i < totalPacientes; i++)
            if (pacientes[i].cpf.equals(cpf)) return true;
        return false;
    }

    public static Paciente buscarPorCPF(String cpf) {
        for (int i = 0; i < totalPacientes; i++)
            if (pacientes[i].cpf.equals(cpf)) return pacientes[i];
        return null;
    }

    public static Profissional buscarProfissional(int id) {
        for (int i = 0; i < totalProfissionais; i++)
            if (profissionais[i].id == id) return profissionais[i];
        return null;
    }

    public static Profissional buscarProfissionalPorEspDia(String esp, String dia) {
        for (int i = 0; i < totalProfissionais; i++) {
            Profissional p = profissionais[i];
            if (p.especialidade.equalsIgnoreCase(esp) && p.atendeDia(dia) && p.valorConsulta > 0)
                return p;
        }
        return null;
    }
}
