package corredores;

public   class  Corredor implements Competidor{
private String nome;
private int velocidade;
private int distanciaASerPercorrida;
private int distanciaPercorrida;
private boolean estaCorrendo;

public Corredor(String nome, int velocidade){
    this.nome=nome;
    this.velocidade=velocidade;
    this.distanciaPercorrida=0;
    this.estaCorrendo = false;
}
 
    // Nome do competidor
    @Override
    public String getNome(){
        return nome;

    }
    // Velocidade do competidor em metros por segundo
    @Override
    public int getVelocidade(){
        return velocidade;

    }
    // Distância (em metros) percorrida pelo competidor desde o início da corrida
    @Override
    public int getDistanciaPercorrida(){
        return distanciaPercorrida;

    }
    // Indica se o competidor está correndo ou não
    @Override
    public boolean estaCorrendo(){
        return estaCorrendo;

    }
    // Prepara o competidor para uma nova corrida, informando a distância (em metros) a ser percorrida
    @Override
    public void prepararParaNovaCorrida(int distanciaDaCorrida){
        if(distanciaDaCorrida<=0) {
            throw new IllegalArgumentException("A distancia da corrida deve ser maior que zero");
        }
        this.distanciaASerPercorrida=distanciaDaCorrida;
        this.distanciaPercorrida=0;
        this.estaCorrendo = false;

    }
    @Override
    public void run(){
        estaCorrendo =true;    

         
            try{
                while(distanciaPercorrida<distanciaASerPercorrida){
                    distanciaPercorrida++;
                     
                    long tempoEspera = 1000L/velocidade;
                    Thread.sleep(tempoEspera);
                
                }
            }catch(InterruptedException e ){
            
            }finally{
                estaCorrendo =false;    
            }
             
    }
             
         
         
}





