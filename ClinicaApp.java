import java.util.Scanner;

public class ClinicaApp {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Agendamento.popularTeste();
        int opcao;
        do {
            System.out.println("\n========== CLINICA VIDAPLENA ==========");
            System.out.println("1 - Pacientes");
            System.out.println("2 - Profissionais");
            System.out.println("3 - Agendamentos");
            System.out.println("4 - Atendimentos");
            System.out.println("5 - Pagamentos");
            System.out.println("6 - Cancelamentos");
            System.out.println("7 - Remarcacoes");
            System.out.println("8 - Relatorios");
            System.out.println("0 - Sair");
            System.out.print("Opcao: ");
            opcao = Integer.parseInt(sc.nextLine().trim());

            if (opcao == 1) menuPacientes();
            else if (opcao == 2) menuProfissionais();
            else if (opcao == 3) menuAgendamentos();
            else if (opcao == 4) menuAtendimentos();
            else if (opcao == 5) menuPagamentos();
            else if (opcao == 6) menuCancelamentos();
            else if (opcao == 7) menuRemarcacoes();
            else if (opcao == 8) menuRelatorios();
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
        if (Cadastro.cpfExiste(cpf)) { System.out.println("ERRO: CPF ja cadastrado."); return; }
        Cadastro.pacientes[Cadastro.totalPacientes] = new Paciente(Cadastro.totalPacientes + 1, nome, cpf);
        Cadastro.totalPacientes++;
        System.out.println("Paciente cadastrado. ID: " + Cadastro.totalPacientes);
    }

    static void cadastrarPacienteParcial() {
        System.out.print("Nome: "); String nome = sc.nextLine();
        System.out.print("CPF: "); String cpf = sc.nextLine();
        if (Cadastro.cpfExiste(cpf)) { System.out.println("ERRO: CPF ja cadastrado."); return; }
        System.out.print("Idade: "); int idade = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Telefone: "); String tel = sc.nextLine();
        Cadastro.pacientes[Cadastro.totalPacientes] = new Paciente(Cadastro.totalPacientes + 1, nome, cpf, idade, tel);
        Cadastro.totalPacientes++;
        System.out.println("Paciente cadastrado. ID: " + Cadastro.totalPacientes);
    }

    static void cadastrarPacienteCompleto() {
        System.out.print("Nome: "); String nome = sc.nextLine();
        System.out.print("CPF: "); String cpf = sc.nextLine();
        if (Cadastro.cpfExiste(cpf)) { System.out.println("ERRO: CPF ja cadastrado."); return; }
        System.out.print("Idade: "); int idade = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Telefone: "); String tel = sc.nextLine();
        System.out.print("Convenio: "); String conv = sc.nextLine();
        Cadastro.pacientes[Cadastro.totalPacientes] = new Paciente(Cadastro.totalPacientes + 1, nome, cpf, idade, tel, conv);
        Cadastro.totalPacientes++;
        System.out.println("Paciente cadastrado. ID: " + Cadastro.totalPacientes);
    }

    static void complementarSemConvenio() {
        System.out.print("CPF: "); String cpf = sc.nextLine();
        Paciente p = Cadastro.buscarPorCPF(cpf);
        if (p == null) { System.out.println("Paciente nao encontrado."); return; }
        System.out.print("Idade: "); int idade = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Telefone: "); String tel = sc.nextLine();
        p.complementar(idade, tel);
        System.out.println("Cadastro complementado.");
    }

    static void complementarComConvenio() {
        System.out.print("CPF: "); String cpf = sc.nextLine();
        Paciente p = Cadastro.buscarPorCPF(cpf);
        if (p == null) { System.out.println("Paciente nao encontrado."); return; }
        System.out.print("Idade: "); int idade = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Telefone: "); String tel = sc.nextLine();
        System.out.print("Convenio: "); String conv = sc.nextLine();
        p.complementar(idade, tel, conv);
        System.out.println("Cadastro complementado com convenio.");
    }

