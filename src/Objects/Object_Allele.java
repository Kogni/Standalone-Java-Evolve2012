package Objects;

public class Object_Allele extends Object_Feature_Template {
	
	int DominantVekt = 0;
	
	public Object_Allele( int DominantVekt, Object_Feature_Template Features ) {
		
		super( 0, 0, 0);
		//super( Features.Teeth_Length, Features.Teeth_Sharpness, Features.TelomereLength );
		//System.out.println ( "Object_Allele " );
		this.DominantVekt = DominantVekt;
	}

	public void Mutate() {
		//System.out.println ( "Object_Allele Mutate" );
		super.Mutate();
	}

	public void Copy(Object_Allele allel) {
		this.Teeth_Length = allel.Teeth_Length;
		this.Teeth_Sharpness = allel.Teeth_Sharpness;
	}

}
