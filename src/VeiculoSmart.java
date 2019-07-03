/**
 * Classe para manipular os comandos do veiculo
 * @author  
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
			this.setEsteirasForward();
			//carro fica andando enquanto os sensores de cor ver preto 
			//e o sensor de infravermelho tem a distancia do chao
			while(this.isPreto("esquerdo") && this.isPreto("direito") && (this.getDistancia() > 8));
			
			//se o sensor de cor da esquerda sair da linha e o 
			//sensor da direita ficar na linha,
			//carro virar para direita ate os dois senhores ficarem dentro da linha,
			//e chama o metodo segueLinhaAteBola recursivamente
			if(!this.isPreto("esquerdo") && this.isPreto("direito")){
				this.curvaDireitaCorrecao();
				while(!this.isPreto("esquerdo") && this.isPreto("direito"));
				this.stop();
				this.segueLinhaAteBola();
			}
			//se nao se o sensor de cor da direita sair da linha e o 
			//sensor da esquerda ficar na linha,
			//carro virar para esquerda ate os dois senhores ficarem dentro da linha,
			//e chama o metodo segueLinhaAteBola recursivamente
			else if(this.isPreto("esquerdo") && !this.isPreto("direito")) {
				this.curvaEsquerdaCorrecao();
				while(this.isPreto("esquerdo") && !this.isPreto("direito"));
				this.stop();
				this.segueLinhaAteBola();
			}
			
			this.stop();
			
			//se sensor de infravermelho detectar a bola chama o metodo pegaBolaNaLinha
			if(this.getDistancia() < 8 && !this.isAchouBola()) {
				this.achouBola = true;
				this.pegaBolaNaLinha();
			}
		
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
	 * 
	 */
	public void pegaBolaNaLinha() {
		this.abreGarra();
		this.setVelocidadeEsteirasGrau(72);
		this.setEsteirasForward(1);
		this.fechaGarra();
	}
	
	/**
	 * 
	 */
	public void voltaAoPontoDeOrigem() {
				
	}
	
	/**
	 * Metodo para carro virar para a direita
	 * com uma esteira para frente e outra para tras
	 */
	public void curvaDireitaCorrecao()
	{
		//this.desligaSincronizacaoEsteiras();
		this.esq.ligaFrente();
		this.dir.ligaTras();
	}
	
	/**
	 * Metodo para veiculo virar para a esquerda
	 * com uma esteira para frente e outra para tras
	 */
	public void curvaEsquerdaCorrecao()
	{
		//this.desligaSincronizacaoEsteiras();
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
	
	/**
	 * Metodo para verificar se a bola foi encontrada
	 * @return achouBola : boolean
	 */
	public boolean isAchouBola() {
		return this.achouBola;
	}
}
