/**
 * Classe para manipular os comandos do veiculo
 * @author  
 * @version 1.0(julho-2019)
 */
public class VeiculoSmart extends Veiculo{
	// 10.6666666667 graus do motor = 1 grau de 360
	private float PROPORCAO = 10.6666666667f;
	private float[] distancias = new float[2];
	private float anguloDireita = 0, anguloEsquerda = 0, anguloRetorno = 0, distanciaRetorno = 0;
	private boolean achouBolaNaEsquerda = false, achouBola = false;
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
	 * Metodo para o carro andar para frente ate encontrar a linha e registrar a distancia percorrida
	 */
	public void encontraLinha() {
		if(this.corDirAtivo && this.corEsqAtivo)
		{
			this.setVelocidadeEsteirasGrau(240);
			this.setEsteirasForward();
			while(!this.isPreto("esquerdo") && !this.isPreto("direito"));
			this.stop();
			distancias[0] = this.getTacometroDireito();
			this.resetTacometro();
		}
	}

	/**
	 * Metodo para calcular a angulacao do carro com relacao a linha 
	 * e o carro virar a esquerda para dentro da linha
	 */
	public void entraNaLinha() {

		//Se os sensores da esquerda e direita chegarem ao mesmo tempo na linha,
		//o carro fica com 90 graus de angulacao para o lado direito e esquerdo da linha
		if(this.isPreto("esquerdo") && this.isPreto("direito")) {
			anguloDireita = 90;
			anguloEsquerda = 90;
		}
		//Se o sensor esquerdo chegar primeiro na linha, o carro curva para esquerda
		//ate o sensor direito chegar a linha, e converte a rotacao do motor direito
		//para a angulacao do carro em relacao ao lado direito da linha e calcula 
		//a angulacao do carro em relacao ao lado esquerdo da linha
		else if(this.isPreto("esquerdo") && !this.isPreto("direito")) {
			this.curvaEsquerda();
			while(!this.isPreto("direito"));
			this.stop();
			anguloDireita = this.getTacometroDireito() / PROPORCAO;
			anguloEsquerda = 180 - anguloDireita;
			this.resetTacometro();
		}
		//Se o sensor direito chegar primeiro na linha, o carro curva para direita
		//ate o sensor esquerdo chegar a linha, e converte a rotacao do motor esquerdo
		//para a angulacao do carro em relacao ao lado esquerdo da linha e calcula
		//a angulacao do carro em relacao ao lado direito da linha
		else if(!this.isPreto("esquerdo") && this.isPreto("direito")) {
			this.curvaDireita();
			while(!this.isPreto("esquerdo") && this.isPreto("direito"));
			this.stop();
			anguloEsquerda = this.getTacometroEsquerdo() / PROPORCAO;
			anguloDireita = 180 - anguloEsquerda;
			this.resetTacometro();
		}
		//Vira o carro para esquerda
		this.setEsteirasForward(1);
		this.curvaEsquerdaNoEixo(4);
		this.resetTacometro();
	}

	/**
	 * Metodo para controlar o carro dentro da linha preta ate chegar
	 * no final ou encontrar a bola, caso encontre a bola, pega a mesma
	 */
	public void segueLinha() {
		if(this.corDirAtivo && this.corEsqAtivo)
		{
			this.ligaSincronizacaoEsteiras();
			this.setEsteirasForward();
			//Carro segue a linha enquanto nao acha bola e um dos sensores nao sai da linha
			while(this.isPreto("esquerdo") && this.isPreto("direito") && (this.getDistancia() > 6));

			//se o carro sai da linha pela esquerda, ele curva para direita ate voltar para linha
			//e chama o metodo segueLinhaAteBola recursivamente
			if(!this.isPreto("esquerdo") && this.isPreto("direito")){
				this.curvaDireita();
				while(!this.isPreto("esquerdo") && this.isPreto("direito"));
				this.segueLinha();
			}
			//se o carro sai da linha pela direita, ele curva para esquerda ate voltar para linha
			//e chama o metodo segueLinhaAteBola recursivamente
			else if(this.isPreto("esquerdo") && !this.isPreto("direito")) {
				this.curvaEsquerda();
				while(this.isPreto("esquerdo") && !this.isPreto("direito"));
				this.segueLinha();
			}
			//Se o carro encontra a bola, chama o metodo para pegar a mesma
			else if(this.getDistancia() < 6) {
				this.stop();
				this.achouBola = true;
				this.pegaBolaNaLinha();
			}
			this.stop();
		}
	}

	/**
	 * Metodo para calcular a distancia do carro percorrida em cima da linha e verificar se a bola esta 
	 * de um lado da linha, caso nao esteja carro vira 180 graus e vai para a outra ponta da linha
	 */
	public void segueLinhaAteBola() {
		if(this.corDirAtivo && this.corEsqAtivo && this.infravermIsAtivo)
		{
			//chama o metodo para o carro entrar na linha
			this.entraNaLinha();
			//chama o metodo para o carro seguir ate o final da linha
			this.segueLinha();
			//calcula a distancia percorrida na linha 
			this.distancias[1] = (this.getTacometroDireito() + this.getTacometroEsquerdo()) / 2;
			this.resetTacometro();

			//se o carro achou a bola na primeira chamada de segue linha, 
			//significa que a bola estava no lado esquerdo da linha
			if(this.achouBola) {
				this.achouBolaNaEsquerda = true;
			}

			//se nao carro da meia volta e procura a bola no lado direito
			else {
				this.setVelocidadeEsteirasGrau(100);
				this.setEsteirasForward(1);
				this.curvaEsquerdaNoEixo();
				while(!this.isPreto("esquerdo") || !this.isPreto("direito"));
				this.stop();
				this.resetTacometro();
				this.segueLinha();

				//calcula a distancia percorrida de uma ponta ate a outra da linha e diminue pela
				//distancia que o carro tinha percorrido da chegada na linha ate o final do lado esquerdo
				this.distancias[1] = ((this.getTacometroDireito() + this.getTacometroEsquerdo()) / 2) - this.distancias[1];
				this.resetTacometro();
				this.achouBolaNaEsquerda = false;
			}
			this.stop();
			//this.ev3.esperaSegundos(3);
		}
	}