    static void listarPacientes() {
        if (Cadastro.totalPacientes == 0) { System.out.println("Nenhum paciente cadastrado."); return; }
        for (int i = 0; i < Cadastro.totalPacientes; i++) Cadastro.pacientes[i].exibir();
    }

    static void buscarPacienteCPF() {
        System.out.print("CPF: "); String cpf = sc.nextLine();
        Paciente p = Cadastro.buscarPorCPF(cpf);
        if (p == null) System.out.println("Nao encontrado.");
        else p.exibir();
    }

    static void desativarPaciente() {
        System.out.print("CPF: "); String cpf = sc.nextLine();
        Paciente p = Cadastro.buscarPorCPF(cpf);
        if (p == null) System.out.println("Nao encontrado.");
        else p.desativar();
    }

    static void ativarPaciente() {
        System.out.print("CPF: "); String cpf = sc.nextLine();
        Paciente p = Cadastro.buscarPorCPF(cpf);
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
        Profissional p = new Profissional(Cadastro.totalProfissionais + 1, nome, esp);
        if (p.ativo) { Cadastro.profissionais[Cadastro.totalProfissionais++] = p; System.out.println("Cadastrado. ID: " + p.id); }
    }

    static void cadastrarProfCompleto() {
        System.out.print("Nome: "); String nome = sc.nextLine();
        System.out.print("Especialidade: "); String esp = sc.nextLine();
        System.out.print("Registro profissional: "); String reg = sc.nextLine();
        System.out.print("Valor da consulta: "); double val = Double.parseDouble(sc.nextLine().trim());
        System.out.print("Quantos dias disponiveis? "); int qtd = Integer.parseInt(sc.nextLine().trim());
        String[] dias = new String[qtd];
        for (int i = 0; i < qtd; i++) { System.out.print("Dia " + (i + 1) + ": "); dias[i] = sc.nextLine(); }
        Profissional p = new Profissional(Cadastro.totalProfissionais + 1, nome, esp, reg, val, dias);
        if (p.ativo) { Cadastro.profissionais[Cadastro.totalProfissionais++] = p; System.out.println("Cadastrado. ID: " + p.id); }
    }

    static void atualizarProfSemDias() {
        System.out.print("ID do profissional: "); int id = Integer.parseInt(sc.nextLine().trim());
        Profissional p = Cadastro.buscarProfissional(id);
        if (p == null) { System.out.println("Nao encontrado."); return; }
        System.out.print("Registro: "); String reg = sc.nextLine();
        System.out.print("Valor consulta: "); double val = Double.parseDouble(sc.nextLine().trim());
        p.atualizar(reg, val);
        System.out.println("Atualizado.");
    }

    static void atualizarProfComDias() {
        System.out.print("ID do profissional: "); int id = Integer.parseInt(sc.nextLine().trim());
        Profissional p = Cadastro.buscarProfissional(id);
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
        if (Cadastro.totalProfissionais == 0) { System.out.println("Nenhum profissional cadastrado."); return; }
        for (int i = 0; i < Cadastro.totalProfissionais; i++) Cadastro.profissionais[i].exibir();
    }

    static void filtrarPorEspecialidade() {
        System.out.print("Especialidade: "); String esp = sc.nextLine();
        boolean achou = false;
        for (int i = 0; i < Cadastro.totalProfissionais; i++) {
            if (Cadastro.profissionais[i].especialidade.equalsIgnoreCase(esp)) { Cadastro.profissionais[i].exibir(); achou = true; }
        }
        if (!achou) System.out.println("Nenhum profissional para essa especialidade.");
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
        hora = resolverConflito(prof, data, hora); if (hora == null) return;
        Agendamento.consultas[Agendamento.totalConsultas] = new Consulta(Agendamento.totalConsultas + 1, pac, prof, data, hora);
        Agendamento.totalConsultas++;
        System.out.println("Consulta agendada! ID: " + Agendamento.totalConsultas);
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
        hora = resolverConflito(prof, data, hora); if (hora == null) return;
        Agendamento.consultas[Agendamento.totalConsultas] = new Consulta(Agendamento.totalConsultas + 1, pac, prof, data, hora, tipo);
        Agendamento.totalConsultas++;
        System.out.println("Consulta agendada! ID: " + Agendamento.totalConsultas);
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
        hora = resolverConflito(prof, data, hora); if (hora == null) return;
        Agendamento.consultas[Agendamento.totalConsultas] = new Consulta(Agendamento.totalConsultas + 1, pac, prof, data, hora);
        Agendamento.totalConsultas++;
        System.out.println("Agendado com Dr(a). " + prof.nome + "! ID: " + Agendamento.totalConsultas);
    }

