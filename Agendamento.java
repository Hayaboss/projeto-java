import java.util.Scanner;

class Consulta {
    public int id;
    public Paciente paciente;
    public Profissional profissional;
    public String data;
    public String hora;
    public String tipo;
    public String status;

    public Consulta(int id, Paciente paciente, Profissional profissional, String data, String hora) {
        this.id = id;
        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
        this.hora = hora;
        this.tipo = "consulta inicial";
        this.status = "agendada";
    }

    public Consulta(int id, Paciente paciente, Profissional profissional, String data, String hora, String tipo) {
        this.id = id;
        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
        this.hora = hora;
        this.tipo = tipo;
        this.status = "agendada";
    }

    public Consulta(int id, Paciente paciente, Profissional profissional, String data, String hora, String tipo, String status) {
        this.id = id;
        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
        this.hora = hora;
        this.tipo = tipo;
        this.status = status;
    }

    public Consulta(int novoId, Consulta outra) {
        this.id = novoId;
        this.paciente = outra.paciente;
        this.profissional = outra.profissional;
        this.data = outra.data;
        this.hora = outra.hora;
        this.tipo = outra.tipo;
        this.status = "agendada";
    }

    public void exibir() {
        System.out.println("ID: " + id
                + " | Paciente: " + paciente.nome
                + " | Dr(a). " + profissional.nome
                + " | " + data + " " + hora
                + " | Tipo: " + tipo
                + " | Status: " + status);
    }
}

class Atendimento {
    public int id;
    public Consulta consulta;
    public String observacoes;
    public String diagnostico;
    public String[] procedimentos = new String[10];
    public int totalProcedimentos;

    public Atendimento(int id, Consulta consulta) {
        this.id = id;
        this.consulta = consulta;
        this.totalProcedimentos = 0;
    }

    public void registrar(String observacoes) {
        this.observacoes = observacoes;
        this.consulta.status = "realizada";
    }

    public void registrar(String observacoes, String diagnostico) {
        this.observacoes = observacoes;
        this.diagnostico = diagnostico;
        this.consulta.status = "realizada";
    }

    public void registrar(String observacoes, String diagnostico, String[] procs) {
        this.observacoes = observacoes;
        this.diagnostico = diagnostico;
        for (int i = 0; i < procs.length && totalProcedimentos < 10; i++)
            this.procedimentos[totalProcedimentos++] = procs[i];
        this.consulta.status = "realizada";
    }

    public void registrar(String observacoes, String diagnostico, String procedimento) {
        this.observacoes = observacoes;
        this.diagnostico = diagnostico;
        if (totalProcedimentos < 10) this.procedimentos[totalProcedimentos++] = procedimento;
        else System.out.println("Limite de 10 procedimentos atingido.");
        this.consulta.status = "realizada";
    }

    public void adicionarProcedimento(String proc) {
        if (totalProcedimentos < 10) { procedimentos[totalProcedimentos++] = proc; System.out.println("Procedimento adicionado."); }
        else System.out.println("Limite de procedimentos atingido.");
    }

    public void exibir() {
        System.out.println("-- Resumo do Atendimento --");
        System.out.println("ID Atendimento: " + id);
        System.out.println("Paciente: " + consulta.paciente.nome + " | Dr(a). " + consulta.profissional.nome);
        System.out.println("Observacoes: " + observacoes);
        System.out.println("Diagnostico: " + (diagnostico != null ? diagnostico : "-"));
        System.out.print("Procedimentos: ");
        if (totalProcedimentos == 0) System.out.println("-");
        else { for (int i = 0; i < totalProcedimentos; i++) System.out.print(procedimentos[i] + (i < totalProcedimentos - 1 ? " | " : "")); System.out.println(); }
        System.out.println("Status da consulta: " + consulta.status);
    }
}

public class Agendamento {
    public static Consulta[] consultas = new Consulta[200];
    public static int totalConsultas = 0;

