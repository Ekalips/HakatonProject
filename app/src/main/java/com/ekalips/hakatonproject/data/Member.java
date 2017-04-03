package com.ekalips.hakatonproject.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by wldev on 4/3/17.
 */

public class Member extends RealmObject {
    @SerializedName("name")
    @Expose
    private String memberName;
    @SerializedName("avatar")
    @Expose
    private String memberPhoto;
    @PrimaryKey
    @SerializedName("_id")
    @Expose
    private String memberId;
    @SerializedName("role")
    @Expose
    private int role;

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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