    static String resolverConflito(Profissional prof, String data, String hora) {
        if (!Agendamento.temConflito(prof, data, hora)) return hora;
        System.out.println("Horario ocupado. Sugerindo proximo horario livre...");
        String sugerido = Agendamento.sugerirHorario(prof, data);
        if (sugerido == null) { System.out.println("Nenhum horario livre no dia."); return null; }
        System.out.print("Horario sugerido: " + sugerido + ". Confirmar? (s/n): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("s")) return null;
        return sugerido;
    }

    static void listarConsultas() {
        if (Agendamento.totalConsultas == 0) { System.out.println("Nenhuma consulta."); return; }
        for (int i = 0; i < Agendamento.totalConsultas; i++) Agendamento.consultas[i].exibir();
    }

    static void buscarConsultasCPF() {
        System.out.print("CPF do paciente: "); String cpf = sc.nextLine();
        boolean achou = false;
        for (int i = 0; i < Agendamento.totalConsultas; i++) {
            if (Agendamento.consultas[i].paciente.cpf.equals(cpf)) { Agendamento.consultas[i].exibir(); achou = true; }
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
        Atendimento at = new Atendimento(Agendamento.totalAtendimentos + 1, c);
        System.out.print("Observacoes: "); at.registrar(sc.nextLine());
        Agendamento.atendimentos[Agendamento.totalAtendimentos++] = at;
        at.exibir();
    }

    static void registrarObsDiag() {
        Consulta c = selecionarConsultaAgendada(); if (c == null) return;
        Atendimento at = new Atendimento(Agendamento.totalAtendimentos + 1, c);
        System.out.print("Observacoes: "); String obs = sc.nextLine();
        System.out.print("Diagnostico: "); String diag = sc.nextLine();
        at.registrar(obs, diag);
        Agendamento.atendimentos[Agendamento.totalAtendimentos++] = at;
        at.exibir();
    }

    static void registrarCompleto() {
        Consulta c = selecionarConsultaAgendada(); if (c == null) return;
        Atendimento at = new Atendimento(Agendamento.totalAtendimentos + 1, c);
        System.out.print("Observacoes: "); String obs = sc.nextLine();
        System.out.print("Diagnostico: "); String diag = sc.nextLine();
        System.out.print("Quantos procedimentos (max 10)? "); int qtd = Integer.parseInt(sc.nextLine().trim());
        if (qtd > 10) qtd = 10;
        String[] procs = new String[qtd];
        for (int i = 0; i < qtd; i++) { System.out.print("Procedimento " + (i + 1) + ": "); procs[i] = sc.nextLine(); }
        at.registrar(obs, diag, procs);
        Agendamento.atendimentos[Agendamento.totalAtendimentos++] = at;
        at.exibir();
    }

    static void registrarCompletoUmAUm() {
        Consulta c = selecionarConsultaAgendada(); if (c == null) return;
        Atendimento at = new Atendimento(Agendamento.totalAtendimentos + 1, c);
        System.out.print("Observacoes: "); String obs = sc.nextLine();
        System.out.print("Diagnostico: "); String diag = sc.nextLine();
        System.out.print("Primeiro procedimento: "); String proc = sc.nextLine();
        at.registrar(obs, diag, proc);
        Agendamento.atendimentos[Agendamento.totalAtendimentos++] = at;
        String continuar;
        do {
            System.out.print("Adicionar outro procedimento? (s/n): "); continuar = sc.nextLine().trim();
            if (continuar.equalsIgnoreCase("s")) { System.out.print("Procedimento: "); at.adicionarProcedimento(sc.nextLine()); }
        } while (continuar.equalsIgnoreCase("s"));
        at.exibir();
    }

    static void menuPagamentos() {
        int op;
        do {
            System.out.println("\n--- PAGAMENTOS ---");
            System.out.println("1 - Pagamento direto (informa valor)");
            System.out.println("2 - Calcular automatico (sem desconto)");
            System.out.println("3 - Calcular com desconto");
            System.out.println("4 - Calcular com desconto + convenio");
            System.out.println("5 - Listar pagamentos");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine().trim());

            if (op == 1) pagarDireto();
            else if (op == 2) pagarAutoSemDesconto();
            else if (op == 3) pagarAutoComDesconto();
            else if (op == 4) pagarAutoConvenio();
            else if (op == 5) listarPagamentos();
        } while (op != 0);
    }

    static void pagarDireto() {
        Consulta c = selecionarConsulta(); if (c == null) return;
        System.out.print("Valor: "); double val = Double.parseDouble(sc.nextLine().trim());
        System.out.print("Tipo (dinheiro/cartao/convenio): "); String tipo = sc.nextLine();
        int parcelas = 1;
        if (tipo.equalsIgnoreCase("cartao")) {
            System.out.print("Parcelas (max 3 sem juros): "); parcelas = Integer.parseInt(sc.nextLine().trim());
            if (parcelas > 3) { System.out.println("Maximo 3 parcelas."); parcelas = 3; }
        }
        Pagamento pg = new Pagamento(Financeiro.totalPagamentos + 1, c, val, tipo, parcelas);
        pg.calcularValor();
        Financeiro.pagamentos[Financeiro.totalPagamentos++] = pg;
        exibirParcelas(pg);
    }

    static void pagarAutoSemDesconto() {
        Consulta c = selecionarConsulta(); if (c == null) return;
        System.out.print("Tipo (dinheiro/cartao/convenio): "); String tipo = sc.nextLine();
        Pagamento pg = new Pagamento(Financeiro.totalPagamentos + 1, c, c.profissional.valorConsulta, tipo);
        pg.calcularValor();
        Financeiro.pagamentos[Financeiro.totalPagamentos++] = pg;
        exibirParcelas(pg);
    }

    static void pagarAutoComDesconto() {
        Consulta c = selecionarConsulta(); if (c == null) return;
        System.out.print("Desconto (%): "); double desc = Double.parseDouble(sc.nextLine().trim());
        System.out.print("Tipo (dinheiro/cartao/convenio): "); String tipo = sc.nextLine();
        int parcelas = 1;
        if (tipo.equalsIgnoreCase("cartao")) {
            System.out.print("Parcelas (max 3): "); parcelas = Integer.parseInt(sc.nextLine().trim());
            if (parcelas > 3) parcelas = 3;
        }
        Pagamento pg = new Pagamento(Financeiro.totalPagamentos + 1, c, c.profissional.valorConsulta, tipo, parcelas);
        pg.calcularValor(desc);
        Financeiro.pagamentos[Financeiro.totalPagamentos++] = pg;
        exibirParcelas(pg);
    }

    static void pagarAutoConvenio() {
        Consulta c = selecionarConsulta(); if (c == null) return;
        double multa = Financeiro.buscarMultaPendente(c.paciente.cpf);
        double descPct = 0;
        if (c.tipo.equalsIgnoreCase("retorno")) descPct = 20;
        double coberturaConvenio = 0;
        if (c.paciente.convenio != null && !c.paciente.convenio.isEmpty()) {
            System.out.print("Cobertura do convenio (%): ");
            coberturaConvenio = Double.parseDouble(sc.nextLine().trim());
        }
        System.out.print("Tipo (dinheiro/cartao/convenio): "); String tipo = sc.nextLine();
        int parcelas = 1;
        if (tipo.equalsIgnoreCase("cartao")) {
            System.out.print("Parcelas (max 3): "); parcelas = Integer.parseInt(sc.nextLine().trim());
            if (parcelas > 3) parcelas = 3;
        }
        Pagamento pg = new Pagamento(Financeiro.totalPagamentos + 1, c, c.profissional.valorConsulta, tipo, parcelas);
        pg.calcularValor(descPct, coberturaConvenio, multa);
        if (multa > 0) { pg.temMulta = true; pg.valorMulta = multa; }
        Financeiro.pagamentos[Financeiro.totalPagamentos++] = pg;
        exibirParcelas(pg);
    }

    static void exibirParcelas(Pagamento pg) {
        pg.exibir();
        if (pg.parcelas > 1)
            System.out.printf("Parcelado em %dx de R$%.2f%n", pg.parcelas, pg.valorFinal / pg.parcelas);
    }

    static void listarPagamentos() {
        if (Financeiro.totalPagamentos == 0) { System.out.println("Nenhum pagamento."); return; }
        for (int i = 0; i < Financeiro.totalPagamentos; i++) Financeiro.pagamentos[i].exibir();
    }

    static void menuCancelamentos() {
        int op;
        do {
            System.out.println("\n--- CANCELAMENTOS ---");
            System.out.println("1 - Cancelar sem justificativa");
            System.out.println("2 - Cancelar com justificativa");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine().trim());
            if (op == 1) cancelarSemJustificativa();
            else if (op == 2) cancelarComJustificativa();
        } while (op != 0);
    }

