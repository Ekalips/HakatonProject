package com.ekalips.hakatonproject.data;

import io.realm.RealmObject;

/**
 * Created by wldev on 4/3/17.
 */

public class Member extends RealmObject{
    private String memberName;
    private String memberPhoto;
    private String memberId;

    public Member() {
    }

    public Member(String memberName, String memberPhoto, String memberId) {
        this.memberName = memberName;
        this.memberPhoto = memberPhoto;
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPhoto() {
        return memberPhoto;
    }

    public void setMemberPhoto(String memberPhoto) {
        this.memberPhoto = memberPhoto;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
