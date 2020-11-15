package lab.design_patterns;

public class DistributionService
{
    public void sendMail()
    {
        int code = DBUtils.getMailCode();
        if(code == 1)
            System.out.println("Welcome");
        else if(code == 2)
            System.out.println("Call me");
    }
}
