package book;

public class CartoonStrategy implements PricingStrategy {

	double discount_100;
	public  double GetPreferentialPrice(double price,int vipLevel) {
		
		switch(vipLevel)
		{	
			case 0:break;
					
			case 1:price=price*0.9;break;
					
			case 2:price=price*0.8;break;
					
			case 3:price=price*0.7;break;
					
			default:price=price*0.7;break;
		}		
		
		return price*(1-discount_100);
	
		
		
	}
	public CartoonStrategy(double a)
	{
		this.discount_100=a;
	}
	

}

