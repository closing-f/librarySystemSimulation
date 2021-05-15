package book;

public class ComputerStrategy implements PricingStrategy {

	double discount_minus;
	public  double GetPreferentialPrice(double price,int vipLevel) {
		
		//getDiscount by vipLevel
		switch(vipLevel)
		{	
			case 0:break;
					
			case 1:price=price*0.9;break;
					
			case 2:price=price*0.8;break;
					
			case 3:price=price*0.7;break;
					
			default:price=price*0.7;break;
		}
		
		if(price-discount_minus>0)
		return price-discount_minus;
		else
			return 0;
	
		
		
	}
	public ComputerStrategy(double a)
	{
		this.discount_minus=a;
	}
	

}

