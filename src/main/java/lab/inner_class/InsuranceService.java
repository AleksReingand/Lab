package lab.inner_class;


public class InsuranceService
{
  private final int driverAge;
  private final int driverExperience;
  private final int numberOfAccidence;

  public InsuranceService(int driverAge, int driverExperience, int numberOfAccidence)
  {
    this.driverAge = driverAge;
    this.driverExperience = driverExperience;
    this.numberOfAccidence = numberOfAccidence;
  }

  public int calcPayment()
  {
    return driverAge * driverExperience / numberOfAccidence;
  }
}
