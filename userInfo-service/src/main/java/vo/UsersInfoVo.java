package vo;

import com.cn.entity.UserInfo;
import com.cn.entity.UserPoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersInfoVo {

    private UserInfo userInfo;

    private UserPoint userPoint;

    @Override
    public String toString() {
        return "UsersInfoVo{" +
                "userInfo=" + userInfo +
                ", userPoint=" + userPoint +
                '}';
    }
}
