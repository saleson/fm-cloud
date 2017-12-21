package com.fm.aop.spring.framework.autoproxy;

import com.sun.javafx.UnmodifiableArrayList;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * 不依赖于spring容器中的{@code Advisor}, 需要传递{@code Advisor}, 也不会影响容器中的{@code Advisor}.
 * Created by saleson on 2017/12/21.
 */
public class AdvisorAutoProxyCreator extends DefaultAdvisorAutoProxyCreator {


    private static final long serialVersionUID = 6846577808673042079L;

    private List<Advisor> advisors = new ArrayList<>();


    /**
     * 添加{@code Advisor}
     *
     * @param advisor
     */
    public void addAdvisor(Advisor advisor) {
        advisors.add(advisor);
    }


    /**
     * 移除{@code Advisor}
     *
     * @param advisor
     * @return
     */
    public boolean removeAdvisor(Advisor advisor) {
        return advisors.remove(advisor);
    }

    @Override
    protected List<Advisor> findCandidateAdvisors() {
        return new UnmodifiableArrayList<>(advisors.toArray(new Advisor[advisors.size()]), advisors.size());
    }
}
