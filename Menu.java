import java.util.Scanner;

public class Menu {
    private Controller control;

    public Menu (Controller c)
    {
        this.control = c;
    }
    public void showMenu ()
    {
        boolean seguir= false;
        while(seguir != true){
            System.out.println("1)Configure \n2)Show Data\n3)Get new mail\n4)Train\n5)displayWords\n6)Logout\n7)Log In\n8)exit");
            Scanner scan= new Scanner(System.in);
            int num= scan.nextInt();
            switch(num){
                case 1:
                    control.configure();
                    break;
                case 2:
                    control.showData();
                    break;
                case 3:
                    control.getNewMail();
                    break;
                case 4:
                    control.train();
                    break;
                case 5:
                    control.showData();
                    break;
                case 6:
                    control.logout();
                    break;
                case 7:
                    control.login();
                    break;
                case 8:
                    control.exit();
                    break;
                default: break;

            }
        }
    }
}
