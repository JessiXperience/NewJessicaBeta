package jessica.managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jessica.Wrapper;

public class FriendManager {
	
	public static List<String> friends = new ArrayList<String>();

	public static boolean isFriend(final String nickname) {
        return FriendManager.friends.contains(nickname);
    }
    
    public static void addFriend(final String nickname) {
        if (isFriend(nickname)) {
            Wrapper.msg("&cДанный игрок уже добавлен в список друзей.", true);
            return;
        }
        FriendManager.friends.add(nickname);
        Wrapper.msg("&a\u0418\u0433\u0440\u043e\u043a \u0441 \u043d\u0438\u043a\u043e\u043c \"" + nickname + "\" \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d \u0432 \u0441\u043f\u0438\u0441\u043e\u043a \u0434\u0440\u0443\u0437\u0435\u0439.", true);
    }
    
    public static void delFriend(final String nickname) {
        if (!isFriend(nickname)) {
            Wrapper.msg("&cДанный игрок не является вашим другом!", true);
            return;
        }
        FriendManager.friends.remove(nickname);
        Wrapper.msg("&c\u0418\u0433\u0440\u043e\u043a \u0441 \u043d\u0438\u043a\u043e\u043c \"" + nickname + "\" \u0443\u0434\u0430\u043b\u0451\u043d \u0438\u0437 \u0441\u043f\u0438\u0441\u043a\u0430 \u0434\u0440\u0443\u0437\u0435\u0439.", true);
    }
    
    public static Collection<String> getFriends() {
        if (FriendManager.friends == null) {
            FriendManager.friends = new ArrayList<String>();
        }
        return FriendManager.friends;
    }
    
    public static void setFriends(final Collection<String> friends) {
        FriendManager.friends = (List<String>)(List)friends;
    }
}
