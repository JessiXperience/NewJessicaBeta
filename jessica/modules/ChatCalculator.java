package jessica.modules;

import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jessica.module.Category;
import jessica.module.Module;
import jessica.Wrapper;
import jessica.events.EventPacket;
import jessica.value.Value;
import jessica.value.ValueNumber;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketChat;

public class ChatCalculator extends Module {
  ValueNumber delay = new ValueNumber("Delay (ms)", 1337.0D, 1.0D, 5000.0D, 1.0D);
  
  public ChatCalculator() {
    super("ChatCalculator", Category.EXPLOIT);
    addValue((Value)this.delay);
  }
  
  public void onGetPacket(EventPacket packet) {
    if (packet.getPacket() instanceof SPacketChat) {
      SPacketChat m = (SPacketChat)packet.getPacket();
      final String message = m.getChatComponent().getUnformattedText();
      String skobka = "(\\()";
      String operands = "(\\-|\\+|\\*|/)";
      int skobkacount = 0;
      int operandcount = 0;
      Pattern pt = Pattern.compile("(\\()");
      Matcher mt = pt.matcher(message);
      Pattern po = Pattern.compile("(\\-|\\+|\\*|/)");
      Matcher mo = po.matcher(message);
      while (mt.find())
        skobkacount += mt.groupCount(); 
      while (mo.find())
        operandcount += mo.groupCount(); 
      if (message.startsWith("  Решите пример:"))
        (new Thread() {
            public void run() {
              try {
                Thread.sleep((long)ChatCalculator.this.delay.getDoubleValue());
                Integer num1 = Integer.valueOf(0);
                Integer num2 = Integer.valueOf(0);
                String sub1 = message.substring(0, 20);
                String sub2 = message.substring(20, message.length());
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(sub1);
                Matcher m2 = p.matcher(sub2);
                while (m.find())
                  num1 = Integer.valueOf(num1.intValue() + Integer.parseInt(m.group())); 
                while (m2.find())
                  num2 = Integer.valueOf(num2.intValue() + Integer.parseInt(m2.group())); 
                char getsymbol = message.charAt(19);
                char getsymbol2 = message.charAt(20);
                char getsymbol3 = message.charAt(21);
                if (getsymbol == '-') {
                  Integer result = Integer.valueOf(num1.intValue() - num2.intValue());
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol == '+') {
                  Integer result = Integer.valueOf(num1.intValue() + num2.intValue());
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol == '/') {
                  Integer result = Integer.valueOf(num1.intValue() / num2.intValue());
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol == '*') {
                  Integer result = Integer.valueOf(num1.intValue() * num2.intValue());
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol2 == '-') {
                  Integer result = Integer.valueOf(num1.intValue() - num2.intValue());
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol2 == '+') {
                  Integer result = Integer.valueOf(num1.intValue() + num2.intValue());
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol2 == '/') {
                  Integer result = Integer.valueOf(num1.intValue() / num2.intValue());
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol2 == '*') {
                  Integer result = Integer.valueOf(num1.intValue() * num2.intValue());
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol3 == '-') {
                  Integer result = Integer.valueOf(num1.intValue() - num2.intValue());
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol3 == '+') {
                  Integer result = Integer.valueOf(num1.intValue() + num2.intValue());
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol3 == '/') {
                  Integer result = Integer.valueOf(num1.intValue() / num2.intValue());
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol3 == '*') {
                  Integer result = Integer.valueOf(num1.intValue() * num2.intValue());
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                System.out.println("null3");
              } catch (Exception e) {
                e.printStackTrace();
              } 
            }
          }).start(); 
      if (message.startsWith("(i) Решите пример"))
        (new Thread() {
            public void run() {
              try {
                Thread.sleep((long)ChatCalculator.this.delay.getDoubleValue());
                Pattern p = Pattern.compile("(\\d+)");
                Matcher m2 = p.matcher(message);
                Integer sum = Integer.valueOf(0);
                while (m2.find())
                  sum = Integer.valueOf(sum.intValue() + Integer.parseInt(m2.group())); 
                sum = Integer.valueOf(sum.intValue() - 18);
                Wrapper.sendPacket((Packet)new CPacketChatMessage(sum.toString()));
              } catch (Exception e) {
                e.printStackTrace();
              } 
            }
          }).start(); 
      if (message.startsWith("[CegouCraft] Решите"))
        (new Thread() {
            public void run() {
              try {
                Thread.sleep((long)ChatCalculator.this.delay.getDoubleValue());
                Pattern p3 = Pattern.compile("(\\d+)");
                Matcher m3 = p3.matcher(message);
                Integer sum = Integer.valueOf(0);
                while (m3.find())
                  sum = Integer.valueOf(sum.intValue() + Integer.parseInt(m3.group())); 
                Wrapper.sendPacket((Packet)new CPacketChatMessage(sum.toString()));
              } catch (Exception e) {
                e.printStackTrace();
              } 
            }
          }).start(); 
      if (message.startsWith("   Решите пример") && skobkacount == 0 && operandcount == 4)
        (new Thread() {
            public void run() {
              try {
                Thread.sleep((long)ChatCalculator.this.delay.getDoubleValue());
                String sub5 = message.substring(18);
                List<String> list = new CopyOnWriteArrayList<>();
                StringTokenizer sTok = new StringTokenizer(sub5, "(\\-|\\+|\\*|/)", true);
                while (sTok.hasMoreTokens())
                  list.add(sTok.nextToken()); 
                for (String ss : list) {
                  if (ss.equals("*") || ss.equals("/"))
                    for (int i = 0; i < list.size(); i++) {
                      if (((String)list.get(i)).equals("*")) {
                        Integer number = Integer.valueOf(Integer.parseInt(list.get(i - 1)) * Integer.parseInt(list.get(i + 1)));
                        list.set(i, number.toString());
                        list.remove(i + 1);
                        list.remove(i - 1);
                      } else if (((String)list.get(i)).equals("/")) {
                        Integer number = Integer.valueOf(Integer.parseInt(list.get(i - 1)) / Integer.parseInt(list.get(i + 1)));
                        list.set(i, number.toString());
                        list.remove(i + 1);
                        list.remove(i - 1);
                      } 
                    }  
                } 
                for (String ss : list) {
                  if (ss.equals("+") || ss.equals("-"))
                    for (int i = 0; i < list.size(); i++) {
                      if (((String)list.get(i)).equals("+")) {
                        Integer number = Integer.valueOf(Integer.parseInt(list.get(i - 1)) + Integer.parseInt(list.get(i + 1)));
                        list.set(i, number.toString());
                        list.remove(i + 1);
                        list.remove(i - 1);
                      } else if (((String)list.get(i)).equals("-")) {
                        Integer number = Integer.valueOf(Integer.parseInt(list.get(i - 1)) - Integer.parseInt(list.get(i + 1)));
                        list.set(i, number.toString());
                        list.remove(i + 1);
                        list.remove(i - 1);
                      } 
                    }  
                } 
                for (String l : list)
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(l)); 
              } catch (Exception e) {
                e.printStackTrace();
              } 
            }
          }).start(); 
      if (message.startsWith("   Решите пример:") && skobkacount == 2)
        (new Thread() {
            public void run() {
              try {
                byte b;
                Thread.sleep((long)ChatCalculator.this.delay.getDoubleValue());
                if (message.charAt(28) == '(') {
                  b = 29;
                } else {
                  b = 27;
                } 
                if (message.charAt(27) == '(')
                  b = 28; 
                String substr = message.substring(b, message.length());
                char getsymbol = message.charAt(20);
                char getsymbol2 = message.charAt(21);
                char getsymbol5 = message.charAt(24);
                char getsymbol6 = message.charAt(25);
                char getsymbol7 = message.charAt(26);
                char getsymbol9 = substr.charAt(1);
                char getsymbol10 = substr.charAt(2);
                char getsymbol11 = substr.charAt(3);
                Integer num1 = Integer.valueOf(0);
                Integer num2 = Integer.valueOf(0);
                Integer num3 = Integer.valueOf(0);
                Integer num4 = Integer.valueOf(0);
                String sub1 = message.substring(19, 21);
                String sub2 = message.substring(21, 24);
                String sub3 = substr.substring(0, 2);
                String sub4 = substr.substring(2, substr.length());
                Pattern p = Pattern.compile("\\d+");
                Matcher m123 = p.matcher(sub1);
                Matcher m2 = p.matcher(sub2);
                Matcher m3 = p.matcher(sub3);
                Matcher m4 = p.matcher(sub4);
                while (m123.find())
                  num1 = Integer.valueOf(num1.intValue() + Integer.parseInt(m123.group())); 
                while (m2.find())
                  num2 = Integer.valueOf(num2.intValue() + Integer.parseInt(m2.group())); 
                while (m3.find())
                  num3 = Integer.valueOf(num3.intValue() + Integer.parseInt(m3.group())); 
                while (m4.find())
                  num4 = Integer.valueOf(num4.intValue() + Integer.parseInt(m4.group())); 
                int first = 0;
                int second = 0;
                Integer result = Integer.valueOf(0);
                if (getsymbol == '-')
                  first = num1.intValue() - num2.intValue(); 
                if (getsymbol == '+')
                  first = num1.intValue() + num2.intValue(); 
                if (getsymbol == '/')
                  first = num1.intValue() / num2.intValue(); 
                if (getsymbol == '*')
                  first = num1.intValue() * num2.intValue(); 
                if (getsymbol2 == '-')
                  first = num1.intValue() - num2.intValue(); 
                if (getsymbol2 == '+')
                  first = num1.intValue() + num2.intValue(); 
                if (getsymbol2 == '/')
                  first = num1.intValue() / num2.intValue(); 
                if (getsymbol2 == '*')
                  first = num1.intValue() * num2.intValue(); 
                if (getsymbol9 == '-')
                  second = num3.intValue() - num4.intValue(); 
                if (getsymbol9 == '+')
                  second = num3.intValue() + num4.intValue(); 
                if (getsymbol9 == '/')
                  second = num1.intValue() / num2.intValue(); 
                if (getsymbol9 == '*')
                  second = num3.intValue() * num4.intValue(); 
                if (getsymbol10 == '-')
                  second = num3.intValue() - num4.intValue(); 
                if (getsymbol10 == '+')
                  second = num3.intValue() + num4.intValue(); 
                if (getsymbol10 == '/')
                  second = num3.intValue() / num4.intValue(); 
                if (getsymbol10 == '*')
                  second = num3.intValue() * num4.intValue(); 
                if (getsymbol11 == '-')
                  second = num3.intValue() - num4.intValue(); 
                if (getsymbol11 == '+')
                  second = num3.intValue() + num4.intValue(); 
                if (getsymbol11 == '/')
                  second = num3.intValue() / num4.intValue(); 
                if (getsymbol11 == '*')
                  second = num3.intValue() * num4.intValue(); 
                if (getsymbol5 == '-') {
                  result = Integer.valueOf(first - second);
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol5 == '+') {
                  result = Integer.valueOf(first + second);
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol5 == '/') {
                  result = Integer.valueOf(first / second);
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol5 == '*') {
                  result = Integer.valueOf(first * second);
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol6 == '-') {
                  result = Integer.valueOf(first - second);
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol6 == '+') {
                  result = Integer.valueOf(first + second);
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol6 == '/') {
                  result = Integer.valueOf(first / second);
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol6 == '*') {
                  result = Integer.valueOf(first * second);
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol7 == '-') {
                  result = Integer.valueOf(first - second);
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol7 == '+') {
                  result = Integer.valueOf(first + second);
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol7 == '/') {
                  result = Integer.valueOf(first / second);
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
                if (getsymbol7 == '*') {
                  result = Integer.valueOf(first * second);
                  Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
                } 
              } catch (Exception e) {
                e.printStackTrace();
              } 
            }
          }).start(); 
      if (message.startsWith("   Решите пример") && message.indexOf("(") != 18 && skobkacount == 1)
        (new Thread() {
            public void run() {
              try {
                int n;
                Thread.sleep((long)ChatCalculator.this.delay.getDoubleValue());
                if (message.charAt(23) == '(') {
                  n = 24;
                } else {
                  n = 23;
                } 
                String substr = message.substring(n, message.length());
                char getsymbol = message.charAt(20);
                char getsymbol2 = message.charAt(21);
                char getsymbol3 = substr.charAt(1);
                char getsymbol4 = substr.charAt(2);
                char getsymbol5 = substr.charAt(3);
                Integer num1 = Integer.valueOf(0);
                Integer num2 = Integer.valueOf(0);
                Integer num3 = Integer.valueOf(0);
                String sub1 = message.substring(18, 20);
                String sub2 = substr.substring(0, 2);
                String sub3 = substr.substring(2, substr.length());
                Pattern p = Pattern.compile("\\d+");
                Matcher m11 = p.matcher(sub1);
                Matcher m2 = p.matcher(sub2);
                Matcher m3 = p.matcher(sub3);
                while (m11.find())
                  num1 = Integer.valueOf(num1.intValue() + Integer.parseInt(m11.group())); 
                while (m2.find())
                  num2 = Integer.valueOf(num2.intValue() + Integer.parseInt(m2.group())); 
                while (m3.find())
                  num3 = Integer.valueOf(num3.intValue() + Integer.parseInt(m3.group())); 
                System.out.println("Первое число" + num1);
                System.out.println("Второе число" + num2);
                System.out.println("Третье число" + num3);
                int first = 0;
                Integer result = Integer.valueOf(0);
                if (getsymbol3 == '-')
                  first = num2.intValue() - num3.intValue(); 
                if (getsymbol3 == '+')
                  first = num2.intValue() + num3.intValue(); 
                if (getsymbol3 == '/')
                  first = num2.intValue() / num3.intValue(); 
                if (getsymbol3 == '*')
                  first = num2.intValue() * num3.intValue(); 
                if (getsymbol4 == '-')
                  first = num2.intValue() - num3.intValue(); 
                if (getsymbol4 == '+')
                  first = num2.intValue() + num3.intValue(); 
                if (getsymbol4 == '/')
                  first = num2.intValue() / num3.intValue(); 
                if (getsymbol4 == '*')
                  first = num2.intValue() * num3.intValue(); 
                if (getsymbol5 == '-')
                  first = num2.intValue() - num3.intValue(); 
                if (getsymbol5 == '+')
                  first = num2.intValue() + num3.intValue(); 
                if (getsymbol5 == '/')
                  first = num2.intValue() / num3.intValue(); 
                if (getsymbol5 == '*')
                  first = num2.intValue() * num3.intValue(); 
                if (getsymbol == '-')
                  result = Integer.valueOf(num1.intValue() - first); 
                if (getsymbol == '+')
                  result = Integer.valueOf(num1.intValue() + first); 
                if (getsymbol == '/')
                  result = Integer.valueOf(num1.intValue() / first); 
                if (getsymbol == '*')
                  result = Integer.valueOf(num1.intValue() * first); 
                if (getsymbol2 == '-')
                  result = Integer.valueOf(num1.intValue() - first); 
                if (getsymbol2 == '+')
                  result = Integer.valueOf(num1.intValue() + first); 
                if (getsymbol2 == '/')
                  result = Integer.valueOf(num1.intValue() / first); 
                if (getsymbol2 == '*')
                  result = Integer.valueOf(num1.intValue() * first); 
                System.out.println(result);
                Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
              } catch (Exception e) {
                e.printStackTrace();
              } 
            }
          }).start(); 
      if (message.startsWith("   Решите пример: (") && operandcount == 2)
        (new Thread() {
            public void run() {
              try {
                Thread.sleep((long)ChatCalculator.this.delay.getDoubleValue());
                Integer num1 = Integer.valueOf(0);
                Integer num2 = Integer.valueOf(0);
                Integer num3 = Integer.valueOf(0);
                String sub1 = message.substring(19, 21);
                String sub2 = message.substring(21, 24);
                String sub3 = message.substring(25, message.length());
                Pattern p = Pattern.compile("\\d+");
                Matcher m11 = p.matcher(sub1);
                Matcher m2 = p.matcher(sub2);
                Matcher m3 = p.matcher(sub3);
                while (m11.find())
                  num1 = Integer.valueOf(num1.intValue() + Integer.parseInt(m11.group())); 
                while (m2.find())
                  num2 = Integer.valueOf(num2.intValue() + Integer.parseInt(m2.group())); 
                while (m3.find())
                  num3 = Integer.valueOf(num3.intValue() + Integer.parseInt(m3.group())); 
                System.out.println("Первое число" + num1);
                System.out.println("Второе число" + num2);
                System.out.println("Третье число" + num3);
                char getsymbol = message.charAt(20);
                char getsymbol2 = message.charAt(21);
                char getsymbol5 = message.charAt(24);
                char getsymbol6 = message.charAt(25);
                char getsymbol7 = message.charAt(26);
                int first = 0;
                Integer result = Integer.valueOf(0);
                if (getsymbol == '-' || getsymbol2 == '-')
                  first = num1.intValue() - num2.intValue(); 
                if (getsymbol == '+' || getsymbol2 == '+')
                  first = num1.intValue() + num2.intValue(); 
                if (getsymbol == '/' || getsymbol2 == '/')
                  first = num1.intValue() / num2.intValue(); 
                if (getsymbol == '*' || getsymbol2 == '*')
                  first = num1.intValue() * num2.intValue(); 
                if (getsymbol5 == '-' || getsymbol6 == '-' || getsymbol7 == '-')
                  result = Integer.valueOf(first - num3.intValue()); 
                if (getsymbol5 == '+' || getsymbol6 == '+' || getsymbol7 == '+')
                  result = Integer.valueOf(first + num3.intValue()); 
                if (getsymbol5 == '/' || getsymbol6 == '/' || getsymbol7 == '/')
                  result = Integer.valueOf(first / num3.intValue()); 
                if (getsymbol5 == '*' || getsymbol6 == '*' || getsymbol7 == '*')
                  result = Integer.valueOf(first * num3.intValue()); 
                System.out.println(result);
                Wrapper.sendPacket((Packet)new CPacketChatMessage(result.toString()));
              } catch (Exception e) {
                e.printStackTrace();
              } 
            }
          }).start(); 
    } 
  }
}