    static void cancelarSemJustificativa() {
        Consulta c = selecionarConsulta(); if (c == null) return;
        if (!c.status.equals("agendada")) { System.out.println("Nao e possivel cancelar: status '" + c.status + "'."); return; }
        System.out.print("Hora atual (HH:MM): "); String horaAtual = sc.nextLine();
        aplicarCancelamento(c, horaAtual, null);
    }

    static void cancelarComJustificativa() {
        Consulta c = selecionarConsulta(); if (c == null) return;
        if (!c.status.equals("agendada")) { System.out.println("Nao e possivel cancelar: status '" + c.status + "'."); return; }
        System.out.print("Hora atual (HH:MM): "); String horaAtual = sc.nextLine();
        System.out.print("Justificativa: "); String just = sc.nextLine();
        aplicarCancelamento(c, horaAtual, just);
    }

    static void aplicarCancelamento(Consulta c, String horaAtual, String justificativa) {
        int diff = horaParaMinutos(c.hora) - horaParaMinutos(horaAtual);
        c.status = "cancelada";
        if (diff < 120) {
            System.out.println("Faltam menos de 2 horas. Multa aplicada: R$50,00");
            Pagamento pg = new Pagamento(Financeiro.totalPagamentos + 1, 50.0, "pendente");
            pg.consulta = c;
            Financeiro.pagamentos[Financeiro.totalPagamentos++] = pg;
        } else {
            System.out.println("Cancelamento sem multa.");
        }
        if (justificativa != null) System.out.println("Justificativa: " + justificativa);
        System.out.println("Consulta ID " + c.id + " cancelada.");
    }

