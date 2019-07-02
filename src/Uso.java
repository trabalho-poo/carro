import lejos.hardware.Button;
import java.util.*;

public class Uso {
	
	public static void main(String[] args) {
		/*
		 * Cria instancia de carro e inicia ela com
		 * 2 esteiras, garra e todos sensores ativos
		 */
		Veiculo carro =  new Veiculo(true, true, true);
		/*
		 * Cria instancia de carro e inicia ela com
		 * 2 esteiras, garra e sensor de toque ativo
		 */
//		Veiculo carro = new Veiculo(true,false,false);
		/*
		 * Cria instancia de carro e inicia ela com
		 * 2 esteiras, garra e nenhum sensor ativo
		 */
//		Veiculo carro = new Veiculo();
		/*
		 * Cria instancia de carro e inicia ela com
		 * 2 esteiras, garra e sensor de cor ativo
		 */
//		Veiculo carro = new Veiculo(false,true,false);
		/*
		 * Cria instancia de carro e inicia ela com
		 * 2 esteiras, garra e sensor infravermelho ativo
		 */
//		Veiculo carro = new Veiculo(false,false,true);

	public static void main(String[] args)
	{
		float PROPORCAO = 10.6666666667f;
		//10.7 graus do motor = 1 grau de 360
		List<Float> distancias = new LinkedList<Float>();
		VeiculoSmart carro = new VeiculoSmart(false, true, true, true);
		float anguloDireita, anguloEsquerda;
		
		//carro.setVelocidadeEsteirasGrau(480);
		//carro.curvaEsquerda(2);
		//carro.recuaDireita(2);
		//carro.setEsteirasBackward(2);
		
		/*while(Button.ESCAPE.isUp())
		{
			carro.coletaAmostras();
			System.out.println("dir"+carro.amostras[0]+" esq" + carro.amostras[1]);
		}*/
		//carro.setVelocidadeEsteirasGrau(480);
		//carro.setEsteirasForward(1);
		//carro.curvaEsquerda(2);
		//carro.curvaDireita(2);
		//carro.setEsteirasBackward(2);
		carro.fechaGarra();
		carro.setVelocidadeEsteirasGrau(240);
		carro.resetTacometro();
		carro.encontraLinha();
		distancias.add(carro.getTacometroDireito());
		carro.resetTacometro();
		if(carro.isPreto("esquerdo") && carro.isPreto("direito")) {
			anguloDireita = 90;
			anguloEsquerda = 90;
		}
		else if(carro.isPreto("esquerdo") && !carro.isPreto("direito")) {
			carro.curvaEsquerda();
			while(!carro.isPreto("direito"));
			carro.stop();
			anguloDireita = carro.getTacometroDireito() / PROPORCAO;
			anguloEsquerda = 180 - anguloDireita;
			carro.resetTacometro();
		}
		else if(!carro.isPreto("esquerdo") && carro.isPreto("direito")) {
			carro.curvaDireitaCorrecao();
			while(!carro.isPreto("esquerdo") && carro.isPreto("direito"));
//			carro.ev3.corLed(7);
//			carro.ev3.beep2();
			carro.stop();
			anguloEsquerda = carro.getTacometroEsquerdo() / PROPORCAO;
			anguloDireita = 180 - anguloEsquerda;
			carro.resetTacometro();
		}
		carro.ligaSincronizacaoEsteiras();
		carro.setEsteirasForward(1);
		carro.segueLinha();
		carro.curvaEsquerdaCorrecao(2);
		carro.abreGarra();
		carro.segueLinhaAteBola();
// 		carro.resetTacometro();
// 		carro.setVelocidadeEsteirasGrau(240);
//		carro.curvaEsquerda(4);
//		//carro.abreGarra();
//		carro.segueLinha();
//		carro.resetTacometro();
//		carro.ligaSincronizacaoEsteiras();
//		carro.segueLinhaAteBola();
		/*distancias.add(carro.getTacometroDireito());
		carro.resetTacometro();
		if(carro.getDistancia() == 3) {
			carro.segueLinhaAteBola();
			distancias.add(carro.getTacometroDireito());
			carro.resetTacometro();
		}
		carro.seguraBola();
		carro.voltaAoPontoDeOrigem();*/
		/*carro.setVelocidadeEsteirasGrau(300);
		carro.setEsteirasForward(3);
		carro.ev3.beep3();
		carro.setEsteirasBackward(3);
		carro.ev3.beep3();
		carro.curvaDireita(2);
		carro.ev3.beep1();
		carro.curvaEsquerda(2);
		carro.ev3.beep4();
		
		/*
		 * carro espera t segundos para realizar proximo comando
		 * obs: existe opcao para milissegundos com esperaMilissegundos(t)
		 */
//		carro.ev3.esperaSegundos(2);
//		carro.ev3.beep5();
		
		
		/*
		 * carro anda e para
		 */
//		carro.setEsteirasForward();
//		carro.ev3.esperaMilissegundos(500);
//		carro.stop();
		
		
		/*
		 * Carro abre e fecha garra apos 1 segundo
		 */
		carro.abreGarra();
		carro.ev3.beep2();
		carro.ev3.esperaSegundos(1);
		carro.fechaGarra();
		
		/*
		 * Utiliza Sensores para coletar amostras no vetor de amostras do carro
		 * OBS: sensores devem ter sido ativados
		 */
		carro.coletaAmostras();
		System.out.println("Toque: "+carro.amostras[carro.tq.getOffset()]
				+" Preto e Branco: "+carro.amostras[carro.pb.getOffset()]
				+" Infravermelho: " +carro.amostras[carro.iv.getOffset()]);

		/*
		 * carro anda para tras ate sensor de toque detectar batida
		 */
		carro.recuaAteColidir();
		
		
		/*
		 * carro coleta sinais de controle remoto
		 * cada botao ou combinacao de 2 botoes possui um numero
		 * inteiro respectivo, pode-se programar acoes diferentes 
		 * para cada valor lido.
		 * OBS: sensor infravermelho inicia em modo de receptor de controle
		 * eh possivel mudar para modo detector de distancia, que
		 * mede distancia em cm de algum objeto ou superficie na frente do sensor
		 */
//		carro.coletaAmostras();
//		System.out.println(carro.amostras[carro.iv.getOffset()]);
		
		
		/*
		 * desativa  sensor de toque
		 * OBS: argumentos segue mesma ordem de ativasensores() e construtor de veiculo
		 */
		carro.desativaSensores(true, true, true);
		
		
		/*
		 * liga o sensor de toque, caso tenha sido desligado
		 * ou nao tenha sido ligado no inicio do progama
		 */
		carro.ativaSensores(true, false, false);
		
		/*
		 * carro pisca o led em combinacao de cor e padrao
		 * OBS: 7 combinacoes possiveis: corLed(1), ... , corLed(7);
		 */
		carro.ev3.corLed(4);

		
		
		/*
		 * fecha todas as portas abertas
		 * de sensores e motores
		 */
		carro.fechaPortas();
		
	}

}
