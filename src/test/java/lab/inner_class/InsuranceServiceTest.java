package lab.inner_class;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class InsuranceServiceTest
{
  @Test
  public void calcPayment()
  {
    InsuranceService insuranceService = new InsuranceService(10, 2, 5);
    int payment = insuranceService.calcPayment();
    Assert.assertEquals(4, payment);

  }

}