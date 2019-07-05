/**
 * Classe que contem os metodos para manipular 
 * os comandos do veiculo.
 * 
 * @author Grupo (Cleisson diLauro, Franco Flores, Guilherme Mattos, Luciano
 *         Alves, Natalia Lopes)
 * @version 1.0(julho-2019)
 */
public class VeiculoSmart extends Veiculo{
	
	private boolean achouBola = false;
	/**
	 * construtor de veiculo definindo quais sensores serao ativados
	 * @param toque : boolean ativa sensor de toque
	 * @param colorDir : boolean ativa sensor de cor Direito
	 * @param colorEsq : boolean ativa sensor de cor Esquerdo
	 * @param infravermelho : boolean ativa sensor infravermelho
	 */
	public VeiculoSmart(boolean toque, boolean colorDir, boolean colorEsq, boolean infravermelho) {
		super(toque, colorDir, colorEsq, infravermelho);
	}
	
	/**
	 * Metodo para resertar o tacometro dos motores esquerdo e direito
	 */
	public void resetTacometro() {
		this.dir.resetTacometro();
		this.esq.resetTacometro();
	}
	
	/**
	 * Metodo para pegar quantos graus o motor direito girou
	 * @return this.dir.getTacometro() : quantidade de graus que o motor direito girou
	 */
	public float getTacometroDireito() {
		return this.dir.getTacometro();
	}
	
	/**
	 * Metodo para pegar o quantos graus o motor esquerdo girou
	 * @return this.esq.getTacometro() : quantidade de graus que o motor direito girou
	 */
	public float getTacometroEsquerdo() {
		return this.esq.getTacometro();
	}
	
	/**
	 * Metodo para fazer o carro andar para frente ate um dos sensores 
	 * encontrar a cor preta
	 */
	public void encontraLinha() {
		if(this.corDirAtivo && this.corEsqAtivo)
		{
			this.setEsteirasForward();
			while(!this.isPreto("esquerdo") && !this.isPreto("direito"));
			this.stop();
		}
	}
	
	/**
	 * 
	 */
	public void segueLinha() {
		if(this.corDirAtivo && this.corEsqAtivo)
		{
			this.setEsteirasForward();
			while(this.isPreto("esquerdo") && this.isPreto("direito"));
			this.ev3.corLed(7);
			this.ev3.beep2();
			this.stop();
		}
	}
	
	/**
	 * Metodo para controlar o carro dentro da linha preta ate 
	 * ele chegar ao final da linha ou encontrar a bola,  
	 */
	public void segueLinhaAteBola() {
		if(this.corDirAtivo && this.corEsqAtivo && this.infravermIsAtivo)
		{
			this.ligaSincronizacaoEsteiras();
			this.setEsteirasForward();
			//carro fica andando enquanto os sensores de cor ver preto 
			//e o sensor de infravermelho tem a distancia do chao
			while(this.isPreto("esquerdo") && this.isPreto("direito") && (this.getDistancia() > 6));
			
			//se o sensor de cor da esquerda sair da linha e o 
			//sensor da direita ficar na linha,
			//carro virar para direita ate os dois senhores ficarem dentro da linha,
			//e chama o metodo segueLinhaAteBola recursivamente
			if(!this.isPreto("esquerdo") && this.isPreto("direito")){
				this.curvaDireita();
				while(!this.isPreto("esquerdo") && this.isPreto("direito"));
				//this.stop();
				this.segueLinhaAteBola();
			}
			//se nao se o sensor de cor da direita sair da linha e o 
			//sensor da esquerda ficar na linha,
			//carro virar para esquerda ate os dois senhores ficarem dentro da linha,
			//e chama o metodo segueLinhaAteBola recursivamente
			else if(this.isPreto("esquerdo") && !this.isPreto("direito")) {
				this.curvaEsquerda();
				while(this.isPreto("esquerdo") && !this.isPreto("direito"));
				//this.stop();
				this.segueLinhaAteBola();
			}
			//else if(!this.isPreto("esquerdo") && !this.isPreto("direito"))
//			this.stop();
			
			//se sensor de infravermelho detectar a bola chama o metodo pegaBolaNaLinha
			else if(this.getDistancia() < 6 && !this.isAchouBola()) {
				this.stop();
				this.achouBola = true;
				this.pegaBolaNaLinha();
			}
			this.stop();
		
		}
	}
	
