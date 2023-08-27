package BankSystem;

import java.util.Random;

public  class GenerateRandomString {
    private final char[] str = "qwertyuiopasdfghjklzxcvbnm147258369".toCharArray();
    private final String[] head = new String []{"140829","142732"};// رأسي معرف مشترك

    public String generateIntString(int length){
        StringBuilder  intString = new StringBuilder();
        for (int i = 0;i<length;i++){
            intString.append(new Random().nextInt(10));
        }
        return intString.toString();
    }
    public String generateIDNum(){
        String idHead = head[new Random().nextInt(2)];// رأس رقم المعرف
        String year = String.valueOf((int)(Math.random()*121)+1900);// 1900–2020 ، 1900 حتى 2021 ولكن لا تشمل 2021
        String month = String.valueOf((int)(Math.random()*12)+1);// 1 إلى 13 ولكن لا يشمل 13
        month = this.complement(month);
        String day = String.valueOf((int)(Math.random()*30)+1);// 1 إلى 31 ولكن لا يشمل 31
        day = this.complement(day);
        String randomstr = generateIntString(3);// أول 3 أرقام عشوائية من آخر 4 أرقام من المعرّف
        String last = String.valueOf(str[new Random().nextInt(str.length)]);// الرقم الأخير من المعرّف ، الأحرف أو الأرقام العشوائية
        StringBuilder builder = new StringBuilder();
        builder.append(idHead).append(year).append(month).append(day).append(randomstr).append(last);
        return builder.toString();
    }
    private String complement(String monthOrDay){
        // أكمل الشهر أو التاريخ ، إذا كان طول السلسلة أقل من 2 ، أضف 0 إلى المقدمة للإكمال
        if (monthOrDay.length()==1){
            String result = "0".concat(monthOrDay);
            return result;
        }
        return monthOrDay;
    }
}
