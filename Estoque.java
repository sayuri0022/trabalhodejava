import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Estoque {
    private List<Item> itens;
    private int nextId;
    private final String arquivo = "estoque.dat";

    public Estoque() {
        itens = new ArrayList<>();
        carregarEstoque();
    }

    public void adicionarItem(String nome, int quantidade) {
        if (quantidade <= 0) {
            System.out.println("Quantidade inválida!");
            return;
        }
        for (Item item : itens) {
            if (item.getNome().equalsIgnoreCase(nome)) {
                System.out.println("Item já existe no estoque!");
                return;
            }
        }
        Item novoItem = new Item(nextId++, nome, quantidade);
        itens.add(novoItem);
        salvarEstoque();
        System.out.println("Item adicionado com sucesso!");
    }

    public void listarItens() {
        if (itens.isEmpty()) {
            System.out.println("Estoque vazio!");
            return;
        }
        for (Item item : itens) {
            System.out.println(item);
        }
    }

    private void carregarEstoque() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            itens = (List<Item>) ois.readObject();
            nextId = itens.size() > 0 ? itens.get(itens.size() - 1).getId() + 1 : 1;
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado, iniciando novo estoque.");
            nextId = 1;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void salvarEstoque() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(itens);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void visualizarArquivo() {
        carregarEstoque(); // Carregar itens do arquivo
        listarItens(); // Listar itens em formato legível
    }
}
