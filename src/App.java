import interfaceGrafica.Tela;

public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Tela tela = new Tela();
                tela.exibir();
            }
        });
    }
}
