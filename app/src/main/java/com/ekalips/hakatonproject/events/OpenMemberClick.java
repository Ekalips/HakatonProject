package com.ekalips.hakatonproject.events;

import com.ekalips.hakatonproject.data.Member;

/**
 * Created by ekalips on 4/4/17.
 */

public class OpenMemberClick {
    Member member;

    public OpenMemberClick(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
