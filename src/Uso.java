import lejos.hardware.Button;
import java.util.*;
import java.math.*;

public class Uso {

	public static void main(String[] args)
	{
		//10.7 graus do motor = 1 grau de 360
		
		float PROPORCAO = 10.6666666667f;
		float[] distancias = new float[2];
		VeiculoSmart carro = new VeiculoSmart(false, true, true, true);
		float anguloDireita = 0, anguloEsquerda = 0, anguloRetorno = 0, distanciaRetorno = 0;
		
		carro.ligaSincronizacaoEsteiras();
		carro.setVelocidadeEsteirasGrau(240);
		carro.resetTacometro();
		carro.encontraLinha();
		distancias[0] = carro.getTacometroDireito();
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
		distancias[1] = (carro.getTacometroDireito() + carro.getTacometroEsquerdo()) / 2;
		//System.out.println(carro.getTacometroDireito() + "tacometro 2\n");
		carro.resetTacometro();
		//carro.ligaSincronizacaoEsteiras();
		if(carro.isAchouBola()) {
			distanciaRetorno = carro.calculaDistanciaRetorno(distancias, anguloEsquerda);
			anguloRetorno = carro.calculaAnguloRetorno(distancias, distanciaRetorno);
			anguloRetorno = (float) (Math.acos(anguloRetorno) * PROPORCAO);
			carro.stop();
			carro.voltaAoPontoDeOrigem(distanciaRetorno, anguloRetorno, "esquerdo");
			
		}else {
			carro.setVelocidadeEsteirasGrau(100);
			carro.setEsteirasForward(1);
			carro.setVelocidadeEsteirasGrau(240);
			carro.curvaEsquerdaCorrecao();
			while(!carro.isPreto("esquerdo") || !carro.isPreto("direito"));
			carro.stop();
			carro.resetTacometro();
			//carro.ligaSincronizacaoEsteiras();
			carro.setVelocidadeEsteirasGrau(240);
			carro.segueLinhaAteBola();
			distancias[1] = ((carro.getTacometroDireito() + carro.getTacometroEsquerdo()) / 2) - distancias[1];
			distanciaRetorno = carro.calculaDistanciaRetorno(distancias, anguloDireita);
			anguloRetorno = carro.calculaAnguloRetorno(distancias, distanciaRetorno);
			anguloRetorno = (float) (Math.acos(anguloRetorno) * PROPORCAO);
			carro.stop();
			carro.voltaAoPontoDeOrigem(distanciaRetorno, anguloRetorno, "direita");
		}
		//carro.largaBola();
//		distanciaRetorno = (float) Math.sqrt((distancias[0] * distancias[0]) + (distancias[1] * distancias[1]) +
//									2 * distancias[0] * distancias[1] * Math.cos(anguloDireita));
//		
//		anguloRetorno = (float) (((distancias[0] * distancias[0]) - (distancias[1] * distancias[1]) +
//								 (distanciaRetorno * distanciaRetorno)) - (2 * distancias[0] * distancias[1])) ;
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