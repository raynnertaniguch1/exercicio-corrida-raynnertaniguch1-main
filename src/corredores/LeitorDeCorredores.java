package corredores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeitorDeCorredores {
    public static List<Competidor> lerCorredores(String caminho) {
        List<Competidor> corredores = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split("\\|");
                if (partes.length >= 2) {
                    String nome = partes[0].trim();
                    int velocidade = Integer.parseInt(partes[1].trim());
                    corredores.add(new Corredor(nome, velocidade));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de corredores: " + e.getMessage());
        }

        return corredores;
    }
}
