package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class JanelaPrincipal {
    public static void main(String[] args) throws Exception {
        // Cria a janela principal (JFrame)
        JFrame janela = new JFrame("Minha Primeira Janela Swing");
        janela.setSize(400, 200);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLayout(null); // Layout manual para posicionar componentes

        // Cria um texto (JLabel)
        JLabel label = new JLabel("Clique no botão abaixo:");
        label.setBounds(120, 30, 200, 25);
        janela.add(label);

        // Cria um botão (JButton)
        JButton botao = new JButton("Clique Aqui");
        botao.setBounds(130, 70, 120, 30);
        janela.add(botao);

        // Adiciona ação ao botão (Evento)
        botao.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(janela, "Você clicou no botão!");
            }
        });

        // Torna a janela visível
        janela.setVisible(true);
    }
}
