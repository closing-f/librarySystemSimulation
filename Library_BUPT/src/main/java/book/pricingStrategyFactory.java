package book;

public class pricingStrategyFactory {	   
	  private static  pricingStrategyFactory  instance = new pricingStrategyFactory ();
	  public  static  double MinusDiscountComputer=10;
	  public  static  double PercentDiscountCartoon=0.07;
	   
	  //Make the constructor private so that the class is not instantiated
	   private pricingStrategyFactory() {}
	 
	   //Gets the only object available
	   public static pricingStrategyFactory  getInstance(){
	      return instance;
	   }
	 
	  public PricingStrategy getStrategy(String category)
	   {
		  switch(category)
		  {
		  case "Cartoon":
			  return new CartoonStrategy(PercentDiscountCartoon);
		  case"Literature":
			  return new LiteratureStrategy();
		  case"Computer":
			  return new ComputerStrategy(MinusDiscountComputer);
			default:
				return new NormalStrategy();
		  }
		   
	   }
	

}
