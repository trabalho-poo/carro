
public class VeiculoSmart extends Veiculo{
	
	public VeiculoSmart(boolean toque, boolean colorDir, boolean colorEsq, boolean infravermelho) {
		super(toque, colorDir, colorEsq, infravermelho);
	}
	
	
	public void resetTacometro() {
		this.dir.resetTacometro();
		this.esq.resetTacometro();
	}
	
	public float getTacometroDireito() {
		return this.dir.getTacometro();
	}
	
	public float getTacometroEsquerdo() {
		return this.esq.getTacometro();
	}
	
	public void encontraLinha() {
		if(this.corDirAtivo && this.corEsqAtivo)
		{
			this.setEsteirasForward();
			while(!this.isPreto("esquerdo") && !this.isPreto("direito"));
//			this.ev3.corLed(7);
//			this.ev3.beep2();
			this.stop();
		}
	}
	
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
	
	public void segueLinhaAteBola() {
		if(this.corDirAtivo && this.corEsqAtivo /*&& this.infravermIsAtivo*/)
		{
			this.setEsteirasForward();
			while(this.isPreto("esquerdo") && this.isPreto("direito") && (this.getDistancia() < 9)/*colocar distancia do chao*/);
			if(!this.isPreto("esquerdo") && this.isPreto("direito")){
				this.curvaDireitaCorrecao();
				while(!this.isPreto("esquerdo") && this.isPreto("direito"));
				this.stop();
				this.segueLinhaAteBola();
			}
			else if(this.isPreto("esquerdo") && !this.isPreto("direito")) {
				this.curvaEsquerdaCorrecao();
				while(this.isPreto("esquerdo") && !this.isPreto("direito"));
				this.stop();
				this.segueLinhaAteBola();
			}
			//this.ev3.corLed(7);
			//this.ev3.beep2();
			this.stop();
			if(this.getDistancia() < 9) {
				this.fechaGarra();
			}else {
				this.curvaDireitaCorrecao();
				while((!this.isPreto("esquerdo") && this.isPreto("direito")) || (this.isPreto("esquerdo") && !this.isPreto("direito"))
						|| (!this.isPreto("esquerdo") && !this.isPreto("direito")));
				this.stop();
				this.segueLinhaAteBola();
			}
			
		}
	}
	
	public void seguraBola() {
		this.abreGarra();
		this.setEsteirasForward(1, 1/3);
		this.fechaGarra();
	}
	
	public void largaBola() {
		this.abreGarra();		
	}
	
	public void pegaBolaNaLinha() {
		
	}
	
	public void voltaAoPontoDeOrigem() {
				
	}
	
	public void curvaDireitaCorrecao()
	{
		this.desligaSincronizacaoEsteiras();
		this.esq.ligaFrente();
		this.dir.ligaTras();
	}
	public void curvaEsquerdaCorrecao()
	{
		this.desligaSincronizacaoEsteiras();
		this.dir.ligaFrente();
		this.esq.ligaTras();
	}
	public void curvaEsquerdaCorrecao(int tempo)
	{
		this.desligaSincronizacaoEsteiras();
		this.esq.ligaTras(tempo);
		this.dir.ligaFrente(tempo);
		
	}
}
