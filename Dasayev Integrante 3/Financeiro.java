import java.util.Scanner;

class Pagamento {
    public int id;
    public Consulta consulta;
    public double valorBase;
    public double valorFinal;
    public int parcelas;
    public String tipoPagamento;
    public boolean temMulta;
    public double valorMulta;

    public Pagamento(int id, Consulta consulta, double valorBase, String tipoPagamento) {
        this.id = id;
        this.consulta = consulta;
        this.valorBase = valorBase;
        this.tipoPagamento = tipoPagamento;
        this.valorFinal = valorBase;
        this.parcelas = 1;
    }

    public Pagamento(int id, Consulta consulta, double valorBase, String tipoPagamento, int parcelas) {
        this.id = id;
        this.consulta = consulta;
        this.valorBase = valorBase;
        this.tipoPagamento = tipoPagamento;
        this.parcelas = parcelas;
        this.valorFinal = valorBase;
    }

    public Pagamento(int id, Consulta consulta, double valorBase, String tipoPagamento, double multa) {
        this.id = id;
        this.consulta = consulta;
        this.valorBase = valorBase;
        this.tipoPagamento = tipoPagamento;
        this.parcelas = 1;
        this.temMulta = true;
        this.valorMulta = multa;
        this.valorFinal = valorBase + multa;
    }

    public Pagamento(int id, double valorMulta, String tipoPagamento) {
        this.id = id;
        this.valorBase = 0;
        this.valorMulta = valorMulta;
        this.temMulta = true;
        this.tipoPagamento = tipoPagamento;
        this.valorFinal = valorMulta;
        this.parcelas = 1;
    }

    public double calcularValor() {
        valorFinal = valorBase;
        if (valorFinal < 0) valorFinal = 0;
        return valorFinal;
    }

    public double calcularValor(double descontoPct) {
        valorFinal = valorBase - (valorBase * descontoPct / 100.0);
        if (valorFinal < 0) valorFinal = 0;
        return valorFinal;
    }

    public double calcularValor(double descontoPct, double multa) {
        valorFinal = valorBase - (valorBase * descontoPct / 100.0) + multa;
        if (valorFinal < 0) valorFinal = 0;
        return valorFinal;
    }

    public double calcularValor(double descontoPct, double coberturaPct, double multa) {
        double aposDesconto = valorBase - (valorBase * descontoPct / 100.0);
        double aposConvenio = aposDesconto - (valorBase * coberturaPct / 100.0);
        valorFinal = aposConvenio + multa;
        if (valorFinal < 0) valorFinal = 0;
        return valorFinal;
    }

    public void exibir() {
        System.out.println("Pagamento ID: " + id
                + " | Consulta: " + (consulta != null ? consulta.id : "-")
                + " | Base: R$" + String.format("%.2f", valorBase)
                + " | Final: R$" + String.format("%.2f", valorFinal)
                + " | " + parcelas + "x"
                + " | Tipo: " + tipoPagamento
                + (temMulta ? " | MULTA: R$" + String.format("%.2f", valorMulta) : ""));
    }
}

class Relatorio {
    static void geralConsultas(Consulta[] consultas, int total, Atendimento[] atendimentos, int totalAt) {
        System.out.println("\n===== RELATORIO GERAL =====");
        if (total == 0) { System.out.println("Nenhuma consulta registrada."); return; }
        for (int i = 0; i < total; i++) {
            consultas[i].exibir();
            String diag = buscarDiagnostico(consultas[i].id, atendimentos, totalAt);
            if (diag != null) System.out.println("  Diagnostico: " + diag);
        }
    }

    static void porProfissional(Consulta[] consultas, int total, int idProf, Atendimento[] atendimentos, int totalAt) {
        System.out.println("\n===== CONSULTAS - PROFISSIONAL ID " + idProf + " =====");
        boolean achou = false;
        for (int i = 0; i < total; i++) {
            if (consultas[i].profissional.id == idProf) {
                consultas[i].exibir();
                String diag = buscarDiagnostico(consultas[i].id, atendimentos, totalAt);
                if (diag != null) System.out.println("  Diagnostico: " + diag);
                achou = true;
            }
        }
        if (!achou) System.out.println("Nenhuma consulta encontrada.");
    }

    static void porPeriodo(Consulta[] consultas, int total, String dataInicio, String dataFim, Atendimento[] atendimentos, int totalAt) {
        System.out.println("\n===== CONSULTAS DE " + dataInicio + " A " + dataFim + " =====");
        boolean achou = false;
        for (int i = 0; i < total; i++) {
            String dc = converterData(consultas[i].data);
            String di = converterData(dataInicio);
            String df = converterData(dataFim);
            if (dc.compareTo(di) >= 0 && dc.compareTo(df) <= 0) {
                consultas[i].exibir();
                String diag = buscarDiagnostico(consultas[i].id, atendimentos, totalAt);
                if (diag != null) System.out.println("  Diagnostico: " + diag);
                achou = true;
            }
        }
        if (!achou) System.out.println("Nenhuma consulta no periodo.");
    }

