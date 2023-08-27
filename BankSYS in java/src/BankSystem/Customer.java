package BankSystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Customer {
    private IdentityCard identityCard;//بطاقة التعريف
    private String phoneNum;//رقم التليفون
    private String name;//اسم العميل
    private int age;//عمر

    {
        GenerateRandomString g = new GenerateRandomString();
        phoneNum = "1"+ g.generateIntString(10);// الرقم الأول هو 1 ، متبوعًا بـ 10 أرقام هواتف عشوائية
    }

    public Customer(){
        Scanner input = new Scanner(System.in);
        System.out.println("مرحبا ما اسمك؟:");
        String name = input.nextLine();
        this.name = name;
    }
    public Customer(String IDNum){

        // الحكم على صحة رقم الهوية ، ليس هنا!
    }
    // معرف التسجيل
    public IdentityCard registerIdentify(){
        if (this.getName()!=null){
            IdentityCard card = new IdentityCard(this);
            this.identityCard = card;
            IdentityManageSystem.getIdentifyMsg().put(card.getIDNum(),this);// بعد تسجيل بطاقة الهوية ، أدخل اسم رقم هوية زوج القيمة في نظام إدارة الهوية
            return this.identityCard;
        }
        System.err.println("ليس لديك اسم بعد");
        return null;
    }
    public BankCard applyBankCard(IdentityCard card,String phoneNum){
        Scanner input = new Scanner(System.in);

        System.out.println(this.getName()+"\n رقم الهوية:"+this.getIdentityCard().getIDNum()+"\n عميل"+
                "\n رقم هاتف:"+this.getPhoneNum()+"\n التقدم بطلب للحصول على بطاقة مصرفية ... \n الرجاء تعيين كلمة مرور بطاقتك المصرفية (القاعدة: يجب أن تتكون كلمة المرور من 6 أرقام):");
        int password;
        while (true) {
            try {
                password = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                input.nextLine();// اقرأ حرف الإرجاع الإضافي
                System.err.println("كلمة المرور التي أدخلتها غير قانونية ، يرجى إعادة إدخالها:");
            }
        }
        String pswStr = String.valueOf(password);
        while (pswStr.length()!=6) {
            System.err.println("طول كلمة المرور التي أدخلتها ليس 6 أرقام ، يرجى إعادة إدخال:");
            password = input.nextInt();
            pswStr = String.valueOf(password);
        }

        // بحاجة إلى بطاقة هوية (وليس رقمًا) ورقم هاتف محمول
        BankCard bankCard = new BankCard(card.getIDNum(), phoneNum,password);
        //BankSystem.getAllBanCard().add(bankCard);// أضف تلقائيًا إلى مكتبة النظام بعد المعالجة
        BankSystem.addBanCard(bankCard);
        System.out.println("تمت معالجة البطاقة المصرفية ، رقم بطاقتك هو:"+bankCard.getCARDNUM()+"\n من فضلك اعتني جيدًا ببطاقتك المصرفية وتذكر كلمة المرور !!!");
        return bankCard;
    }

    public IdentityCard getIdentityCard() {
        if(this.identityCard==null){
            System.err.println("لم تسجل بطاقة هوية ، لذلك أسرع وتقدم للحصول على واحدة!");
        }
        return identityCard;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getName() {
        return name;
    }
}

