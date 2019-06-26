
public class VeiculoSmart extends Veiculo {

	public void segueLinha(Veiculo _veiculo, SensorPretoBranco _sensorDireito, SensorPretoBranco _sensorEsquerdo,
			float[] _amostrasRecebidas) {
		if (_sensorDireito.isPreto(_amostrasRecebidas) && _sensorEsquerdo.isPreto(_amostrasRecebidas)) {
			this.setEsteirasForward();
			while (_sensorDireito.isPreto(_amostrasRecebidas) && _sensorEsquerdo.isPreto(_amostrasRecebidas));
			this.stop();
		}
		// enquanto os dois sensores tiverem pretos, segue linha

	}

	public void segueLinhaAteBola() {

	}

	public void seguraBola(Veiculo _veiculo) {
		
	}

	public void largaBola() {

	}

	public void pegaBolaNaLinha() {

	}

	public void voltaAoPontoDeOrigem() {

	}
}
