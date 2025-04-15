import java.util.Random;

public class JogoDaVelha {
    protected static final int X = 1, O = -1;
    protected static final int VAZIO = 0;
    protected int tabuleiro[][];
    protected int jogador;
    protected int dim;

    public  JogoDaVelha(int dimensao) {
        limparTabuleiro();
        this.tabuleiro = new int[dimensao][dimensao];
        this.dim = dimensao;
    };

    public void limparTabuleiro() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                tabuleiro[i][j] = VAZIO;
            }
        }
        jogador = X;
    };

    public void poePeca(int i, int j) {
        if (i < 0 || i > 4 || j < 0 || j > 4) {
            throw new IllegalArgumentException("Posição inválida");
        }
        if (tabuleiro[i][j] != VAZIO)
            throw new IllegalArgumentException("Posição ocupada");
        tabuleiro[i][j] = jogador;

        jogador = -jogador;
    }

    public int[] gerarLinhaColuna() {
        Random gerar = new Random();
        int linha = gerar.nextInt(0, dim);
        int coluna = gerar.nextInt(0, dim);
        return new int[] { linha, coluna };
    }

    public int poePecaAutomatico() {
        int[] posicao = gerarLinhaColuna();
        while (tabuleiro[posicao[0]][posicao[1]] != VAZIO) {
            posicao = gerarLinhaColuna();
        }
        tabuleiro[posicao[0]][posicao[1]] = jogador;
        int acabou = vencedor();

        if (acabou == 1) {
            return 1;
        } else if (acabou == -1) {
            return -1;
        } else if (acabou == 0) {
            return 0;
        } 

        jogador = -jogador;
        return 2;
    }

    public boolean descVencedor(int marca) {
        int somaDiagonal1 = 0;
        int somaDiagonal2 = 0;
        for (int i = 0; i < dim; i++) {
            int somaLinha = 0;
            for (int j = 0; j < dim; j++) {
                somaLinha += tabuleiro[i][j];
                if (i == j) {
                    somaDiagonal1 += tabuleiro[i][j];
                }

                if (i + j == 2) {
                    somaDiagonal2 += tabuleiro[i][j];
                }
            }
            if (somaLinha == marca * dim) {
                return true;
            }
        }

        if (somaDiagonal1 == marca * dim) {
            return true;
        }

        if (somaDiagonal2 == marca * dim) {
            return true;
        }

        for (int j = 0; j < dim; j++) {
            int somaColuna = 0;
            for (int i = 0; i < dim; i++) {
                somaColuna += tabuleiro[i][j];
            }
            if (somaColuna == marca * dim) {
                return true;
            }
        }

        return false;
    }

    public int vencedor() {
        if (descVencedor(X)) {
            return X;
        } else if (descVencedor(O)) {
            return O;
        } else {
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    if (tabuleiro[i][j] == VAZIO) {
                        return 2;
                    }
                }
            }
        }
        return 0;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                switch (tabuleiro[i][j]) {
                    case X:
                        s += "X";
                        break;
                    case O:
                        s += "O";
                        break;
                    case VAZIO:
                        s += " ";
                        break;
                }
                if (j < dim - 1)
                    s += "|"; // limite da coluna
            }
            if (i < dim - 1) {
                String tracos = "-".repeat(dim * 2 - 1); // limite da linha
                s += "\n" + tracos + "\n"; // limite da linha
            }
        }
        return s;
    }

    public int getJogador() {
        return jogador;
    }
}