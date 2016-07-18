package Objects;

public class Object_Gen {
	
	public Object_Allele Allel;
	
	public Object_Gen( Object_Allele Allel ) {
		//System.out.println ( "Object_Gen Allel="+Allel );
		Object_Feature_Template NewFeatures = new Object_Feature_Template( Allel.Teeth_Length, Allel.Teeth_Sharpness, Allel.TelomereLength );
		this.Allel = new Object_Allele( Allel.DominantVekt, NewFeatures );
		this.Allel.Copy(Allel);
	}

	public void DNACopying() {
		//System.out.println ( "Object_Gen sjekker om gener muterer" );
		double RngD = Math.random();
		if ( RngD > 0.0 ) {
			Object_Allele NewAllel = Allel;
			NewAllel.Mutate();
			Allel = NewAllel;
		}
	}

}
