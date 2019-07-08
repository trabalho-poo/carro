
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
		VeiculoSmart carro = new VeiculoSmart(false, true, true, true);

		carro.encontraLinha();
		carro.segueLinhaAteBola();
		carro.voltaAoPontoDeOrigem();
		
		carro.ev3.beep5();
		carro.ev3.corLed(3);
	}

}
