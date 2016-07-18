package Objects;

public class Object_Feature extends Object_Feature_Template {
	
	public double Teeth_Length = 0;
	public double Teeth_Sharpness = 0; //skarpe vs flate tenner. Uspesifiserte tenner er runde? +1 er skarpe og -1 er flate?
	public double TelomereLength = 1;

	public Object_Feature( Object_Feature_Template Parent ) {
		
		super( Parent.Teeth_Length, Parent.Teeth_Sharpness, Parent.TelomereLength );
		Teeth_Length = Parent.Teeth_Length;
		Teeth_Sharpness = Parent.Teeth_Sharpness;
		TelomereLength = Parent.TelomereLength;
	}

}
