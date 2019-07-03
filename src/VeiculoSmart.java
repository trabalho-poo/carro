/**
 * Classe para manipular os comandos do veículo
 * @author 
 * @version 1.0(julho-2019)
 */
public class VeiculoSmart extends Veiculo{
	
	private boolean achouBola = false;
	/**
	 * Construtor para veiculo
	 * @param toque
	 * @param colorDir
	 * @param colorEsq
	 * @param infravermelho
	 */
	public VeiculoSmart(boolean toque, boolean colorDir, boolean colorEsq, boolean infravermelho) {
		super(toque, colorDir, colorEsq, infravermelho);
	}
	
	/**
	 * Método para resertar o tacometro dos motores esquerdo e direito
	 */
	public void resetTacometro() {
		this.dir.resetTacometro();
		this.esq.resetTacometro();
	}
	
	/**
	 * Método para pegar quantos graus o motor direito girou
	 * @return graus do motor direito
	 */
	public float getTacometroDireito() {
		return this.dir.getTacometro();
	}
	
	/**
	 * Método para pegar o quantos graus o motor esquerdo girou
	 * @return graus do motor esquerdo
	 */
	public float getTacometroEsquerdo() {
		return this.esq.getTacometro();
	}
	
	/**
	 * Método para fazer o carro andar para frente até um dos sensores 
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
	 * Método para controlar o carro dentro da linha preta ate 
	 * ele chegar ao final da linha ou encontrar a bola,  
	 */
	public void segueLinhaAteBola() {
		if(this.corDirAtivo && this.corEsqAtivo && this.infravermIsAtivo)
		{
			this.setEsteirasForward();
			//carro fica andando enquanto os sensores de cor ver preto 
			//e o sensor de infravermelho tem a distancia do chão
			while(this.isPreto("esquerdo") && this.isPreto("direito") && (this.getDistancia() > 8));
			
			//se o sensor de cor da esquerda deixa de ver preto e o 
			//sensor da direita ver preto, da comando para 
			//carro virar para direita ate os dois sensores identificarem preto
			//e chama o método segueLinhaAteBola recursivamente
			if(!this.isPreto("esquerdo") && this.isPreto("direito")){
				this.curvaDireitaCorrecao();
				while(!this.isPreto("esquerdo") && this.isPreto("direito"));
				this.stop();
				this.segueLinhaAteBola();
			}
			//se não se o sensor de cor da direita deixa de ver preto e o 
			//sensor da esquerda ver preto, da comando para 
			//carro virar para direita ate os dois sensores identificarem preto
			//e chama o método segueLinhaAteBola recursivamente
			else if(this.isPreto("esquerdo") && !this.isPreto("direito")) {
				this.curvaEsquerdaCorrecao();
				while(this.isPreto("esquerdo") && !this.isPreto("direito"));
				this.stop();
				this.segueLinhaAteBola();
			}
			this.stop();
			if(this.getDistancia() < 8) {
				this.achouBola = true;
				this.pegaBolaNaLinha();
			}
//			}else {
//				this.curvaDireitaCorrecao();
//				while((!this.isPreto("esquerdo") && this.isPreto("direito")) || (this.isPreto("esquerdo") && !this.isPreto("direito"))
//						|| (!this.isPreto("esquerdo") && !this.isPreto("direito")));
//				this.stop();
//				this.segueLinhaAteBola();
//			}
			
		}
	}
	
	/**
	 * 
	 */
	public void seguraBola() {
		this.abreGarra();
		this.setVelocidadeEsteirasGrau(72);
		this.setEsteirasForward(1);
		this.fechaGarra();
	}
	
	/**
	 * 
	 */
	public void largaBola() {
		this.abreGarra();		
	}
	
	/**
	 * 
	 */
	public void pegaBolaNaLinha() {
		
	}
	
	/**
	 * 
	 */
	public void voltaAoPontoDeOrigem() {
				
	}
	
	/**
	 * 
	 */
	public void curvaDireitaCorrecao()
	{
		this.desligaSincronizacaoEsteiras();
		this.esq.ligaFrente();
		this.dir.ligaTras();
	}
	
	/**
	 * 
	 */
	public void curvaEsquerdaCorrecao()
	{
		this.desligaSincronizacaoEsteiras();
		this.dir.ligaFrente();
		this.esq.ligaTras();
	}
	
	/**
	 * 
	 * @param tempo
	 */
	public void curvaEsquerdaCorrecao(int tempo)
	{
		this.desligaSincronizacaoEsteiras();
		this.esq.ligaTras(tempo);
		this.dir.ligaFrente(tempo);
		
	}
	
	public boolean isAchouBola() {
		return this.achouBola;
	}
}
