package BankSystem;

/**
   * تستخدم هذه الفئة لوصف بطاقات الهوية
 */
public class IdentityCard {
    private final String IDNum;// رقم الهوية ، عادة ما يكون 18 رقمًا ، يمكن أن يكون الرقم الأخير حرفًا
    private final Customer holder;//مالك

    public IdentityCard(Customer holder) {
        // لإنشاء بطاقة هوية ، يجب أن يكون هناك حامل
        // رقم المعرف ، سأقوم بإنشائه لك بشكل عشوائي
        GenerateRandomString g = new GenerateRandomString();
        this.IDNum = g.generateIDNum();
        this.holder = holder;
    }

    public  String getIDNum() {
        return IDNum;
    }

    public  Customer getHolder() {
        return holder;
    }
}

