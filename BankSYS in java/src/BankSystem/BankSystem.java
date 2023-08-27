package BankSystem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class BankSystem {
    private static final ATM_Machine atm = new ATM_Machine();
    private static final Set<BankCard> allBankCard = new HashSet<>();
    
    public static void addBanCard(BankCard card){
        allBankCard.add(card);
    }
    public static Set getAllBanCard(){
        return allBankCard;
    }

    public static boolean loginATM(ATM_Machine atm){
        Scanner input = new Scanner(System.in);
        String cardNum;
        System.out.println("الرجاء إدخال رقم بطاقتك المصرفية: (خطأ في التشغيل ، اضغط على مفتاح # واضغط على إدخال للعودة إلى الصفحة الرئيسية للنظام)");
        cardNum = input.nextLine();
        if (cardNum.equals("#")){
            start();
        }
        ArrayList<BankCard> allBankCard = new ArrayList<>(BankSystem.getAllBanCard());

        BankCard card = null;
        boolean isExist = false;
        for (int i = 0;i<allBankCard.size();i++){
            if(allBankCard.get(i).getCARDNUM().equals(cardNum)){
                card = allBankCard.get(i);// رقم البطاقة موجود ، قم بإنشاء مثيل البطاقة المصرفية
                isExist = true;
            }
        }
        if (isExist == false){
            System.err.println("رقم البطاقة المصرفية الذي أدخلته غير موجود ، يرجى إدخاله مرة أخرى");
            loginATM(atm);// نداء متكرر
        }
        String name = card.getHolder().getName();
        System.out.println("أهلا بك"+name+"استخدم نظام الصراف الآلي للتذكير الذكي الخاص بشركتنا \n الرجاء إدخال كلمة مرور بطاقتك المصرفية:");
        int password = input.nextInt();
        int n = 3;// 3 فرص لإدخال كلمة المرور
        while (n>0) {
            if (!atm.loginCheck(card,password)) {
                n--;
                System.err.println("كلمة المرور التي أدخلتها خاطئة ، يرجى إعادة إدخالها ، فلا يزال لديك" + n + "فرصة!");
                if(n==0){
                    atm.exit();
                    return false;
                }
                password = input.nextInt();
            }else{
                System.out.println("كلمة المرور صحيحة ، جاري بدء تشغيل النظام ...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                atm.showOperation(card);
                return true;
            }
        }
        atm.exit();
        return false;
    }
    public static void start(){
        System.out.println("\t ============= مرحبًا بك في نظامنا المصرفي ============= \t");
        System.out.println("1. ليس لدي بطاقة مصرفية ، أحتاج إلى التقدم للحصول على بطاقة ");
        System.out.println("2. لدي بطاقة مصرفية، وأريد القيام ببعض الأعمال من خلال ماكينة الصراف");
        System.out.println("3 - الانسحاب من النظام المصرفي بأكمله ");
        System.out.println("الرجاء اختيار الإجراء الخاص بك:");
        Scanner input = new Scanner(System.in);
        // فقط أدخل اسمك لتقديم طلب للحصول على بطاقة مصرفية. رقم الهوية ورقم الهاتف المحمول ورقم البطاقة المصرفية يتم إنشاؤه تلقائيًا بشكل عشوائي (فقط لتعظيم محاكاة الوضع الحقيقي ، ولكن ليس لإدخال الكثير من الأشياء المرهقة أثناء الاختبار)
        String option = input.nextLine();
        if (option.equals("1")){
            Customer customer = new Customer();
            customer.registerIdentify();
            BankCard card = customer.applyBankCard(customer.getIdentityCard(), customer.getPhoneNum());
            //allBankCard.add(card)؛// قم بإيداع البطاقة المصرفية التي تمت معالجتها حديثًا في النظام
            // بعد معالجة البطاقة المصرفية ، سيتم إيداعها تلقائيًا في النظام في طريقة applicationBankCard
            System.out.println("تمت معالجة بطاقتك المصرفية وسوف تدخل النظام مرة أخرى ...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            start();
        }else if(option.equals("2")){
            loginATM(atm);
        }else if (option.equals("3")){
            System.out.println("امشِ ببطء ، شكرًا على قدومك ، ومرحبًا بكم في التعامل مع الأعمال في المرة القادمة");
        }else {
            System.err.println("لقد أدخلت حرفًا غير قانوني");
            start();
        }
    }
}
// karithbel is player realmadrid aleardy