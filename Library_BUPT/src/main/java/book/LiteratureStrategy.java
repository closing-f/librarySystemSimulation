package book;

public class LiteratureStrategy implements PricingStrategy {
	
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
		
		if(price>50)
		return price*(0.8);
		else if(price>30)
			return price-5;
		else
			return price;
	}
	public LiteratureStrategy()
	{
		
	}
	

}

