package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main extends EngineFrame {

    private int[][] labyrinth;
    private List<int[][]> labyrinthList;
    private Vector2 start, finish, up, down, left, right;
    private int pos;
    private boolean solved;
    private double tempoParaMudar;
    private double contadorTempo;
    private double squareSize;
    private static final int largura = 500;
    private static final int altura = 500;
    
    
    public Main() {
 
        super(largura, altura, "LabyrinthBacktrack", 60, true);
        
    }

    @Override
    public void create() {
        
        //labirinto para resolver
        labyrinth = new int[][]{
            { 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, -1, -1, 0, 0, -1, 0, 0, 0, -1, -1, -1, -1, -1, -1 },
            { 0, 0, 0, -1, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, -1, -1, -1, 0, 0, -1, 0, 0, 0, 0, 0, -1, -1, -1, 0 },
            { 0, 0, -1, 0, -1, 0, -1, 0, 0, 0, -1, 0, -1, 0, 0, 0 },
            { 0, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, -1 },
            { 0, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, 0, 0 },
            { 0, -1, -1, 0, -1, -1, 0, 0, -1, 0, -1, 0, -1, -1, -1, 0 },
            { 0, 0, 0, 0, 0, -1, 0, 0, -1, 0, -1, 0, -1, 0, 0, 0 },
            { 0, 0, 0, 0, 0, -1, 0, 0, -1, 0, -1, 0, -1, 0, -1, -1 },
            { 0, 0, -1, -1, -1, -1, 0, 0, -1, 0, -1, 0, -1, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0 },
        };
     
        start = new Vector2();
        finish = new Vector2();
        up = new Vector2();
        down = new Vector2();
        left = new Vector2(); 
        right = new Vector2();
        
        start.x = 0;
        start.y = 0;
        finish.x = 0;
        finish.y = 4;
        
        solved = false;
        up.y = down.y = left.x = right.x = 0;
        up.x = -1;
        down.x = 1;
        left.y = -1;
        right.y = 1;
        /*
        labyrinth = new int[][]{
            {0, 0 , -1},
            {0, -1, 0},
            {0, 0 ,-1},
        };
        */
        
        labyrinthList = new ArrayList<>();
        copyLabyrinth(labyrinthList, labyrinth);
        solveLabyrinth(labyrinthList, labyrinth, start, finish );
        findSquareSize();
        tempoParaMudar = 0.15;

        //inicia cada lista e ordena o array
    }

    @Override
    public void update(double delta) {

        contadorTempo += delta;

        //update listas
        if (contadorTempo > tempoParaMudar) {
            contadorTempo = 0;
            //update Select
            if (pos < labyrinthList.size() - 1) {
                pos++;
            }
        }

    }

    @Override
    public void draw() {

        clearBackground(WHITE);
        //desenharArrays();
        fillRectangle(0, 0, getScreenWidth(), getScreenHeight(), GRAY);
        drawLabyrinth(labyrinthList.get(pos), 0, 0, squareSize);
    }
    
    private void solveLabyrinth ( List<int[][]> labyrinthList, int[][] labyrinth, Vector2 start, Vector2 finish ) {
        
        solve( labyrinthList, labyrinth, start, finish);
        
    }
    
    private void solve(List<int[][]> labyrinthList,int[][] labyrinth,Vector2 current,Vector2 finish) {
        if(!solved) {
            if( current.x == finish.x && current.y == finish.y ) {
                solved = true;
                copyLabyrinth(labyrinthList, labyrinth);
            }
            else {
                
                if(!solved){
                    
                    //se direita disponivel
                    if( nextAvailable(labyrinth, current.add(right) ) ) {
                        //atual + 1
                        labyrinth[(int)current.x][(int)current.y]++;
                        //proximo direita + 1
                        labyrinth[(int)current.x][(int)current.y + 1]++;
                        //salvar
                        copyLabyrinth(labyrinthList, labyrinth);
                        solve(labyrinthList, labyrinth, current.add(right), finish);
                    } else {
                        //senão
                        //atual + 1
                        labyrinth[(int)current.x][(int)current.y]++;
                        copyLabyrinth(labyrinthList, labyrinth);
                    }
                    
                }
                
                if(!solved){
                    //se baixo disponivel
                    if( nextAvailable(labyrinth, current.add(down) ) ) {
                        //atual + 1
                        labyrinth[(int)current.x][(int)current.y]++;
                        //proximo baixo + 1
                        labyrinth[(int)current.x + 1][(int)current.y]++;
                        //salvar
                        copyLabyrinth(labyrinthList, labyrinth);
                        solve(labyrinthList, labyrinth, current.add(down), finish);
                    } else {
                        //senão
                        //atual + 1
                        labyrinth[(int)current.x][(int)current.y]++;
                        copyLabyrinth(labyrinthList, labyrinth);
                    }       
                }
                
                                if(!solved) {
                    //se esquerda disponivel
                    if( nextAvailable(labyrinth, current.add(left) ) ) {
                        //atual + 1
                        labyrinth[(int)current.x][(int)current.y]++;
                        //proximo esquerda + 1
                        labyrinth[(int)current.x][(int)current.y - 1]++;
                        //salvar
                        copyLabyrinth(labyrinthList, labyrinth);
                        solve(labyrinthList, labyrinth, current.add(left), finish);
                    } else {
                        //senão
                        //atual + 1
                        labyrinth[(int)current.x][(int)current.y]++;
                        copyLabyrinth(labyrinthList, labyrinth);
                    }
                }
                
                if(!solved){
                    
                    //se cima disponivel
                    if( nextAvailable(labyrinth, current.add(up) ) ) {
                        //atual + 1
                        labyrinth[(int)current.x][(int)current.y]++;
                        //proximo cima + 1
                        labyrinth[(int)current.x - 1][(int)current.y]++;
                        //salvar
                        copyLabyrinth(labyrinthList, labyrinth);
                        solve(labyrinthList, labyrinth, current.add(up), finish);
                    } else {
                        //senão
                        //atual + 1
                        labyrinth[(int)current.x][(int)current.y]++;
                        copyLabyrinth(labyrinthList, labyrinth);
                    } 
                }
                
            }
        }
    }
    
    private boolean nextAvailable(int[][] labyrinth,Vector2 coord) {
        
        //verificar se ponto está disponível
        return coord.x < labyrinth.length &&
                coord.x >= 0 &&
                coord.y < labyrinth[0].length &&
                coord.y >= 0 &&
                labyrinth[(int)coord.x][(int)coord.y] == 0;         
    }
    
    private void copyLabyrinth(List<int[][]> labyrinthList, int[][] labyrinth) {
        
        int[][] newLabyrinth = new int[labyrinth.length][];
        for ( int i = 0; i < labyrinth.length; i++) {
            newLabyrinth[i] = Arrays.copyOf(labyrinth[i], labyrinth[i].length);
        }
        
        labyrinthList.add(newLabyrinth);
        
    }
    
    private void drawLabyrinth(
            int[][] labyrinth,
            int x,
            int y,
            double squareSize
            ) {
        
        int xAux;
        int yAux;
        fillRectangle(0, 0, labyrinth[0].length*squareSize+1, labyrinth.length*squareSize+1, BLACK);
        //baixo pra cima, esquerda pra direita
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                xAux = x + j * ((int)squareSize);
                yAux = y + i * ((int)squareSize);
                //livre
                if(labyrinth[i][j] == 0)
                    fillRectangle(
                        xAux,
                        yAux,
                        squareSize,
                        squareSize,
                        WHITE);
                    
                //obstaculo
                if(labyrinth[i][j] == -1)
                    fillRectangle(
                        xAux,
                        yAux,
                        squareSize,
                        squareSize,
                        BLACK);
                
                //percorrido
                if(labyrinth[i][j] > 0)
                {
                    fillRectangle(
                        xAux,
                        yAux,
                        squareSize,
                        squareSize,
                        SKYBLUE);
                    
                    switch (labyrinth[i][j]){
                        case 2: drawText("1", xAux, yAux + (squareSize/3)/3, (int)squareSize/3, BLACK); break;
                        case 3: drawText("2", xAux, yAux + (squareSize/3)/3, (int)squareSize/3, BLACK); break;
                        case 4: drawText("3", xAux, yAux + (squareSize/3)/3, (int)squareSize/3, BLACK); break;
                        case 5: drawText("4", xAux, yAux + (squareSize/3)/3, (int)squareSize/3, BLACK); break;
                    }
                    
                    drawRectangle(
                        xAux,
                        yAux,
                        squareSize,
                        squareSize,
                        BLACK);
                }
                if(start.x == i && start.y == j)
                    drawText("ST", xAux, yAux + squareSize/4 , (int)squareSize-(int)squareSize/4, GREEN);
                if(finish.x == i && finish.y == j)
                    drawText("EN", xAux, yAux + squareSize/4 , (int)squareSize-(int)squareSize/4, RED);
            }
        }

    }
    
    
    /**
     * Instancia a engine e a inicia.
     *
     * Instantiates the engine and starts it.
     */
    public static void main(String[] args) {
        new Main();
    }

    private void findSquareSize() {
        boolean smallestDimX = largura <= altura;
        if(smallestDimX)
            squareSize = (largura/labyrinth[0].length);
        else 
            squareSize =(altura/labyrinth.length);
    }
}