	/**
	 * Metodo para o carro soltar a bola
	 */
	public void largaBola() {
		this.abreGarra();		
	}

	/**
	 * Metodo para o carro pegar a bola
	 */
	public void pegaBolaNaLinha() {
		this.ligaSincronizacaoEsteiras();
		this.abreGarra();
		this.ev3.esperaSegundos(1);
		this.setVelocidadeEsteirasGrau(240);
		this.setEsteirasForward(1);
		this.fechaGarra();
	}

	/**
	 * Metodo para o carro calcular o quanto ele tem que virar, para qual lado tem que virar
	 * e o quanto ele tem que andar ate voltar ao ponto de origem e 
	 * faz o carro voltar ao ponto de origem
	 */
	public void voltaAoPontoDeOrigem() {	
		this.setVelocidadeEsteirasGrau(240);
		//Se o carro achou bola na esquerda, ele tem que virar pra esquerda para voltar ao ponto de origem
		if(this.achouBolaNaEsquerda) {
			this.calculaDistanciaRetorno(anguloEsquerda);
			this.calculaAnguloRetorno();
			this.anguloRetorno = 180 - (float) (Math.acos(anguloRetorno));
			this.anguloRetorno = this.anguloRetorno * PROPORCAO;
			//this.ev3.beep1();
			this.curvaEsquerda((int)(anguloRetorno / 240.0 + 1));
			//this.ev3.beep1();
			this.ligaSincronizacaoEsteiras();
			this.setEsteirasForward((int) (distanciaRetorno / 240.0 + 1));
			this.stop();
		}
		//Se o carro achou bola na esquerda, ele tem que virar pra direita para voltar ao ponto de origem
		else {
			this.calculaDistanciaRetorno(anguloDireita);
			this.calculaAnguloRetorno();
			this.anguloRetorno = 180 - (float) (Math.acos(anguloRetorno));
			this.anguloRetorno = this.anguloRetorno * PROPORCAO;
			//this.ev3.beep1();
			this.curvaDireita((int)(anguloRetorno / 240.0 + 1));
			//this.ev3.beep1();
			this.ligaSincronizacaoEsteiras();
			this.setEsteirasForward((int) (distanciaRetorno / 240.0 + 1));
			this.stop();
		}
	}

	/**
	 * Metodo para carro virar para a direita no proprio eixo
	 */
	public void curvaDireitaNoEixo()
	{
		this.desligaSincronizacaoEsteiras();
		this.esq.ligaFrente();
		this.dir.ligaTras();
		this.ligaSincronizacaoEsteiras();
	}

	/**
	 * Metodo para veiculo virar para a esquerda no proprio eixo
	 */
	public void curvaEsquerdaNoEixo()
	{
		this.desligaSincronizacaoEsteiras();
		this.dir.ligaFrente();
		this.esq.ligaTras();
		this.ligaSincronizacaoEsteiras();

	}

	/**
	 * Metodo para veiculo virar para a esquerda no proprio eixo durante um determinado tempo
	 * @param tempo
	 */
	public void curvaEsquerdaNoEixo(int tempo)
	{
		this.desligaSincronizacaoEsteiras();
		this.esq.ligaTras();
		this.dir.ligaFrente();
		this.ev3.esperaSegundos(tempo / 2);
		this.ligaSincronizacaoEsteiras();

	}

	/**
	 * Metodo para veiculo virar para a direita no proprio eixo durante um determinado tempo
	 * @param tempo
	 */
	public void curvaDireitaNoEixo(int tempo)
	{
		this.desligaSincronizacaoEsteiras();
		this.esq.ligaTras();
		this.dir.ligaFrente();
		this.ev3.esperaSegundos(tempo);
		this.ligaSincronizacaoEsteiras();

	}

	/**
	 * Metodo para calcular a distancia que o carro tera que percorrer ate o ponto de origem
	 * @param distancias
	 * @param angulo
	 * @return distancia de retorno
	 */
	public void calculaDistanciaRetorno(float angulo) {
		this.distanciaRetorno = (float) (Math.sqrt((this.distancias[0] * this.distancias[0]) + (this.distancias[1] * this.distancias[1])
				- (2 * this.distancias[0] * this.distancias[1] * Math.cos(angulo))));
	}

	/**
	 * Metodo para calcular o angulo que o carro tera que virar para voltar ao ponto de origem
	 * @param distancias
	 * @param distanciaRetorno
	 * @return angulo de retorno
	 */
	public void calculaAnguloRetorno() {
		this.anguloRetorno = (float) ((this.distanciaRetorno * this.distanciaRetorno - this.distancias[0] * this.distancias[0] + 
				this.distancias[1] * this.distancias[1]) / (2 * this.distanciaRetorno * this.distancias[1])) ;

	}
}
