package BankSystem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ATM_Machine {
    private boolean isFirstShow = true;
    public boolean loginCheck(BankCard card,int password){
        int truePassword = card.getPassword();
        if (truePassword == password){
            return true;
        }
        return false;
    }
    public void showTitle(){
        System.out.println("------------ جهاز الصراف الآلي ------------");
    }
    // بعد تسجيل الدخول بنجاح ، يبدأ النظام في العمل
    public void showOperation(BankCard card){
        // تسجيل الدخول ، التحقق من الرصيد ، الإيداع ، السحب ، التحويل ، إلغاء الحساب ، فتح الحساب ، تسجيل الخروج
        Scanner input = new Scanner(System.in);
        if(isFirstShow) {
            this.showTitle();
            isFirstShow = false;
        }
        System.out.println("1. تحقق من الرصيد ");
        System.out.println("2. الإيداع");
        System.out.println("3. تحويل ");
        System.out.println("4. سحب الأموال");
        System.out.println("5. إلغاء الحساب ");
        System.out.println("6. خروج");
        System.out.println("الرجاء إدخال ما تريد القيام به:");
        int option = input.nextInt();
        input.nextLine();
        switch (option){
            case 6:exit();
                BankSystem.start();
                break;
            case 1:showBalance(card);
                break;
            case 2:showDeposit(card);
                break;
            case 3:showTransfer(card);
                break;
            case 4:showWithdrawal(card);
                break;
            case 5:closeAccount(card);
                break;
            default:
                System.err.println("لقد أدخلت شخصية غير قانونية ، أقترح عليك إدخالها مرة أخرى!");
                showOperation(card);// نداء متكرر
        }
    }
    // اخرج من النظام ، أدخل كلمة مرور خاطئة 3 مرات ، أو بعد اكتمال المعاملة ، يجب عليك الخروج من النظام
    public void exit(){
        System.out.println("نظام الصراف الآلي مغلق");
    }

    public static void main(String[] args) {
        ATM_Machine a = new ATM_Machine();

    }
    public BigDecimal queryBalance(BankCard card){
        return card.getBalance();
    }
    public void showBalance(BankCard card){
        this.showTitle();
        System.out.println("رصيدك هو:"+queryBalance(card));
        this.showOperation(card);
    }
    // إيداع الإيداع
    public void deposit(BankCard card,int amount){
        card.setBalance(card.getBalance().add(new BigDecimal(amount)));
    }
    public void showDeposit(BankCard card){
        this.showTitle();
        int amount = checkAmountInput("الوديعة",20000);// إيداع حتى 20،000 في المرة الواحدة
        deposit(card,amount);// تنفيذ عملية الإيداع
        System.out.println("تم الإيداع بنجاح ، رصيد بطاقتك الحالي هو:"+card.getBalance());
        showOperation(card);
    }
    // الانسحاب: الانسحاب
    public void withdrawal(BankCard card,int amount){
        card.setBalance(card.getBalance().subtract(new BigDecimal(amount)));
    }
    public void showWithdrawal(BankCard card){
        this.showTitle();
        int amount = checkAmountInput("انسحاب",3000);// إيداع حتى 20،000 في المرة الواحدة
        withdrawal(card,amount);// تنفيذ عملية الإيداع
        System.out.println("تم السحب بنجاح ، رصيد بطاقتك الحالي هو:"+card.getBalance());
        showOperation(card);
    }
    // إغلاق حساب: إغلاق حساب
    public void closeAccount(BankCard card ){
        Scanner input = new Scanner(System.in);
        System.err.println("إلغاء الحساب عملية خطيرة. سيتم إفراغ رصيدك بالكامل وسيتم استرداد رقم بطاقتك. هل أنت متأكد أنك تريد المتابعة؟");
        System.out.println("1. نعم ، متابعة  ");
        System.out.println("2. إلغاء هذه العملية");
        String s = input.nextLine();
        if (s.equals("1")){
            BankSystem.getAllBanCard().remove(card);
            card = null;
            System.out.println("لقد أغلقت حسابك بنجاح");
            exit();
            BankSystem.start();
        }else if (s.equals("2")){
            showOperation(card);
        }else{
            System.err.println("لقد أدخلت حرفًا غير قانوني !!! \n الرجاء إعادة العملية:");
            closeAccount(card);// نداء متكرر
        }
    }

    // تحويل: نقل
    public void transfer(BankCard myCard,BankCard anotherCard,int amount){
        myCard.setBalance(myCard.getBalance().subtract(new BigDecimal(amount)));
        anotherCard.setBalance(anotherCard.getBalance().add(new BigDecimal(amount)));
    }
    public void showTransfer(BankCard myCard){
        Scanner input = new Scanner(System.in);
        System.out.println("الرجاء إدخال رقم البطاقة المصرفية للطرف الآخر:");
        String cardNum = input.nextLine();
        ArrayList<BankCard> allBankCard = new ArrayList<>(BankSystem.getAllBanCard());
        BankCard anotherCard = null;
        boolean isExist = false;
        for (int i = 0;i<allBankCard.size();i++){
            if(allBankCard.get(i).getCARDNUM().equals(cardNum)){
                anotherCard = allBankCard.get(i);// رقم البطاقة موجود ، قم بإنشاء مثيل البطاقة المصرفية
                isExist = true;
                break;
            }
        }
        if (isExist == false){
            System.err.println("رقم البطاقة المصرفية الذي أدخلته غير موجود ، يرجى إدخاله مرة أخرى");
            showTransfer(myCard);// نداء متكرر
        }
        int amount = checkAmountInput("تحويل",5000);// يمكنك فقط نقل 5000 في كل مرة
        if (myCard.getBalance().compareTo(new BigDecimal(amount)) == -1){
            System.err.println("عذرًا ، رصيدك غير كافٍ");
            System.out.println();
            showOperation(myCard);
        }
        transfer(myCard,anotherCard,amount);
        System.out.println("تم النقل بنجاح! الرجاء متابعة العملية الخاصة بك");
        showOperation(myCard);
    }
    //====================================
    private int checkAmountInput(String operation,int maxAmount){
        Scanner input = new Scanner(System.in);
        int amount;
        System.out.println("الرجاء إدخال ما تريد"+operation+"المبلغ (لا يتجاوز مرة واحدة)"+maxAmount);
        while (true) {
            try {
                amount = input.nextInt();
                input.nextLine();
                if (amount % 100 != 0) {
                    System.err.println(operation+"يجب أن يكون الرقم مضاعفًا لا يتجزأ لـ 100 ، الرجاء إعادة إدخال:");
                    continue;
                }
                if (amount > maxAmount) {
                    System.err.println(operation+"لا يمكن تجاوز الرقم في وقت واحد"+maxAmount+"، الرجاء إدخال مرة أخرى:");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                input.nextLine();// عندما لا يتطابق نوع إدخال المستخدم ، امسح مكدس الإدخال
                System.err.println(operation+"يجب أن يكون المبلغ رقمًا صحيح، يرجى إعادة إدخال قيمة الوديعة:");
            }
        }
        return amount;
    }
    //===================================================
}

