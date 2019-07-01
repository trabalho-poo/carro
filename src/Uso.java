import lejos.hardware.Button;
import java.util.*;

public class Uso {

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
		
		
		carro.segueLinha("direito");
		
		
		carro.recuaAteColidir();
		
		
		int comando = 0;
		while(comando!=9)
		{
			comando = carro.getComando();
			if(comando == 1) carro.setEsteirasForward();
			if(comando == 2) carro.setEsteirasBackward();
			if(comando == 3) carro.curvaDireita();
			if(comando == 4) carro.curvaEsquerda();
			if(comando == 5 && carro.garra.isFechada()) carro.abreGarra();
			if(comando == 6 && carro.garra.isAberta()) carro.fechaGarra();
			if(comando == 7) carro.stop();
		}
		*/
		carro.ev3.beep5();
		carro.ev3.corLed(3);
	}
	
}