	/**
	 * Metodo para fazer o carro pegar a bola
	 */
	public void seguraBola() {
		this.abreGarra();
		this.setVelocidadeEsteirasGrau(72);
		this.setEsteirasForward(1);
		this.fechaGarra();
	}
	
	/**
	 * Metodo para fazer o carro soltar a bola
	 */
	public void largaBola() {
		this.abreGarra();		
	}
	
	/**
	 * Metodo para dar comandos ao carro para pegar a bola
	 */
	public void pegaBolaNaLinha() {
		this.ligaSincronizacaoEsteiras();
		this.abreGarra();
		this.ev3.esperaSegundos(1);
		this.setVelocidadeEsteirasGrau(200);
		this.setEsteirasForward(1);
		this.fechaGarra();
	}
	
	/**
	 * Metodo para
	 */
	public void voltaAoPontoDeOrigem(float distanciaRetorno, float anguloRetorno, String lado) {	
		this.setVelocidadeEsteirasGrau(240);
		if (lado == "esquerda") {
			this.ev3.beep1();
			this.curvaEsquerda((int)(anguloRetorno / 240));
			this.ev3.beep1();
		}else if(lado == "direita") {
			this.ev3.beep1();
			this.curvaDireita((int)(anguloRetorno / 240));
			this.ev3.beep1();
		}
		this.ligaSincronizacaoEsteiras();
		this.setVelocidadeEsteirasGrau(240);
		this.setEsteirasForward((int) (distanciaRetorno / 240));
		this.stop();
	}
	
	/**
	 * Metodo para carro virar para a direita
	 * com uma esteira para frente e outra para tras
	 */
	public void curvaDireitaCorrecao()
	{
		this.desligaSincronizacaoEsteiras();
		this.esq.ligaFrente();
		this.dir.ligaTras();
	}
	
	/**
	 * Metodo para veiculo virar para a esquerda
	 * com uma esteira para frente e outra para tras
	 */
	public void curvaEsquerdaCorrecao()
	{
		this.desligaSincronizacaoEsteiras();
		this.dir.ligaFrente();
		this.esq.ligaTras();
		
	}
	
	/**
	 * Metodo para veiculo virar para esquerda por um determinado tempo
	 * com uma esteira para frente e a outra para tras 
	 * @param tempo
	 */
	public void curvaEsquerdaCorrecao(int tempo)
	{
		this.desligaSincronizacaoEsteiras();
		this.esq.ligaTras(tempo);
		this.dir.ligaFrente(tempo);
		
	}
	public void curvaDireitaCorrecao(int tempo)
	{
		this.desligaSincronizacaoEsteiras();
		this.esq.ligaTras(tempo);
		this.dir.ligaFrente(tempo);
		
	}
	
	/**
	 * Metodo para calcular a distancia que o carro tera que percorrer ate o ponto de origem
	 * @param distancias
	 * @param angulo
	 * @return distancia de retorno
	 */
	public float calculaDistanciaRetorno(float[] distancias, float angulo) {
		return (float) (Math.sqrt((distancias[0] * distancias[0]) + (distancias[1] * distancias[1]) +
								  (2 * distancias[0] * distancias[1] * Math.cos(angulo))));
	}
	
	/**
	 * Metodo para calcular o angulo que o carro tera que virar para voltar ao ponto de origem
	 * @param distancias
	 * @param distanciaRetorno
	 * @return angulo de retorno
	 */
	public float calculaAnguloRetorno(float[] distancias, float distanciaRetorno) {
		return (float) ((distancias[0] * distancias[0] - distancias[1] * distancias[1] -
				         distanciaRetorno * distanciaRetorno) / (- (2 * distancias[0] * distancias[1]))) ;
	}
	
	/**
	 * Metodo para verificar se a bola foi encontrada
	 * @return achouBola : boolean
	 */
	public boolean isAchouBola() {
		return this.achouBola;
	}
}
