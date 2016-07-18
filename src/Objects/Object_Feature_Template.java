package Objects;

public class Object_Feature_Template {

	double Teeth_Length = 0;
	double Teeth_Sharpness = 0; //skarpe vs flate tenner. Uspesifiserte tenner er runde? +1 er skarpe og -1 er flate?
	double TelomereLength = 1;
	
	public Object_Feature_Template( double Teeth_Length, double Teeth_Sharpness, double TelomereLength ) {
		
		this.Teeth_Length = Teeth_Length;
		this.Teeth_Sharpness = Teeth_Sharpness;
		this.TelomereLength = TelomereLength;
	}

	public void Mutate() {
		double FeatureToMutate = Math.random() * 3;
		double FeatureChangeAdd = (Math.random() * 3) - 1;
		double FeatureChangeMulti = (Math.random() * 3) - 1;
		if ( FeatureToMutate >= 2 ) {
			//System.out.println( "Teeth length mutating, from "+Teeth_Length );
			//Teeth_Length = (int) (Teeth_Length + (Teeth_Length * FeatureChangeMulti));
			Teeth_Length = (Teeth_Length + FeatureChangeAdd);
			if ( Teeth_Length < 0 ) {
				Teeth_Length = 0;
			}
			//System.out.println( "Teeth length mutating, to "+Teeth_Length );
		} else if ( FeatureToMutate >= 1 ) {
			//System.out.println( "Teeth mutating, from "+Teeth_Sharpness );
			//Teeth_Sharpness = (int) (Teeth_Sharpness + (Teeth_Sharpness * FeatureChangeMulti));
			Teeth_Sharpness = (Teeth_Sharpness + FeatureChangeAdd);
			if ( Teeth_Sharpness < 0 ) {
				Teeth_Sharpness = 0;
			}
			//System.out.println( "Teeth mutating, to "+Teeth_Sharpness );
		} else if ( FeatureToMutate >= 0 ) {
			//System.out.println( "Teeth mutating, from "+Teeth_Sharpness );
			//Teeth_Sharpness = (int) (Teeth_Sharpness + (Teeth_Sharpness * FeatureChangeMulti));
			TelomereLength = (TelomereLength + FeatureChangeAdd);
			if ( TelomereLength < 0 ) {
				TelomereLength = 0;
			}
			//System.out.println( "Teeth mutating, to "+Teeth_Sharpness );
		}
	}
	
}
