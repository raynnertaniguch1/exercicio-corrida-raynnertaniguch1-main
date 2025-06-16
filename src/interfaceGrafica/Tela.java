package interfaceGrafica;

import javax.swing.*;
import corredores.Competidor;
import corredores.LeitorDeCorredores;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Tela {
    private JFrame janela;
    private JTextField caixaTextoDistancia;
    private JButton botaoIniciarCorrida;
    private JButton botaoInterromperCorrida;
    private JButton botaoAtualizarCorrida;

    private List<Competidor> competidores;
    private List<Thread> threads;
    private int distanciaDaCorrida;

    private JPanel painelPrincipal;

    public Tela() {
        criarCompetidores();
        criarComponentes();
        tratarEventos();
        montarJanela();
    }

    private void criarCompetidores() {
        competidores = LeitorDeCorredores.lerCorredores("dados/corredores.txt");
        threads = new ArrayList<>();
    }

    private void criarComponentes() {
        janela = new JFrame("Corrida com Threads");
        caixaTextoDistancia = new JTextField("50");

        botaoIniciarCorrida = new JButton("Iniciar Corrida");
        botaoInterromperCorrida = new JButton("Interromper Corrida");
        botaoAtualizarCorrida = new JButton("Atualizar");

        painelPrincipal = new JPanel();
    }

    private void tratarEventos() {
        botaoIniciarCorrida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarCorrida();
            }
        });

        botaoInterromperCorrida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interromperCorrida();
            }
        });

        botaoAtualizarCorrida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarCorrida();
            }
        });
    }

    private void montarJanela() {
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLayout(new BorderLayout());

        JPanel painelSuperior = new JPanel();
        painelSuperior.setLayout(new FlowLayout());

        painelSuperior.add(new JLabel("Distância da corrida (em metros):"));
        caixaTextoDistancia.setColumns(10);
        caixaTextoDistancia.setHorizontalAlignment(JTextField.RIGHT);

        painelSuperior.add(caixaTextoDistancia);
        painelSuperior.add(botaoIniciarCorrida);
        painelSuperior.add(botaoInterromperCorrida);
        painelSuperior.add(botaoAtualizarCorrida);

        janela.add(painelSuperior, BorderLayout.NORTH);
        janela.add(painelPrincipal, BorderLayout.CENTER);

        janela.pack();
        janela.setVisible(true);
    }

    private void iniciarCorrida() {
    try {
        distanciaDaCorrida = Integer.parseInt(caixaTextoDistancia.getText());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(janela, "A distância da corrida deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (distanciaDaCorrida > 0) {
        threads.clear();

        for (Competidor c : competidores) {
            c.prepararParaNovaCorrida(distanciaDaCorrida);
            Thread t = new Thread(c);
            threads.add(t);
        }

        for (Thread t : threads) {
            t.start();
        }

        // PASSO 4: Thread de atualização automática da corrida
        Thread atualizador = new Thread(
            new Runnable() {
                public void run() {
                    while (aindaHaCompetidoresCorrendo()) {
                        painelPrincipal.repaint();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            }
        );
        atualizador.start();
    }
    }


    private void interromperCorrida() {
        for (Thread t : threads) {
            t.interrupt();
        }
        painelPrincipal.repaint();
    }

    private void atualizarCorrida() {
        painelPrincipal.repaint();
    }
    public void exibir() {
    janela.setVisible(true);
    janela.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private boolean aindaHaCompetidoresCorrendo() {
    for (Competidor c : competidores) {
        if (c.estaCorrendo()) {
            return true;
        }
    }
    return false;
    }

}
