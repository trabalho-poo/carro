import lejos.hardware.Button;
import java.util.*;

/**
 * Classe a utilizacao do carro.
 * 
 * @author Grupo (Cleisson diLauro, Franco Flores, Guilherme Mattos, Luciano
 *         Alves, Natalia Lopes)
 * @version 1.0 (junho-2019)
 */

public class Uso {
	public static void main(String[] args)
	{
		float PROPORCAO = 10.6666666667f;
		//10.7 graus do motor = 1 grau de 360
		List<Float> distancias = new LinkedList<Float>();
		/*
		 * Cria instancia de carro e inicia ela com 2 esteiras 
		 * sensor de cor Direito e Esquerdo ativos e infravermelho. 
		 */
		VeiculoSmart carro = new VeiculoSmart(false, true, true, true);
		float anguloDireita = 0, anguloEsquerda = 0;
		
		carro.fechaGarra();
		carro.ligaSincronizacaoEsteiras();
		carro.setVelocidadeEsteirasGrau(240);
		carro.resetTacometro();
		carro.encontraLinha();
		distancias.add(carro.getTacometroDireito());
		//System.out.println(carro.getTacometroDireito() + "tacometro\n");
		carro.resetTacometro();
		if(carro.isPreto("esquerdo") && carro.isPreto("direito")) {
			anguloDireita = 90;
			anguloEsquerda = 90;
		}
		else if(carro.isPreto("esquerdo") && !carro.isPreto("direito")) {
			carro.curvaEsquerdaCorrecao();
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
		//System.out.println(anguloDireita + "angDireita" + anguloEsquerda +  "angEsquerda\n");		
		carro.ligaSincronizacaoEsteiras();
		carro.setEsteirasForward(1);
		//carro.segueLinha();
		carro.curvaEsquerda(4);
		//carro.curvaEsquerdaCorrecao(2);
		//carro.abreGarra();
		carro.resetTacometro();
		carro.ligaSincronizacaoEsteiras();
		carro.segueLinhaAteBola();
		distancias.add(carro.getTacometroDireito());
		//System.out.println(carro.getTacometroDireito() + "tacometro 2\n");
		carro.resetTacometro();
		carro.ligaSincronizacaoEsteiras();
		if(!carro.isAchouBola()) {
			carro.curvaEsquerdaCorrecao();
			while((!carro.isPreto("esquerdo") && carro.isPreto("direito")) || (carro.isPreto("esquerdo") && !carro.isPreto("direito"))
					|| (!carro.isPreto("esquerdo") && !carro.isPreto("direito")));
			carro.stop();
			carro.ligaSincronizacaoEsteiras();
			carro.setVelocidadeEsteirasGrau(240);
			carro.segueLinhaAteBola();
			distancias.add(carro.getTacometroDireito());
		}
//		}else {
//			carro.curvaEsquerdaCorrecao(4);
//			carro.curvaDireitaCorrecao();
//			while((!carro.isPreto("esquerdo") && carro.isPreto("direito")) || (carro.isPreto("esquerdo") && !carro.isPreto("direito"))
//					|| (!carro.isPreto("esquerdo") && !carro.isPreto("direito")));
//			carro.stop();
//			carro.ligaSincronizacaoEsteiras();
//			carro.setVelocidadeEsteirasGrau(240);
//			carro.segueLinhaAteBola();
//			distancias.add(carro.getTacometroDireito());
//			//System.out.println(carro.getTacometroDireito() + "tacometro3\n");
//			if(carro.isAchouBola()) {
//				carro.setVelocidadeEsteirasGrau(240);
//				carro.setEsteirasForward(1);
//				carro.fechaGarra();
//				carro.resetTacometro();
//				carro.ligaSincronizacaoEsteiras();
//			}
//		}

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
		carro.ev3.beep5();
		carro.ev3.corLed(3);
	}

}