    static void menuRemarcacoes() {
        int op;
        do {
            System.out.println("\n--- REMARCACOES ---");
            System.out.println("1 - Remarcar (mesmo dia, novo horario)");
            System.out.println("2 - Remarcar (nova data + novo horario)");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine().trim());
            if (op == 1) remarcarMesmoDia();
            else if (op == 2) remarcarNovaData();
        } while (op != 0);
    }

    static void remarcarMesmoDia() {
        Consulta c = selecionarConsulta(); if (c == null) return;
        if (!c.status.equals("agendada")) { System.out.println("Apenas consultas agendadas podem ser remarcadas."); return; }
        System.out.print("Novo horario (HH:MM): "); String novaHora = sc.nextLine();
        if (Agendamento.temConflito(c.profissional, c.data, novaHora)) { System.out.println("Horario ocupado."); return; }
        c.status = "remarcada";
        Consulta nova = new Consulta(Agendamento.totalConsultas + 1, c.paciente, c.profissional, c.data, novaHora, c.tipo);
        Agendamento.consultas[Agendamento.totalConsultas++] = nova;
        System.out.println("Remarcada! Nova consulta ID: " + nova.id + " as " + novaHora);
    }

    static void remarcarNovaData() {
        Consulta c = selecionarConsulta(); if (c == null) return;
        if (!c.status.equals("agendada")) { System.out.println("Apenas consultas agendadas podem ser remarcadas."); return; }
        System.out.print("Nova data (DD/MM/AAAA): "); String novaData = sc.nextLine();
        System.out.print("Dia da semana da nova data: "); String novoDia = sc.nextLine();
        System.out.print("Novo horario (HH:MM): "); String novaHora = sc.nextLine();
        if (!c.profissional.atendeDia(novoDia)) { System.out.println("Profissional nao atende em " + novoDia + "."); return; }
        if (Agendamento.temConflito(c.profissional, novaData, novaHora)) { System.out.println("Horario ocupado."); return; }
        c.status = "remarcada";
        Consulta nova = new Consulta(Agendamento.totalConsultas + 1, c.paciente, c.profissional, novaData, novaHora, c.tipo);
        Agendamento.consultas[Agendamento.totalConsultas++] = nova;
        System.out.println("Remarcada! Nova consulta ID: " + nova.id + " em " + novaData + " as " + novaHora);
    }

    static void menuRelatorios() {
        int op;
        do {
            System.out.println("\n--- RELATORIOS ---");
            System.out.println("1 - Relatorio geral");
            System.out.println("2 - Por profissional");
            System.out.println("3 - Por periodo");
            System.out.println("4 - Financeiro");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine().trim());

            if (op == 1)
                Relatorio.geralConsultas(Agendamento.consultas, Agendamento.totalConsultas, Agendamento.atendimentos, Agendamento.totalAtendimentos);
            else if (op == 2) {
                System.out.print("ID do profissional: "); int id = Integer.parseInt(sc.nextLine().trim());
                Relatorio.porProfissional(Agendamento.consultas, Agendamento.totalConsultas, id, Agendamento.atendimentos, Agendamento.totalAtendimentos);
            } else if (op == 3) {
                System.out.print("Data inicio (DD/MM/AAAA): "); String di = sc.nextLine();
                System.out.print("Data fim (DD/MM/AAAA): "); String df = sc.nextLine();
                Relatorio.porPeriodo(Agendamento.consultas, Agendamento.totalConsultas, di, df, Agendamento.atendimentos, Agendamento.totalAtendimentos);
            } else if (op == 4)
                Relatorio.financeiro(Agendamento.consultas, Agendamento.totalConsultas, Financeiro.pagamentos, Financeiro.totalPagamentos);
        } while (op != 0);
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
        for (int i = 0; i < Agendamento.totalConsultas; i++) {
            if (Agendamento.consultas[i].id == id) {
                if (!Agendamento.consultas[i].status.equals("agendada")) {
                    System.out.println("Consulta com status '" + Agendamento.consultas[i].status + "'. Nao e possivel registrar atendimento.");
                    return null;
                }
                return Agendamento.consultas[i];
            }
        }
        System.out.println("Consulta nao encontrada.");
        return null;
    }

    static Consulta selecionarConsulta() {
        System.out.print("ID da consulta: "); int id = Integer.parseInt(sc.nextLine().trim());
        Consulta c = Agendamento.buscarConsultaPorId(id);
        if (c == null) System.out.println("Consulta nao encontrada.");
        return c;
    }

    static int horaParaMinutos(String hora) {
        String[] p = hora.split(":");
        return Integer.parseInt(p[0]) * 60 + Integer.parseInt(p[1]);
    }
}