    static void financeiro(Consulta[] consultas, int total, Pagamento[] pagamentos, int totalPag) {
        System.out.println("\n===== RELATORIO FINANCEIRO =====");
        int realizadas = 0, cancelamentos = 0;
        double totalFaturado = 0, totalMultas = 0;
        for (int i = 0; i < total; i++) {
            if (consultas[i].status.equals("realizada")) realizadas++;
            if (consultas[i].status.equals("cancelada")) cancelamentos++;
        }
        for (int i = 0; i < totalPag; i++) {
            totalFaturado += pagamentos[i].valorFinal;
            if (pagamentos[i].temMulta) totalMultas += pagamentos[i].valorMulta;
        }
        System.out.println("Atendimentos realizados: " + realizadas);
        System.out.println("Cancelamentos: " + cancelamentos);
        System.out.println("Total faturado: R$" + String.format("%.2f", totalFaturado));
        System.out.println("Total em multas: R$" + String.format("%.2f", totalMultas));
    }

    static String buscarDiagnostico(int idConsulta, Atendimento[] atendimentos, int total) {
        for (int i = 0; i < total; i++)
            if (atendimentos[i].consulta.id == idConsulta && atendimentos[i].diagnostico != null)
                return atendimentos[i].diagnostico;
        return null;
    }

    static String converterData(String data) {
        if (data == null || data.length() < 10) return "";
        return data.substring(6) + data.substring(3, 5) + data.substring(0, 2);
    }
}

public class Financeiro {
    public static Pagamento[] pagamentos = new Pagamento[200];
    public static int totalPagamentos = 0;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Agendamento.popularTeste();
        int opcao;
        do {
            System.out.println("\n========== FINANCEIRO ==========");
            System.out.println("1 - Pagamentos");
            System.out.println("2 - Cancelamentos");
            System.out.println("3 - Remarcacoes");
            System.out.println("4 - Relatorios");
            System.out.println("0 - Sair");
            System.out.print("Opcao: ");
            opcao = Integer.parseInt(sc.nextLine().trim());
            if (opcao == 1) menuPagamentos();
            else if (opcao == 2) menuCancelamentos();
            else if (opcao == 3) menuRemarcacoes();
            else if (opcao == 4) menuRelatorios();
        } while (opcao != 0);
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
        Pagamento pg = new Pagamento(totalPagamentos + 1, c, val, tipo, parcelas);
        pg.calcularValor();
        pagamentos[totalPagamentos++] = pg;
        exibirParcelas(pg);
    }

    static void pagarAutoSemDesconto() {
        Consulta c = selecionarConsulta(); if (c == null) return;
        System.out.print("Tipo (dinheiro/cartao/convenio): "); String tipo = sc.nextLine();
        Pagamento pg = new Pagamento(totalPagamentos + 1, c, c.profissional.valorConsulta, tipo);
        pg.calcularValor();
        pagamentos[totalPagamentos++] = pg;
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
        Pagamento pg = new Pagamento(totalPagamentos + 1, c, c.profissional.valorConsulta, tipo, parcelas);
        pg.calcularValor(desc);
        pagamentos[totalPagamentos++] = pg;
        exibirParcelas(pg);
    }

    static void pagarAutoConvenio() {
        Consulta c = selecionarConsulta(); if (c == null) return;
        double multa = buscarMultaPendente(c.paciente.cpf);
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
        Pagamento pg = new Pagamento(totalPagamentos + 1, c, c.profissional.valorConsulta, tipo, parcelas);
        pg.calcularValor(descPct, coberturaConvenio, multa);
        if (multa > 0) { pg.temMulta = true; pg.valorMulta = multa; }
        pagamentos[totalPagamentos++] = pg;
        exibirParcelas(pg);
    }

    static void exibirParcelas(Pagamento pg) {
        pg.exibir();
        if (pg.parcelas > 1)
            System.out.printf("Parcelado em %dx de R$%.2f%n", pg.parcelas, pg.valorFinal / pg.parcelas);
    }

    static void listarPagamentos() {
        if (totalPagamentos == 0) { System.out.println("Nenhum pagamento."); return; }
        for (int i = 0; i < totalPagamentos; i++) pagamentos[i].exibir();
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
        verificarEAplicarMulta(c, horaAtual, null);
    }

    static void cancelarComJustificativa() {
        Consulta c = selecionarConsulta(); if (c == null) return;
        if (!c.status.equals("agendada")) { System.out.println("Nao e possivel cancelar: status '" + c.status + "'."); return; }
        System.out.print("Hora atual (HH:MM): "); String horaAtual = sc.nextLine();
        System.out.print("Justificativa: "); String just = sc.nextLine();
        verificarEAplicarMulta(c, horaAtual, just);
    }

    static void verificarEAplicarMulta(Consulta c, String horaAtual, String justificativa) {
        int diff = horaParaMinutos(c.hora) - horaParaMinutos(horaAtual);
        c.status = "cancelada";
        if (diff < 120) {
            System.out.println("Faltam menos de 2 horas. Multa aplicada: R$50,00");
            Pagamento pg = new Pagamento(totalPagamentos + 1, 50.0, "pendente");
            pg.consulta = c;
            pagamentos[totalPagamentos++] = pg;
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
                Relatorio.financeiro(Agendamento.consultas, Agendamento.totalConsultas, pagamentos, totalPagamentos);
        } while (op != 0);
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

    static double buscarMultaPendente(String cpf) {
        for (int i = 0; i < totalPagamentos; i++) {
            Pagamento pg = pagamentos[i];
            if (pg.temMulta && pg.tipoPagamento.equals("pendente")
                    && pg.consulta != null && pg.consulta.paciente.cpf.equals(cpf))
                return pg.valorMulta;
        }
        return 0;
    }
}
