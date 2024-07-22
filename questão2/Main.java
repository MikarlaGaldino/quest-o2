import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

class Produto {
    protected String nome;
    protected double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String etiquetaDePreco() {
        return nome + " R$ " + String.format("%.2f", preco);
    }
}

class ProdutoImportado extends Produto {
    private double taxaAlfandegaria;

    public ProdutoImportado(String nome, double preco, double taxaAlfandegaria) {
        super(nome, preco);
        this.taxaAlfandegaria = taxaAlfandegaria;
    }

    @Override
    public String etiquetaDePreco() {
        return nome + " R$ " + String.format("%.2f", precoTotal()) + " (Taxa alfandegária: R$ " + String.format("%.2f", taxaAlfandegaria) + ")";
    }

    public double precoTotal() {
        return preco + taxaAlfandegaria;
    }
}

class ProdutoUsado extends Produto {
    private Date dataDeFabricacao;

    public ProdutoUsado(String nome, double preco, Date dataDeFabricacao) {
        super(nome, preco);
        this.dataDeFabricacao = dataDeFabricacao;
    }


    public String etiquetaDePreco() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return nome + " (usado) R$ " + String.format("%.2f", preco) + " (Data de fabricação: " + sdf.format(dataDeFabricacao) + ")";
    }
}

public class Main {
    public static void main(String[] args) throws ParseException {
        Scanner sc = new Scanner(System.in);
        List<Produto> produtos = new ArrayList<>();

        System.out.print("Digite o número de produtos: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("Dados do produto #" + (i + 1) + ":");
            System.out.print("Comum, usado ou importado (c/u/i)? ");
            char tipo = sc.next().charAt(0);
            System.out.print("Nome: ");
            sc.nextLine();  // consumir a nova linha restante
            String nome = sc.nextLine();
            System.out.print("Preço: ");
            double preco = sc.nextDouble();

            if (tipo == 'i') {
                System.out.print("Taxa alfandegária: ");
                double taxaAlfandegaria = sc.nextDouble();
                produtos.add(new ProdutoImportado(nome, preco, taxaAlfandegaria));
            } else if (tipo == 'u') {
                System.out.print("Data de fabricação (DD/MM/AAAA): ");
                String data = sc.next();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date dataDeFabricacao = sdf.parse(data);
                produtos.add(new ProdutoUsado(nome, preco, dataDeFabricacao));
            } else {
                produtos.add(new Produto(nome, preco));
            }
        }

        System.out.println();
        System.out.println("ETIQUETAS DE PREÇO:");
        for (Produto produto : produtos) {
            System.out.println(produto.etiquetaDePreco());
        }

        sc.close();
}
}
