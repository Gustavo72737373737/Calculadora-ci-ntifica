package view; // se o seu pacote for outro, troque esta linha

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.net.URL;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * Tema rosa para a calculadora. Usa só Java Swing, sem bibliotecas extras.
 * Para mudar o visual, altere as cores e fontes logo abaixo.
 */
public class Tema{

    // ===================== PALETA (mude aqui) =====================
    static final Color FUNDO       = new Color(0xFF, 0xE4, 0xEC); // fundo da janela
    static final Color VISOR_BG    = new Color(0xFF, 0xF8, 0xFA); // fundo do visor
    static final Color VISOR_TXT   = new Color(0x88, 0x0E, 0x4F); // texto do visor
    static final Color BORDA       = new Color(0xF0, 0x62, 0x92); // borda do visor
    static final Color HISTORICO   = new Color(0xAD, 0x14, 0x57); // texto do histórico

    static final Color NUMEROS     = new Color(0xFF, 0xFF, 0xFF);
    static final Color NUMEROS_TXT = new Color(0x88, 0x0E, 0x4F);
    static final Color OPERADORES  = new Color(0xF4, 0x8F, 0xB1);
    static final Color IGUAL       = new Color(0xD8, 0x1B, 0x60);
    static final Color CONTROLES   = new Color(0xEC, 0x40, 0x7A);
    static final Color CIENTIFICAS = new Color(0xF8, 0xBB, 0xD0);
    static final Color QUIMICA     = new Color(0xE1, 0xBE, 0xE7); // lilás claro, combina com o rosa
    static final Color TEXTO_CLARO = Color.WHITE;

    static final String FONTE = "Segoe UI"; // se não existir, o Java usa uma fonte padrão

    // ===================== APLICAR O TEMA =====================
    public static void aplicar(JFrame janela,
                               JTextField visor, JTextField historico,
                               AbstractButton[] numeros,
                               AbstractButton[] operadores,
                               AbstractButton igual,
                               AbstractButton[] controles,
                               AbstractButton[] cientificas,
                               AbstractButton[] quimica) {

        janela.getContentPane().setBackground(FUNDO);
        janela.setTitle("Calculadora Científica");

        // Visor
        visor.setBackground(VISOR_BG);
        visor.setForeground(VISOR_TXT);
        visor.setFont(new Font(FONTE, Font.BOLD, 28));
        visor.setHorizontalAlignment(JTextField.LEFT);
        visor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA, 2, true),
                BorderFactory.createEmptyBorder(4, 12, 4, 12)));

        // Histórico
        historico.setBackground(FUNDO);
        historico.setForeground(HISTORICO);
        historico.setFont(new Font(FONTE, Font.ITALIC, 14));
        historico.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));

        // Botões
        estilizar(numeros, NUMEROS, NUMEROS_TXT, 16);
        estilizar(operadores, OPERADORES, TEXTO_CLARO, 18);
        estilizar(new AbstractButton[]{igual}, IGUAL, TEXTO_CLARO, 22);
        estilizar(controles, CONTROLES, TEXTO_CLARO, 16);
        estilizar(cientificas, CIENTIFICAS, NUMEROS_TXT, 14);
        estilizar(quimica, QUIMICA, NUMEROS_TXT, 14);
    }

    private static void estilizar(AbstractButton[] botoes, Color fundo, Color texto, int tamanhoFonte) {
        for (AbstractButton b : botoes) {
            b.setUI(new BotaoArredondadoUI(fundo));  // o setUI vem primeiro
            b.setOpaque(false);
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
            b.setFocusPainted(false);
            b.setRolloverEnabled(true);              // permite o efeito ao passar o mouse
            b.setForeground(texto);
            b.setFont(new Font(FONTE, Font.BOLD, tamanhoFonte));
            b.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        }
    }

    // ===================== IMAGENS E ÍCONES =====================

    /** Carrega uma imagem: primeiro do classpath (/imagens/x.png), depois de um arquivo (imagens/x.png). */
    public static ImageIcon carregarIcone(String caminho, int largura, int altura) {
        ImageIcon icone = null;
        URL url = Tema.class.getResource(caminho.startsWith("/") ? caminho : "/" + caminho);
        if (url != null) {
            icone = new ImageIcon(url);
        } else if (new java.io.File(caminho).exists()) {
            icone = new ImageIcon(caminho);
        }
        if (icone == null) {
            System.err.println("Imagem não encontrada: " + caminho);
            return null;
        }
        if (largura > 0 && altura > 0) {
            Image redimensionada = icone.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            return new ImageIcon(redimensionada);
        }
        return icone;
    }

    /** Troca o ícone da janela (o que aparece na barra de título e na barra de tarefas). */
    public static void definirIconeDaJanela(JFrame janela, String caminho) {
        ImageIcon icone = carregarIcone(caminho, 0, 0);
        if (icone != null) {
            janela.setIconImage(icone.getImage());
        }
    }

    // ===================== BOTÃO ARREDONDADO =====================
    private static class BotaoArredondadoUI extends BasicButtonUI {
        private final Color base;

        BotaoArredondadoUI(Color base) {
            this.base = base;
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton b = (AbstractButton) c;
            ButtonModel m = b.getModel();

            Color cor = base;
            if (m.isPressed() || m.isSelected()) {
                cor = base.darker();
            } else if (m.isRollover()) {
                cor = clarear(base, 25);
            }

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(cor);
            g2.fillRoundRect(1, 1, c.getWidth() - 2, c.getHeight() - 2, 20, 20);
            g2.setColor(new Color(0, 0, 0, 35)); // contorno suave
            g2.drawRoundRect(1, 1, c.getWidth() - 3, c.getHeight() - 3, 20, 20);
            g2.dispose();

            super.paint(g, c); // desenha o texto/ícone por cima
        }

        private static Color clarear(Color c, int quanto) {
            return new Color(Math.min(255, c.getRed() + quanto),
                             Math.min(255, c.getGreen() + quanto),
                             Math.min(255, c.getBlue() + quanto));
        }
    }
}
