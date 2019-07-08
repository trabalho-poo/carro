
/**
 * Classe para a utilizacao do carro.
 * 
 * @author Grupo (Cleisson diLauro, Franco Flores, Guilherme Mattos, Luciano
 *         Alves, Natalia Lopes)
 * @version 1.0 (junho-2019)
 */

public class Uso {
	public static void main(String[] args)
	{
		/*
		 * Cria instancia de carro e inicia ela com 2 esteiras 
		 * sensor de cor Direito e Esquerdo ativos e infravermelho. 
		 */
		VeiculoSmart carro = new VeiculoSmart(false, true, true, true);

		/* Chama o metodo para o carro sair da origem ate a linha preta */
		carro.encontraLinha();
		
		/* Chama o metodo para o carro na linha ate a bola */
		carro.segueLinhaAteBola();

		/* Chama o metodo para o carro sair da linha preta e retornar para origem*/
		carro.voltaAoPontoDeOrigem();
		
		/* Executa verificacao audiovisual para fim do programa */
		carro.ev3.beep5();
		carro.ev3.corLed(3);
	}

}
