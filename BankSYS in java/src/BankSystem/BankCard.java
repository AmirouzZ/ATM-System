package BankSystem;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class BankCard {
    /* فهم الكلمة الأساسية الثابتة: إذا تم تعيين سمة معينة باستخدام المُعدِّل الثابت ، فهذا يعني أنه بغض النظر عن عدد الكائنات التي تم إنشاؤها ، فهناك نسخة واحدة فقط من السمة ، والتي تشترك فيها جميع الكائنات
         بمجرد تعديل كائن معين لهذه السمة ، ستتغير هذه السمة للكائنات الأخرى لأن هذه السمة عامة.
         لذلك: كن حذرًا: لا يمكن إساءة استخدام المعدّل الثابت! !
     */
    private BigDecimal balance = new BigDecimal("0");//توازن
    private final String CARDNUM;// رقم البطاقة ، عادة 19 رقمًا ، أرقام نقية
    private final Customer holder;//مالك
    private int password;//كلمه السر

    public static boolean checkIsExistThisBankCardNum(String bankCardNum){
        boolean flag = false;
        ArrayList<BankCard> allBanCard = new ArrayList<>(BankSystem.getAllBanCard());
        for (int i = 0;i<allBanCard.size();i++){
            while (allBanCard.get(i).getCARDNUM().equals(bankCardNum)){
                flag = true;// رقم البطاقة المصرفية هذا موجود ويحتاج إلى التجديد ، على الرغم من أن الاحتمالية منخفضة للغاية.
            }
        }
        return flag;
    }

    public BankCard(String IDNum,String phoneNum,int password){
        // تحتاج إلى رقم هوية ورقم هاتف محمول
        this.holder = IdentityManageSystem.getIdentifyMsg().get(IDNum);
        String cardNum = new GenerateRandomString().generateIntString(4);// 19 رقمًا عشوائيًا
        while (checkIsExistThisBankCardNum(cardNum)){
            // رقم البطاقة المصرفية هذا موجود ويحتاج إلى التجديد ، على الرغم من أن الاحتمالية منخفضة للغاية.
            cardNum = new GenerateRandomString().generateIntString(4);// 19 رقمًا عشوائيًا
        }
        this.CARDNUM = cardNum;
        this.password = password;
        // تعيين مبلغ عشوائي لفتح الحساب (محاكاة)
        Double money = new Random().nextDouble()*100+1;
        DecimalFormat df = new DecimalFormat("#.00");
        String bd = df.format(money);
        this.balance = new BigDecimal(bd);
    }
    public boolean equals(Object obj){
        if (this==obj){
            return true;
        }
        if (obj instanceof BankCard){
            BankCard newCard = (BankCard)obj;
            if (newCard.getCARDNUM().equals(this.CARDNUM))
                // طالما أن رقم البطاقة هو نفسه ، فإنها تعتبر نفس البطاقة المصرفية
                return true;
        }
        return false;
    }
    public int hashCode(){
        return this.CARDNUM.hashCode();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCARDNUM() {
        return CARDNUM;
    }

    public Customer getHolder() {
        return holder;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public int getPassword() {
        return password;
    }
}

