
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