    public static Atendimento[] atendimentos = new Atendimento[200];
    public static int totalAtendimentos = 0;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        popularTeste();
        int opcao;
        do {
            System.out.println("\n========== AGENDAMENTOS E ATENDIMENTOS ==========");
            System.out.println("1 - Agendamentos");
            System.out.println("2 - Atendimentos");
            System.out.println("0 - Sair");
            System.out.print("Opcao: ");
            opcao = Integer.parseInt(sc.nextLine().trim());
            if (opcao == 1) menuAgendamentos();
            else if (opcao == 2) menuAtendimentos();
        } while (opcao != 0);
    }

    static void menuAgendamentos() {
        int op;
        do {
            System.out.println("\n--- AGENDAMENTOS ---");
            System.out.println("1 - Agendar (paciente + profissional + data + hora)");
            System.out.println("2 - Agendar com tipo");
            System.out.println("3 - Agendar por especialidade (busca automatica)");
            System.out.println("4 - Listar todas as consultas");
            System.out.println("5 - Buscar consultas por CPF");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine().trim());
            if (op == 1) agendarBasico();
            else if (op == 2) agendarComTipo();
            else if (op == 3) agendarPorEspecialidade();
            else if (op == 4) listarConsultas();
            else if (op == 5) buscarConsultasCPF();
        } while (op != 0);
    }

    static void agendarBasico() {
        Paciente pac = selecionarPaciente(); if (pac == null) return;
        if (!pac.ativo) { System.out.println("Paciente inativo. Nao e possivel agendar."); return; }
        Profissional prof = selecionarProfissional(); if (prof == null) return;
        if (prof.valorConsulta <= 0) { System.out.println("Profissional sem valor definido. Agendamento bloqueado."); return; }
        System.out.print("Data (DD/MM/AAAA): "); String data = sc.nextLine();
        System.out.print("Dia da semana: "); String dia = sc.nextLine();
        if (!prof.atendeDia(dia)) { System.out.println("Profissional nao atende em " + dia + "."); return; }
        System.out.print("Hora (HH:MM): "); String hora = sc.nextLine();
        if (temConflito(prof, data, hora)) {
            System.out.println("Horario ocupado. Sugerindo proximo horario livre...");
            String sugerido = sugerirHorario(prof, data);
            if (sugerido == null) { System.out.println("Nenhum horario livre no dia."); return; }
            System.out.print("Horario sugerido: " + sugerido + ". Confirmar? (s/n): ");
            if (!sc.nextLine().trim().equalsIgnoreCase("s")) return;
            hora = sugerido;
        }
        consultas[totalConsultas] = new Consulta(totalConsultas + 1, pac, prof, data, hora);
        totalConsultas++;
        System.out.println("Consulta agendada! ID: " + totalConsultas);
    }

    static void agendarComTipo() {
        Paciente pac = selecionarPaciente(); if (pac == null) return;
        if (!pac.ativo) { System.out.println("Paciente inativo."); return; }
        Profissional prof = selecionarProfissional(); if (prof == null) return;
        if (prof.valorConsulta <= 0) { System.out.println("Profissional sem valor definido."); return; }
        System.out.print("Data (DD/MM/AAAA): "); String data = sc.nextLine();
        System.out.print("Dia da semana: "); String dia = sc.nextLine();
        if (!prof.atendeDia(dia)) { System.out.println("Profissional nao atende em " + dia + "."); return; }
        System.out.print("Hora (HH:MM): "); String hora = sc.nextLine();
        System.out.print("Tipo (consulta inicial/retorno/avaliacao): "); String tipo = sc.nextLine();
        if (temConflito(prof, data, hora)) {
            System.out.println("Horario ocupado. Sugerindo horario livre...");
            String sugerido = sugerirHorario(prof, data);
            if (sugerido == null) { System.out.println("Nenhum horario livre."); return; }
            System.out.print("Horario sugerido: " + sugerido + ". Confirmar? (s/n): ");
            if (!sc.nextLine().trim().equalsIgnoreCase("s")) return;
            hora = sugerido;
        }
        consultas[totalConsultas] = new Consulta(totalConsultas + 1, pac, prof, data, hora, tipo);
        totalConsultas++;
        System.out.println("Consulta agendada! ID: " + totalConsultas);
    }

    static void agendarPorEspecialidade() {
        Paciente pac = selecionarPaciente(); if (pac == null) return;
        if (!pac.ativo) { System.out.println("Paciente inativo."); return; }
        System.out.print("Especialidade: "); String esp = sc.nextLine();
        System.out.print("Data (DD/MM/AAAA): "); String data = sc.nextLine();
        System.out.print("Dia da semana: "); String dia = sc.nextLine();
        System.out.print("Hora (HH:MM): "); String hora = sc.nextLine();
        Profissional prof = Cadastro.buscarProfissionalPorEspDia(esp, dia);
        if (prof == null) { System.out.println("Nenhum profissional de " + esp + " disponivel em " + dia + "."); return; }
        if (temConflito(prof, data, hora)) {
            String sugerido = sugerirHorario(prof, data);
            if (sugerido == null) { System.out.println("Nenhum horario livre."); return; }
            System.out.print("Horario ocupado. Sugerido: " + sugerido + ". Confirmar? (s/n): ");
            if (!sc.nextLine().trim().equalsIgnoreCase("s")) return;
            hora = sugerido;
        }
        consultas[totalConsultas] = new Consulta(totalConsultas + 1, pac, prof, data, hora);
        totalConsultas++;
        System.out.println("Agendado com Dr(a). " + prof.nome + "! ID: " + totalConsultas);
    }

    static void listarConsultas() {
        if (totalConsultas == 0) { System.out.println("Nenhuma consulta."); return; }
        for (int i = 0; i < totalConsultas; i++) consultas[i].exibir();
    }

    static void buscarConsultasCPF() {
        System.out.print("CPF do paciente: "); String cpf = sc.nextLine();
        boolean achou = false;
        for (int i = 0; i < totalConsultas; i++) {
            if (consultas[i].paciente.cpf.equals(cpf)) { consultas[i].exibir(); achou = true; }
        }
        if (!achou) System.out.println("Nenhuma consulta encontrada para esse CPF.");
    }

    static void menuAtendimentos() {
        int op;
        do {
            System.out.println("\n--- ATENDIMENTOS ---");
            System.out.println("1 - Registrar (so observacoes)");
            System.out.println("2 - Registrar (observacoes + diagnostico)");
            System.out.println("3 - Registrar completo (procedimentos de uma vez)");
            System.out.println("4 - Registrar completo (procedimentos um a um)");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine().trim());
            if (op == 1) registrarSimplesObs();
            else if (op == 2) registrarObsDiag();
            else if (op == 3) registrarCompleto();
            else if (op == 4) registrarCompletoUmAUm();
        } while (op != 0);
    }

    static void registrarSimplesObs() {
        Consulta c = selecionarConsultaAgendada(); if (c == null) return;
        Atendimento at = new Atendimento(totalAtendimentos + 1, c);
        System.out.print("Observacoes: "); at.registrar(sc.nextLine());
        atendimentos[totalAtendimentos++] = at;
        at.exibir();
    }

    static void registrarObsDiag() {
        Consulta c = selecionarConsultaAgendada(); if (c == null) return;
        Atendimento at = new Atendimento(totalAtendimentos + 1, c);
        System.out.print("Observacoes: "); String obs = sc.nextLine();
        System.out.print("Diagnostico: "); String diag = sc.nextLine();
        at.registrar(obs, diag);
        atendimentos[totalAtendimentos++] = at;
        at.exibir();
    }

    static void registrarCompleto() {
        Consulta c = selecionarConsultaAgendada(); if (c == null) return;
        Atendimento at = new Atendimento(totalAtendimentos + 1, c);
        System.out.print("Observacoes: "); String obs = sc.nextLine();
        System.out.print("Diagnostico: "); String diag = sc.nextLine();
        System.out.print("Quantos procedimentos (max 10)? "); int qtd = Integer.parseInt(sc.nextLine().trim());
        if (qtd > 10) qtd = 10;
        String[] procs = new String[qtd];
        for (int i = 0; i < qtd; i++) { System.out.print("Procedimento " + (i + 1) + ": "); procs[i] = sc.nextLine(); }
        at.registrar(obs, diag, procs);
        atendimentos[totalAtendimentos++] = at;
        at.exibir();
    }

    static void registrarCompletoUmAUm() {
        Consulta c = selecionarConsultaAgendada(); if (c == null) return;
        Atendimento at = new Atendimento(totalAtendimentos + 1, c);
        System.out.print("Observacoes: "); String obs = sc.nextLine();
        System.out.print("Diagnostico: "); String diag = sc.nextLine();
        System.out.print("Primeiro procedimento: "); String proc = sc.nextLine();
        at.registrar(obs, diag, proc);
        atendimentos[totalAtendimentos++] = at;
        String continuar;
        do {
            System.out.print("Adicionar outro procedimento? (s/n): "); continuar = sc.nextLine().trim();
            if (continuar.equalsIgnoreCase("s")) { System.out.print("Procedimento: "); at.adicionarProcedimento(sc.nextLine()); }
        } while (continuar.equalsIgnoreCase("s"));
        at.exibir();
    }

    public static boolean temConflito(Profissional prof, String data, String hora) {
        for (int i = 0; i < totalConsultas; i++) {
            Consulta c = consultas[i];
            if (c.profissional.id == prof.id && c.data.equals(data) && c.hora.equals(hora) && c.status.equals("agendada"))
                return true;
        }
        return false;
    }

    public static String sugerirHorario(Profissional prof, String data) {
        for (int h = 8; h <= 18; h++) {
            String hora = String.format("%02d:00", h);
            if (!temConflito(prof, data, hora)) return hora;
        }
        return null;
    }

    static Paciente selecionarPaciente() {
        System.out.print("CPF do paciente: "); String cpf = sc.nextLine();
        Paciente p = Cadastro.buscarPorCPF(cpf);
        if (p == null) System.out.println("Paciente nao encontrado.");
        return p;
    }

    static Profissional selecionarProfissional() {
        System.out.print("ID do profissional: "); int id = Integer.parseInt(sc.nextLine().trim());
        Profissional p = Cadastro.buscarProfissional(id);
        if (p == null) System.out.println("Profissional nao encontrado.");
        return p;
    }

    static Consulta selecionarConsultaAgendada() {
        System.out.print("ID da consulta: "); int id = Integer.parseInt(sc.nextLine().trim());
        for (int i = 0; i < totalConsultas; i++) {
            if (consultas[i].id == id) {
                if (!consultas[i].status.equals("agendada")) {
                    System.out.println("Consulta com status '" + consultas[i].status + "'. Nao e possivel registrar atendimento.");
                    return null;
                }
                return consultas[i];
            }
        }
        System.out.println("Consulta nao encontrada.");
        return null;
    }

    public static Consulta buscarConsultaPorId(int id) {
        for (int i = 0; i < totalConsultas; i++)
            if (consultas[i].id == id) return consultas[i];
        return null;
    }

    static void popularTeste() {
        Cadastro.pacientes[0] = new Paciente(1, "Joao Silva", "111.111.111-11", 30, "83999990001", "VidaMais");
        Cadastro.pacientes[1] = new Paciente(2, "Maria Souza", "222.222.222-22", 25, "83999990002");
        Cadastro.pacientes[2] = new Paciente(3, "Pedro Lima", "333.333.333-33", 40, "83999990003", "SaudePlus");
        Cadastro.totalPacientes = 3;

        String[] diasCarla = {"segunda", "terca", "quarta", "quinta", "sexta"};
        String[] diasRicardo = {"segunda", "quarta", "sexta"};
        Cadastro.profissionais[0] = new Profissional(1, "Carla", "fisioterapia", "CREFITO-001", 150.0, diasCarla);
        Cadastro.profissionais[1] = new Profissional(2, "Ricardo", "psicologia", "CRP-002", 180.0, diasRicardo);
        Cadastro.totalProfissionais = 2;
    }
}
